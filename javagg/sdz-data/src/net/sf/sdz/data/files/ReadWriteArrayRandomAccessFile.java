package net.sf.sdz.data.files;

import java.io.*;

/** Writes 100 random integers to a RandomAccessFile, then reads them all back. */
public class ReadWriteArrayRandomAccessFile {
    RandomAccessFile raf = null;
    int[] integers = new int[100];
    int min = 10;
    int max = 200;

    public ReadWriteArrayRandomAccessFile() {
        try {
            raf = new RandomAccessFile("array.txt", "rw");
            makeRandomIntegers();
            writeIntegers();
            raf.seek(0);
            readIntegers();
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

    public void readIntegers() {
        int integer = 0;
        try {
            for (int i = 0; i < 100; i++) {
                integer = raf.readInt();
                System.out.println("#" + i + " " + integer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new ReadWriteArrayRandomAccessFile();
    }
}
