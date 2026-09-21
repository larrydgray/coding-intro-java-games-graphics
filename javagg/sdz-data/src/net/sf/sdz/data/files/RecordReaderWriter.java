package net.sf.sdz.data.files;

import java.io.*;
import java.util.regex.*;
import java.util.*;
import java.text.*;

/**
 * Same idea as {@link AFileReaderWriter}, but holds each record's data in an
 * ExpenseRecord object instead of a raw formatted string.
 */
public class RecordReaderWriter {
    private static String aFileName = "textfile1.txt";
    private static String outputFileName = "textfile3.txt";

    public class ExpenseRecord {
        Date date = null;
        String note = "";
        float amount = 0.0f;
        int account = 0;
        boolean active = false;

        public String toString() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            return dateFormat.format(date) + "|" + note + "|" + amount + "|" + account + "|" + active + "\n";
        }
    }

    public static void main(String[] args) {
        String aLine = null;
        RecordReaderWriter rrw = new RecordReaderWriter();
        ArrayList<ExpenseRecord> records = new ArrayList<ExpenseRecord>();
        try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
            while ((aLine = br.readLine()) != null) {
                String[] dataRecord = aLine.split(Pattern.quote("|"));
                ExpenseRecord expense = rrw.new ExpenseRecord();
                for (int i = 0; i < dataRecord.length; i++) {
                    if (i == 0) expense.date = new Date(dataRecord[i]);
                    if (i == 1) expense.note = dataRecord[i];
                    if (i == 2) expense.amount = Float.parseFloat(dataRecord[i]);
                    if (i == 3) expense.account = Integer.parseInt(dataRecord[i]);
                    if (i == 4) expense.active = Boolean.parseBoolean(dataRecord[i]);
                }
                records.add(expense);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        rrw.write(records);
    }

    public void write(ArrayList<ExpenseRecord> expenseRecords) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
            Iterator<ExpenseRecord> expenseRecordsIterator = expenseRecords.iterator();
            while (expenseRecordsIterator.hasNext()) {
                bw.write(expenseRecordsIterator.next().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
