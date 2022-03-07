package Self_Testing.transferApiTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 设计一个资金转账的接口
 */
@Slf4j
public class TransferTest {

    /**
     * 转载api
     *
     * @param trxId     该笔转载UUID
     * @param accountA  汇出账户
     * @param accountB  汇入账户
     * @param middleAcc 中间账户
     * @param money     金额
     * @return 是否转载成功
     */
    @Transactional
    public boolean mainTransfer(String trxId, Account accountA, Account accountB, Account middleAcc, BigDecimal money) {

        // 1. 应该先向DB进行查询, 是否已经有该trxId的记录, 幂等请求, 防止重复

        log.info(trxId + " From " + accountA + " transfer " + money + " to " + accountB + " through " + middleAcc + " started");

        // Transfer from A to Mid
        boolean transferToMid = transfer(trxId, accountA, middleAcc, money);

        if (!transferToMid) {
            return false;
        }

        // Transfer from Mid to B
        boolean transferToB = transfer(trxId, middleAcc, accountB, money);

        if (!transferToB) {
            // 落库记录异常
            // 补偿机制, 应该放入MQ, 稍后重试?
            return false;
        }

        // DB 真实落库
        log.info(trxId + " Success");
        return true;
    }

    /**
     * Real Transfer
     *
     * @param trxId    交易ID
     * @param accountA 汇出账户
     * @param accountB 汇入账户
     * @param money    金额
     * @return 是否转账成功
     */
    private boolean transfer(String trxId, Account accountA, Account accountB, BigDecimal money) {

        boolean getALock = false;
        boolean getBLock = false;

        try {
            getALock = accountA.lock.tryLock(1, TimeUnit.MINUTES);
            getBLock = accountB.lock.tryLock(1, TimeUnit.MINUTES);
            // get Both Locks
            if (getALock && getBLock) {
                BigDecimal subtract = accountA.getBalance().subtract(money);
                // check balance
                if (BigDecimal.ZERO.compareTo(subtract) > 0) {
                    // transfer to middle account
                    accountA.setBalance(subtract);
                    accountB.setBalance(accountB.getBalance().add(money));
                    // DB落库, 或者至少记录内容
                    log.info(trxId + " From " + accountA + " transfer " + money + " to " + accountB + " finished");
                    // 删除accountB不允许再转出的标记在Redis
                    return true;
                } else {
                    log.warn(trxId + " From " + accountA + " not enough money");
                    // 设置accountA不允许再转出的标记到Redis
                    return false;
                }
            }
            // can not get both lock
            // 落库该问题, 或者发送到MQ重试?
            log.error("");
            return false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放拿到的锁, 避免其中一个抛出异常进到catch, 另一个没有释放
            if (getALock) {
                accountA.lock.unlock();
            }
            if (getBLock) {
                accountB.lock.unlock();
            }
        }
        return false;
    }


    /**
     * 账户内部类, 主要存储一个Lock, 以及Balance
     */
    public class Account {

        private ReentrantLock lock;
        private final String accId;
        private BigDecimal balance;

        public Account(String accId, BigDecimal balance) {
            this.lock = new ReentrantLock();
            this.accId = accId;
            this.balance = balance;
        }

        public ReentrantLock getLock() {
            return lock;
        }

        public String getAccId() {
            return accId;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public void setBalance(BigDecimal newBalance) {
            this.balance = newBalance;
        }
    }
}
