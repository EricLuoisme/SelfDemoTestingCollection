package self_testing.AopTestings.ProxyTesting;

// jdkProxy版
//public class Jerry implements IPerson{
//    @Override
//    public void answer() {
//        System.out.println("This's Jerry");
//    }
//}

//// cglib版
public class Jerry {

    // non para non return
    public void answer() {
        System.out.println("This's Jerry");
    }

    // para + return
    public String hit(String name) {
        return "No " + name;
    }
}