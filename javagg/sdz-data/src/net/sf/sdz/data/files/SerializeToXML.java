package net.sf.sdz.data.files;

import java.util.*;
import java.beans.*;
import java.io.*;

/**
 * Builds a small Journal of JournalEntry beans and writes it to journal.xml
 * using XMLEncoder — JavaBeans-style XML serialization, driven purely by
 * getters/setters rather than the java.io.Serializable mechanism used in
 * {@link ObjectWriterReader}.
 */
public class SerializeToXML {
    ArrayList<JournalEntry> entries = new ArrayList<JournalEntry>();

    public SerializeToXML() {
        JournalEntry anEntry = new JournalEntry();
        anEntry.date = new Date("10/2/18");
        anEntry.creditNote = "Food order in";
        anEntry.debitNote = "Pizza";
        anEntry.creditAccount = 100;
        anEntry.debitAccount = 500;
        anEntry.amount = 17.95f;
        entries.add(anEntry);
        anEntry = new JournalEntry();
        anEntry.date = new Date("10/2/18");
        anEntry.creditNote = "Tip order in";
        anEntry.debitNote = "Pizza tip";
        anEntry.creditAccount = 100;
        anEntry.debitAccount = 501;
        anEntry.amount = 4.0f;
        entries.add(anEntry);
        anEntry = new JournalEntry();
        anEntry.date = new Date("10/5/18");
        anEntry.creditNote = "Credit Card Payment";
        anEntry.debitNote = "Oct Payment";
        anEntry.creditAccount = 100;
        anEntry.debitAccount = 310;
        anEntry.amount = 100.00f;
        entries.add(anEntry);
        anEntry = new JournalEntry();
        anEntry.date = new Date("10/7/18");
        anEntry.creditNote = "Denny's Work Check";
        anEntry.debitNote = "Pay Check";
        anEntry.creditAccount = 400;
        anEntry.debitAccount = 100;
        anEntry.amount = 420.37f;
        entries.add(anEntry);
        Journal aJournal = new Journal();
        aJournal.journal = entries;
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("journal.xml")));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        encoder.writeObject(aJournal);
        encoder.close();
    }

    public static void main(String args[]) {
        new SerializeToXML();
    }
}
