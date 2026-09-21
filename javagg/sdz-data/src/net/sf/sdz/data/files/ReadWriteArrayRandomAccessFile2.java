package net.sf.sdz.data.files;

import java.io.*;

/** Same as {@link ReadWriteArrayRandomAccessFile}, but with a 2D array of integers. */
public class ReadWriteArrayRandomAccessFile2 {
    RandomAccessFile raf = null;
    int[][] integers = new int[25][25];
    int min = 10;
    int max = 200;

    public ReadWriteArrayRandomAccessFile2() {
        try {
            raf = new RandomAccessFile("array2.txt", "rw");
            makeRandomIntegers();
            writeIntegers();
            raf.seek(0);
            readIntegers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeRandomIntegers() {
        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 25; y++) {
                integers[x][y] = min + (int) (Math.random() * ((max - min) + 1));
            }
        }
    }

    public void writeIntegers() {
        try {
            for (int x = 0; x < 25; x++) {
                for (int y = 0; y < 25; y++) {
                    raf.writeInt(integers[x][y]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readIntegers() {
        int integer = 0;
        try {
            for (int x = 0; x < 25; x++) {
                for (int y = 0; y < 25; y++) {
                    integer = raf.readInt();
                    System.out.println("x:" + x + " y:" + y + " val:" + integer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new ReadWriteArrayRandomAccessFile2();
    }
}
