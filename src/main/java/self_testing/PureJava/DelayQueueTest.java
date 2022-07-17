package self_testing.PureJava;

import java.util.*;
import java.util.concurrent.DelayQueue;

public class DelayQueueTest {


    public static void main(String[] args) {
        // DelayQueue
        DelayQueue<DelayObject> theDelayQueue = new DelayQueue<>();
        // PriorityQueue
        Queue<Integer> priorityQueue = new PriorityQueue<>((a, b) -> { return a.compareTo(b); });
        // List
        List<String> list = new LinkedList<>();
        // Map
        Map<String, Integer> map = new HashMap<>();
    }

}
