package net.sf.sdz.data.files;

import java.io.*;
import java.util.regex.*;
import java.util.*;
import java.text.*;

/**
 * Parses pipe-delimited fields into typed values (Date, String, float, int, boolean)
 * and prints them formatted.
 */
public class TypedDelimitedFileReader {
    private static String aFileName = "textfile1.txt";

    public static void main(String[] args) {
        Date date = null;
        String note = "";
        float amount = 0.0f;
        int account = 0;
        boolean active = false;
        SimpleDateFormat dateFormat = null;
        try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
            String aLine;
            while ((aLine = br.readLine()) != null) {
                String[] dataRecord = aLine.split(Pattern.quote("|"));
                for (int i = 0; i < dataRecord.length; i++) {
                    if (i == 0) date = new Date(dataRecord[i]);
                    if (i == 1) note = dataRecord[i];
                    if (i == 2) amount = Float.parseFloat(dataRecord[i]);
                    if (i == 3) account = Integer.parseInt(dataRecord[i]);
                    if (i == 4) active = Boolean.parseBoolean(dataRecord[i]);
                }
                dateFormat = new SimpleDateFormat("dd/MM/yy");
                System.out.println(dateFormat.format(date) + " " + note + " " + amount + " "
                        + account + " " + ((active) ? "Active" : "Not Active"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
