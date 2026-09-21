package net.sf.sdz.data.files;

import java.io.*;

/** Same idea as {@link ReadWriteArrayRandomAccessFile4}, but addressed by (x, y) into a 2D array. */
public class ReadWriteArrayRandomAccessFile5 {
    RandomAccessFile raf = null;
    int[][] integers = new int[25][25];
    int min = 10;
    int max = 200;

    public ReadWriteArrayRandomAccessFile5() {
        int integer = 0;
        try {
            raf = new RandomAccessFile("array5.txt", "rw");
            makeRandomIntegers();
            writeIntegers();
            raf.seek(0);
            integer = readInt(15, 12);
            System.out.println("Read Int Loc 15,12:" + integer);
            print3x3(15, 12);
            integer *= 5;
            System.out.println("Multiply Int by 5 and write it back to Loc 15,12");
            writeInt(15, 12, integer);
            integer = readInt(15, 12);
            System.out.println("Read New Stored Int Loc 15,12:" + integer);
            print3x3(15, 12);
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

    public int readInt(int x, int y) {
        int integer = 0;
        try {
            raf.seek((x * 4 * 25) + (y * 4)); // 4 bytes per int, 25*4 bytes per x or column
            integer = raf.readInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return integer;
    }

    public void writeInt(int x, int y, int integer) {
        try {
            raf.seek((x * 4 * 25) + (y * 4)); // 4 bytes per int, 25*4 bytes per x or column
            raf.writeInt(integer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print3x3(int x, int y) {
        System.out.println("" + readInt(x - 1, y - 1) + " " + readInt(x, y - 1) + " " + readInt(x + 1, y - 1));
        System.out.println("" + readInt(x - 1, y) + " " + readInt(x, y) + " " + readInt(x + 1, y));
        System.out.println("" + readInt(x - 1, y + 1) + " " + readInt(x, y + 1) + " " + readInt(x + 1, y + 1));
    }

    public static void main(String args[]) {
        new ReadWriteArrayRandomAccessFile5();
    }
}
