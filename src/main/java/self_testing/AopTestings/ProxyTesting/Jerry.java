package self_testing.AopTestings.ProxyTesting;

// jdkProxy版
//public class Jerry implements IPerson{
//    @Override
//    public void answer() {
//        System.out.println("This's Jerry");
//    }
//}

//// cglib版
public class Jerry{
    public void answer() {
        System.out.println("This's Jerry");
    }
}