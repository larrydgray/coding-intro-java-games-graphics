package net.sf.sdz.data.files;

import java.util.*;

/** A bean wrapping a list of {@link JournalEntry} — the object actually serialized to XML. */
public class Journal {
    List<JournalEntry> journal = new ArrayList<JournalEntry>();

    public void setJournal(List<JournalEntry> journal) {
        this.journal = journal;
    }

    public List<JournalEntry> getJournal() {
        return journal;
    }

    public String toString() {
        String journalString = "";
        for (JournalEntry entry : journal) {
            journalString += entry.date + "\n";
            journalString += entry.creditNote + ":";
            journalString += entry.creditAccount + "\n";
            journalString += entry.debitNote + ":";
            journalString += entry.debitAccount + "\n";
            journalString += entry.amount + "\n\n";
        }
        return journalString;
    }
}
