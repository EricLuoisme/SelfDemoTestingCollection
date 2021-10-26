package Self_Testing.ProxyTesting;

// jdkProxy版
public class Tom implements IPerson{
    @Override
    public void answer() {
        System.out.println("This is Tom");
    }
}

//// cglib版
//public class Tom {
//    public void answer() {
//        System.out.println("This is Tom");
//    }
//}

