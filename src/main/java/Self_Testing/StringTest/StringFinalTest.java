package Self_Testing.StringTest;

public class StringFinalTest {

    String a = "abc";

    /**
     * 由于使用的是this来指代, 所以String会指向另一个值
     */
    public void test () {
        this.a = "bc";
        System.out.println(a);
    }

    /**
     * 由于入参仅为地址, 实际上原来的String指向的值不会被修改
     */
    public void test2(String input) {
        input = "c";
        System.out.println(input);
    }


    public static void main(String[] args) {
        StringFinalTest ins = new StringFinalTest();
        System.out.println(ins.a);
        ins.test();
        System.out.println(ins.a);
        ins.test2(ins.a);
        System.out.println(ins.a);

        ThreadLocal<String> local = ThreadLocal.withInitial(() -> "initial");
        local.set("gccs");
        local.get();
    }


}
