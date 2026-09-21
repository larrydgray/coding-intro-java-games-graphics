package net.sf.sdz.data.files;

import java.util.regex.*;
import java.io.*;

/**
 * Reads a simple name=value property file line by line, splitting each line on '='.
 */
public class PropertiesFileReader {
    private static String aFileName = "properties1.txt";

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
            String aLine;
            while ((aLine = br.readLine()) != null) {
                String[] dataRecord = aLine.split(Pattern.quote("="));
                for (int i = 0; i < dataRecord.length; i++) {
                    System.out.println(dataRecord[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
