package self_testing.transferApiTest;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现转账系统，给外部系统提供账户开户，充值，转账rpc服务，要求如下，
 * - 账户类设计：
 *     类名：Account，包含属性账号，账户余额，持有人身份证账号
 * - 开户功能：
 *     - 入参：开户人身份证账号
 *     - 功能逻辑：创建对应的Account，并持久化
 *     - 返回值：账号
 *     - 要求：一个身份证只允许有一个Account，每个Account的账号唯一
 * - 充值功能：
 *     - 入参：账号，充值金额，请求号（请求号唯一）
 *     - 功能逻辑：将充值金额增加到对应账户的余额中去
 *     - 返回值：余额- 转账功能：
 *     - 入参：转出账号，转入账号，转账金额，请求号（请求号唯一）
 *     - 功能逻辑：转出账号余额减少，转入账号余额增加
 *     - 返回值：转出账号余额
 * - 其他说明
 *     - 上游系统在调用相关服务超时的情况下，会使用原请求发起重试
 *     - 充值与充值，转账与转账，充值与转账之间存在并发情况
 *     - 考虑性能
 *     - 限定单机提供rpc服务(代码提供rpc接口和实现即可，不用考虑如何
 *       发布rpc)，账户持久化上到jvm内存中，不需要使用DB，基于内存的
 *       持久化默认有事务，不用特别考虑事务问题
 *     - 直接基于jdk编写，不依赖其他框架
 */
public class AntTransferDesign {

    /**
     * 账户类
     */
    class Account {

        /**
         * 每个账户操作Balance的锁
         */
        ReentrantLock accLock;

        /**
         * 账户号
         */
        private final String accId;

        /**
         * 开户人身份证号
         */
        private final String identityId;


        public Account(String accId, String identityId) {

            // 1. 查数据库是否有这个identityId的accId了

            // 2. 如果没有, 分布式锁这个identityId, 防止请求多次创建账户

            // 3. 进行Account正常构造
            // 为什么用公平锁? 实际上同一个账户扣款应该是有顺序性的, 如果用非公平锁, 导致前面获取锁的任务超时
            this.accLock = new ReentrantLock(true);
            this.accId = accId;
            this.identityId = identityId;
        }
    }


}
