package net.sf.sdz.data.files;

import java.io.*;

/**
 * Writes 100 random integers, then reads a single integer at a given index,
 * multiplies it by 5, writes it back at the same location, and reads it
 * again to confirm the update — demonstrating seek-based random access.
 */
public class ReadWriteArrayRandomAccessFile4 {
    RandomAccessFile raf = null;
    int[] integers = new int[100];
    int min = 10;
    int max = 200;

    public ReadWriteArrayRandomAccessFile4() {
        int integer = 0;
        try {
            raf = new RandomAccessFile("array4.txt", "rw");
            makeRandomIntegers();
            writeIntegers();
            raf.seek(0);
            integer = readInt(40);
            System.out.println("Read Int Loc 40:" + integer);
            integer *= 5;
            System.out.println("Multiply Int by 5 and write it back to Loc 40");
            writeInt(40, integer);
            integer = readInt(40);
            System.out.println("Read New Stored Int Loc 40:" + integer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeRandomIntegers() {
        for (int i = 0; i < 100; i++) {
            integers[i] = min + (int) (Math.random() * ((max - min) + 1));
        }
    }

    public void writeIntegers() {
        try {
            for (int i = 0; i < 100; i++) {
                raf.writeInt(integers[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int readInt(int loc) {
        int integer = 0;
        try {
            raf.seek(loc * 4); // 4 bytes per int
            integer = raf.readInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return integer;
    }

    public void writeInt(int loc, int integer) {
        try {
            raf.seek(loc * 4); // 4 bytes per int
            raf.writeInt(integer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new ReadWriteArrayRandomAccessFile4();
    }
}
