package net.sf.sdz.data.files;

import java.beans.*;
import java.io.*;

/** Reads journal.xml (written by {@link SerializeToXML}) back into a Journal via XMLDecoder. */
public class DeserializeFromXML {
    public static void main(String[] args) {
        XMLDecoder decoder = null;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("journal.xml")));
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        Journal journal = (Journal) decoder.readObject();
        System.out.println(journal);
    }
}
