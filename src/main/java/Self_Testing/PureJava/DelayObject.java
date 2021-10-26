package Self_Testing.PureJava;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayObject implements Delayed {

    private String data;
    private long startTime;

    public DelayObject(String data, long delayedInMilliseconds) {
        this.data = data;
        this.startTime = System.currentTimeMillis() + delayedInMilliseconds;
    }

    /**
     * If this function return <= 0, it means this Object which already in the queue, should be popped out
     *
     * @param unit the time unit
     * @return an long for indicate should be outputted or not
     */
    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * This is for sorting the queue's elements
     *
     * @param o obj
     * @return an int represented priority
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.startTime - ((DelayObject) o).startTime);
    }
}
