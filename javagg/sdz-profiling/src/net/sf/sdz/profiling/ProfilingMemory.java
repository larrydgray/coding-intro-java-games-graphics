package net.sf.sdz.profiling;

import java.util.*;

/**
 * Crude memory profiling without an IDE or profiler: measure used memory
 * before and after allocating a batch of objects, then divide by the count
 * for an approximate per-object size.
 * <p>
 * Caveat from the article: this only gives a meaningful reading if nothing
 * else is allocating objects while you measure — real applications with
 * other threads/activity going on will need a dedicated isolated test case.
 */
public class ProfilingMemory {
    private void log(String s) {
        System.out.println(s);
    }
    int numberObjects = 10000;
    ArrayList theObjects = new ArrayList();

    public int getRandomInt(int max, int min) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

    public long getKiloBytesUsed() {
        return (long) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() / 1024);
    }

    public long getBytesUsed() {
        return (long) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }

    void pause() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    long startUsed = 0;
    long endUsed = 0;

    public ProfilingMemory() {
        startUsed = getBytesUsed();
        log("start bytes:" + startUsed);
        makeListOfInts();
        endUsed = getBytesUsed();
        log("end bytes:" + endUsed);
        long usedTotal = endUsed - startUsed;
        long used = usedTotal / numberObjects;
        log("" + numberObjects + " Integers used approx. " + (usedTotal) + "bytes ram");
        log("So this would be " + used + "bytes for each Integer object.");
    }

    public void makeListOfInts() {
        for (int i = 0; i < numberObjects; i++) {
            theObjects.add(new Integer(getRandomInt(50, 100)));
        }
    }

    public static void main(String args[]) {
        new ProfilingMemory();
    }
}
