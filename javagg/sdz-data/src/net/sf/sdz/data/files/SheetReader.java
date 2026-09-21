package net.sf.sdz.data.files;

import java.io.*;
import java.util.regex.*;

/**
 * Reads a pipe-delimited "spreadsheet" text file and prints it row by row.
 */
public class SheetReader {

    private static String aFileName = "survey.txt";

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
            String aLine;
            while ((aLine = br.readLine()) != null) {
                String[] dataRecord = aLine.split(Pattern.quote("|"));
                for (int i = 0; i < dataRecord.length; i++) {
                    System.out.print(dataRecord[i] + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
