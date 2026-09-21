package net.sf.sdz.data.files;

import java.util.*;

/**
 * A simple JavaBean (no-arg constructor + getters/setters) representing one
 * accounting journal entry. Needed as a proper bean — not an inner class,
 * and with real getters/setters, not just public fields — for
 * {@link java.beans.XMLEncoder}/{@link java.beans.XMLDecoder} to be able to
 * serialize/deserialize it.
 */
public class JournalEntry {
    Date date = null;
    String creditNote = "";
    String debitNote = "";
    int creditAccount = 0;
    int debitAccount = 0;
    float amount = 0.0f;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCreditNote(String creditNote) {
        this.creditNote = creditNote;
    }

    public void setDebitNote(String debitNote) {
        this.debitNote = debitNote;
    }

    public void setCreditAccount(int creditAccount) {
        this.creditAccount = creditAccount;
    }

    public void setDebitAccount(int debitAccount) {
        this.debitAccount = debitAccount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public String getCreditNote() {
        return creditNote;
    }

    public String getDebitNote() {
        return debitNote;
    }

    public int getCreditAccount() {
        return creditAccount;
    }

    public int getDebitAccount() {
        return debitAccount;
    }

    public float getAmount() {
        return amount;
    }
}
