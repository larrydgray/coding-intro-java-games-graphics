package net.sf.sdz.data.files;

import java.io.*;

/** Same as {@link ReadWriteArrayRandomAccessFile}, but with a 3D array of integers. */
public class ReadWriteArrayRandomAccessFile3 {
    RandomAccessFile raf = null;
    int[][][] integers = new int[10][10][10];
    int min = 10;
    int max = 200;

    public ReadWriteArrayRandomAccessFile3() {
        try {
            raf = new RandomAccessFile("array3.txt", "rw");
            makeRandomIntegers();
            writeIntegers();
            raf.seek(0);
            readIntegers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeRandomIntegers() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                for (int z = 0; z < 10; z++) {
                    integers[x][y][z] = min + (int) (Math.random() * ((max - min) + 1));
                }
            }
        }
    }

    public void writeIntegers() {
        try {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    for (int z = 0; z < 10; z++) {
                        raf.writeInt(integers[x][y][z]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readIntegers() {
        int integer = 0;
        try {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 10; y++) {
                    for (int z = 0; z < 10; z++) {
                        integer = raf.readInt();
                        System.out.println("x:" + x + " y:" + y + " z:" + z + " val:" + integer);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new ReadWriteArrayRandomAccessFile3();
    }
}
