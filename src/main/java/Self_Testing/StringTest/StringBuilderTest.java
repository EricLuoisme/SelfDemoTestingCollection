package Self_Testing.StringTest;

public class StringBuilderTest {
    StringBuilder sb = new StringBuilder();

    public void test() {
        this.sb.append("ab");
        System.out.println(this.sb);
    }

    public void test2(StringBuilder sb) {
        sb.append("bc");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        StringBuilderTest ins = new StringBuilderTest();
        System.out.println(ins.sb);
        ins.test();
        System.out.println(ins.sb);
        ins.test2(ins.sb);
        System.out.println(ins.sb);
    }
}
