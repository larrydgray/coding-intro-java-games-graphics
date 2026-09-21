package net.sf.sdz.data.files;

import java.io.*;
import java.util.regex.*;
import java.util.*;
import java.text.*;

/**
 * Reads a pipe-delimited "spreadsheet" text file into a row/col addressable
 * Sheet, demonstrates typed get/set access on a few cells, and writes the
 * sheet back out. Each column is padded with whitespace to the width of the
 * widest value written into it; a value longer than the current width
 * expands the whole column.
 */
public class SheetReaderWriter {

    private static String aFileName = "survey.txt";
    private static String outputFileName = "survey2.txt";
    Sheet sheet = null;

    public class Sheet {

        String[][] sheet = null;
        int rows;
        int cols;

        public String toString() {
            String rowString = "";
            String sheetString = "";
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    rowString += sheet[r][c] + "|";
                }
                sheetString += rowString + "\n";
                rowString = "";
            }
            return sheetString;
        }

        public Sheet(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            sheet = new String[rows][cols];
        }

        private int getColWidth(int col) {
            if (sheet[0][col] == null) {
                return 0;
            } else {
                return sheet[0][col].length();
            }
        }

        private String appendWhiteSpace(String theString, int length) {
            return (theString + "   ").substring(0, length);
        }

        private void expandColWidth(int col, int w) {
            for (int r = 0; r < rows; r++) {
                sheet[r][col] = appendWhiteSpace(sheet[r][col], w);
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

        public String manageColumnSpace(String string, int col) {
            int w = getColWidth(col);
            if (w == 0) {
                return string;
            }
            int l = string.length();
            if (l <= w) {
                return appendWhiteSpace(string, w);
            } else {
                expandColWidth(col, l);
                return string;
            }
        }

        public int getInt(int row, int col) {
            return Integer.parseInt((sheet[row][col]).trim());
        }

        public void setInt(int row, int col, int anInt) {
            sheet[row][col] = manageColumnSpace("" + anInt, col);
        }

        public void setFloat(int row, int col, float aFloat) {
            sheet[row][col] = manageColumnSpace("" + aFloat, col);
        }

        public float getFloat(int row, int col) {
            return Float.parseFloat((sheet[row][col]).trim());
        }

        public boolean getBoolean(int row, int col) {
            return Boolean.parseBoolean((sheet[row][col]).trim());
        }

        public void setBoolean(int row, int col, boolean aBoolean) {
            sheet[row][col] = manageColumnSpace("" + aBoolean, col);
        }

        public Date getDate(int row, int col) {
            return new Date(sheet[row][col]);
        }

        public void setDate(int row, int col, Date date) {
            sheet[row][col] = manageColumnSpace(dateFormat.format(date), col);
        }

        public String getData(int row, int col) {
            return sheet[row][col];
        }

        public void setData(int row, int col, String string) {
            sheet[row][col] = manageColumnSpace(string, col);
        }
    }

    public SheetReaderWriter() {
        ArrayList<String> rows = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
            String aLine;
            while ((aLine = br.readLine()) != null) {
                rows.add(aLine);
            }
            Iterator<String> rowsIterator = rows.iterator();
            int r = 0;
            while (rowsIterator.hasNext()) {
                String[] cols = rowsIterator.next().split(Pattern.quote("|"));
                if (sheet == null) {
                    sheet = new Sheet(rows.size(), cols.length);
                }
                for (int c = 0; c < cols.length; c++) {
                    sheet.setData(r, c, cols[c]);
                }
                r++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet.setDate(3, 1, new Date("09/02/2018"));
        sheet.setData(2, 3, "Joseph Stalin Jr III");
        sheet.setData(0, 3, sheet.getData(1, 3));
        sheet.setFloat(9, 2, sheet.getFloat(10, 2) - 180.0f);
        sheet.setInt(10, 4, sheet.getInt(10, 4) + 1);
        sheet.setBoolean(0, 4, false);
        sheet.setBoolean(1, 4, sheet.getBoolean(0, 4));
        sheet.setBoolean(2, 4, true);
        sheet.setBoolean(3, 4, sheet.getBoolean(2, 4));
        write();
    }

    public void write() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
            String aString = sheet.toString();
            bw.write(aString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SheetReaderWriter();
    }
}
