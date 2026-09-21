package net.sf.sdz.profiling;

import java.util.*;

/**
 * Crude execution-time profiling without an IDE or profiler: bracket an
 * operation with System.currentTimeMillis() calls and subtract. Times
 * building and sorting a large list of random Integers.
 */
public class ProfilingTime {
    private void log(String s) {
        System.out.println(s);
    }
    int numberObjects = 10000000;
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

    public long getTime() {
        return System.currentTimeMillis();
    }

    void pause() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    long startTime = 0;
    long endTime = 0;

    public ProfilingTime() {

        startTime = getTime();
        makeListOfInts();
        endTime = getTime();
        long timeSecs = (endTime - startTime);
        log("Time to make Integers is " + timeSecs + " milliseconds");

        startTime = getTime();
        sort();
        endTime = getTime();
        timeSecs = (endTime - startTime);
        log("Time to sort Integers is " + timeSecs + " milliseconds");

    }

    public void makeListOfInts() {
        for (int i = 0; i < numberObjects; i++) {
            theObjects.add(new Integer(getRandomInt(50, 100)));
        }
    }

    public void sort() {
        Collections.sort(theObjects);
    }

    public static void main(String args[]) {
        new ProfilingTime();
    }
}
