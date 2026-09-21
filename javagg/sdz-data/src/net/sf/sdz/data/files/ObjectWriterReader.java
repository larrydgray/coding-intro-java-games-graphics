package net.sf.sdz.data.files;

import java.io.*;
import java.util.*;

/**
 * Writes a list of Serializable Expense objects to a file with an
 * ObjectOutputStream, then reads them back with an ObjectInputStream.
 * <p>
 * Note: this reads until readObject() itself throws (EOFException on
 * reaching the end of the stream isn't caught separately here), matching
 * the original example.
 */
public class ObjectWriterReader {

    public static class Expense implements Serializable {

        private static final long serialVersionUID = -3601370280790552486L;

        public Expense(float amount, Date date, String catagory, String note) {
            this.amount = amount;
            this.date = date;
            this.catagory = catagory;
            this.note = note;
        }

        /**
         * @serial
         */
        float amount = 0.0f;
        /**
         * @serial
         */
        Date date = null;
        /**
         * @serial
         */
        String catagory = "";
        /**
         * @serial
         */
        String note = "";

        public String toString() {
            return "Amount:" + amount + " Date:" + date + " Catagory:" + catagory + " Note:" + note + "\n";
        }
    }

    ArrayList<Expense> expenses = new ArrayList<Expense>();

    public ObjectWriterReader() {
        Expense anExpense = new Expense(22.25f, new Date("10/2/18"), "Clothing", "Jeans");
        expenses.add(anExpense);
        anExpense = new Expense(55.14f, new Date("10/3/18"), "Fuel", "Gas 2.34/gal");
        expenses.add(anExpense);
        anExpense = new Expense(1.08f, new Date("10/5/18"), "Food", "Coffee");
        expenses.add(anExpense);
        anExpense = new Expense(34.25f, new Date("10/9/18"), "Bar", "Food and Drinks");
        expenses.add(anExpense);
        Iterator<Expense> expenseIterator = expenses.iterator();
        try {
            FileOutputStream fos = new FileOutputStream("expenses.dat", true);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            while (expenseIterator.hasNext()) {
                oos.writeObject((Expense) expenseIterator.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            expenses = new ArrayList<Expense>();
            FileInputStream fis = new FileInputStream("expenses.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Expense expenseObject = null;
            do {
                expenseObject = (Expense) ois.readObject();
                if (expenseObject != null) {
                    expenses.add(expenseObject);
                }
            } while (expenseObject != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Expense expense = null;
        expenseIterator = expenses.iterator();
        while (expenseIterator.hasNext()) {
            expense = expenseIterator.next();
            System.out.println(expense);
        }
    }

    public static void main(String args[]) {
        new ObjectWriterReader();
    }
}
