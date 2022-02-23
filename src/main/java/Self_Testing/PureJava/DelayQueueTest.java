package Self_Testing.PureJava;

import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.DelayQueue;

public class DelayQueueTest {


    public static void main(String[] args) {
        // DelayQueue
        DelayQueue<DelayObject> theDelayQueue = new DelayQueue<>();
        // PriorityQueue
        Queue<Integer> priorityQueue = new PriorityQueue<>((a, b) -> { return a.compareTo(b); });

    }

}
