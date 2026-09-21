package net.sf.sdz.data.files;

import java.io.*;
import java.util.regex.*;

/**
 * Parses a pipe-delimited text file and prints each field.
 */
public class DelimitedFileReader {
    private static String aFileName = "textfile1.txt";

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
            String aLine;
            while ((aLine = br.readLine()) != null) {
                String[] dataRecord = aLine.split(Pattern.quote("|"));
                for (int i = 0; i < dataRecord.length; i++) {
                    System.out.println(dataRecord[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
