package net.sf.sdz.data.files;

import java.io.*;
import java.util.regex.*;
import java.util.*;
import java.text.*;

/**
 * Reads a pipe-delimited file, reformats each record, and writes it back out
 * to a different file.
 */
public class AFileReaderWriter {
    private static String aFileName = "textfile1.txt";
    private static String outputFileName = "textfile2.txt";

    public static void main(String[] args) {
        String aLine = null;
        ArrayList<String> lines = new ArrayList<String>();
        Date date = null;
        String note = "";
        float amount = 0.0f;
        int account = 0;
        boolean active = false;
        SimpleDateFormat dateFormat = null;
        try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
            while ((aLine = br.readLine()) != null) {
                String[] dataRecord = aLine.split(Pattern.quote("|"));
                for (int i = 0; i < dataRecord.length; i++) {
                    if (i == 0) date = new Date(dataRecord[i]);
                    if (i == 1) note = dataRecord[i];
                    if (i == 2) amount = Float.parseFloat(dataRecord[i]);
                    if (i == 3) account = Integer.parseInt(dataRecord[i]);
                    if (i == 4) active = Boolean.parseBoolean(dataRecord[i]);
                }
                dateFormat = new SimpleDateFormat("MM/dd/yy");
                aLine = dateFormat.format(date) + "|" + note + "|" + amount + "|" + account + "|" + active + "\n";
                lines.add(aLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        AFileReaderWriter frw = new AFileReaderWriter();
        frw.write(lines);
    }

    public void write(ArrayList<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
            Iterator<String> linesIterator = lines.iterator();
            while (linesIterator.hasNext()) {
                bw.write(linesIterator.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
