package net.sf.sdz.data.files;

import java.io.*;

public class AFileReader {
    private static String aFileName = "textfile1.txt";

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
            String aLine;
            while ((aLine = br.readLine()) != null) {
                System.out.println(aLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
