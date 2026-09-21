package net.sf.sdz.data.files;

import java.io.*;

public class AFileAppender {
    private static String aFileName = "textfile1.txt";

    public static void main(String[] args) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(aFileName, true))) {
            String aString = "The quick brown fox jumped over the fence.\n";
            bw.write(aString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
