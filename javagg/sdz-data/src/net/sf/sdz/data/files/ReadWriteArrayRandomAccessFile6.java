package net.sf.sdz.data.files;

import java.io.*;

/** Same idea as {@link ReadWriteArrayRandomAccessFile5}, but addressed by (layer, x, y) into a 3D array. */
public class ReadWriteArrayRandomAccessFile6 {
    RandomAccessFile raf = null;
    int[][][] integers = new int[25][25][25];
    int min = 10;
    int max = 200;

    public ReadWriteArrayRandomAccessFile6() {
        int integer = 0;
        try {
            raf = new RandomAccessFile("array6.txt", "rw");
            makeRandomIntegers();
            writeIntegers();
            raf.seek(0);
            integer = readInt(3, 15, 12);
            System.out.println("Read Int Layer 3 Loc 15,12:" + integer);
            print3x3x3(3, 15, 12);
            integer *= 5;
            System.out.println("Multiply Int by 5 and write it back to Layer 3 Loc 15,12");
            writeInt(3, 15, 12, integer);
            integer = readInt(3, 15, 12);
            System.out.println("Read New Stored Int Layer 3 Loc 15,12:" + integer);
            print3x3x3(3, 15, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeRandomIntegers() {
        for (int layer = 0; layer < 25; layer++) {
            for (int x = 0; x < 25; x++) {
                for (int y = 0; y < 25; y++) {
                    integers[layer][x][y] = min + (int) (Math.random() * ((max - min) + 1));
                }
            }
        }
    }

    public void writeIntegers() {
        try {
            for (int layer = 0; layer < 25; layer++) {
                for (int x = 0; x < 25; x++) {
                    for (int y = 0; y < 25; y++) {
                        raf.writeInt(integers[layer][x][y]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int readInt(int layer, int x, int y) {
        int integer = 0;
        try {
            // 4 bytes per int, 25*4 bytes per x or column, 25*25*4 per layer
            raf.seek((layer * 4 * 25 * 25) + (x * 4 * 25) + (y * 4));
            integer = raf.readInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return integer;
    }

    public void writeInt(int layer, int x, int y, int integer) {
        try {
            // 4 bytes per int, 25*4 bytes per x or column, 25*25*4 per layer
            raf.seek((layer * 4 * 25 * 25) + (x * 4 * 25) + (y * 4));
            raf.writeInt(integer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print3x3x3(int l, int x, int y) {
        System.out.print("" + readInt(l - 1, x - 1, y - 1) + " " + readInt(l - 1, x, y - 1) + " " + readInt(l - 1, x + 1, y - 1));
        System.out.print("  " + readInt(l, x - 1, y - 1) + " " + readInt(l, x, y - 1) + " " + readInt(l, x + 1, y - 1));
        System.out.println("  " + readInt(l + 1, x - 1, y - 1) + " " + readInt(l + 1, x, y - 1) + " " + readInt(l + 1, x + 1, y - 1));

        System.out.print("" + readInt(l - 1, x - 1, y) + " " + readInt(l - 1, x, y) + " " + readInt(l - 1, x + 1, y));
        System.out.print("  " + readInt(l, x - 1, y) + " " + readInt(l, x, y) + " " + readInt(l, x + 1, y));
        System.out.println("  " + readInt(l + 1, x - 1, y) + " " + readInt(l + 1, x, y) + " " + readInt(l + 1, x + 1, y));

        System.out.print("" + readInt(l - 1, x - 1, y + 1) + " " + readInt(l - 1, x, y + 1) + " " + readInt(l - 1, x + 1, y + 1));
        System.out.print("  " + readInt(l, x - 1, y + 1) + " " + readInt(l, x, y + 1) + " " + readInt(l, x + 1, y + 1));
        System.out.println("  " + readInt(l + 1, x - 1, y + 1) + " " + readInt(l + 1, x, y + 1) + " " + readInt(l + 1, x + 1, y + 1));
    }

    public static void main(String args[]) {
        new ReadWriteArrayRandomAccessFile6();
    }
}
