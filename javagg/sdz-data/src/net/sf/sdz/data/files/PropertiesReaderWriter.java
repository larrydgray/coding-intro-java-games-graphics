package net.sf.sdz.data.files;

import java.util.regex.*;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 * Reads a property file, changes an RPG character's stats, and writes the
 * updated properties back out to a new file.
 */
public class PropertiesReaderWriter {

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    public class PropertyRecord {

        String name = null;
        String value = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

        public int getInt() {
            return Integer.parseInt(value);
        }

        public void setInt(int anInt) {
            value = "" + anInt;
        }

        public float getFloat() {
            return Float.parseFloat(value);
        }

        public void setFloat(Float aFloat) {
            value = "" + aFloat;
        }

        public boolean getBoolean() {
            return Boolean.parseBoolean(value);
        }

        public void setBoolean(boolean aBoolean) {
            value = "" + aBoolean;
        }

        public Date getDate() {
            return new Date(value);
        }

        public void setDate(Date date) {
            value = dateFormat.format(date);
        }

        public String toString() {
            return name + "=" + value;
        }
    }

    private static String aFileName = "properties1.txt";
    private static String outputFileName = "newproperties.txt";

    public PropertiesReaderWriter() {
        PropertyRecord property = null;
        ArrayList<PropertyRecord> properties = new ArrayList<PropertyRecord>();
        try (BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
            String aLine;
            while ((aLine = br.readLine()) != null) {
                String[] propertyInput = aLine.split(Pattern.quote("="));
                property = new PropertyRecord();
                property.name = propertyInput[0];
                property.value = propertyInput[1];
                properties.add(property);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        property = findProperty(properties, "name");
        property.value = "Larry the Wizard";
        property = findProperty(properties, "birth");
        property.setDate(new Date("1/8/2018"));
        property = findProperty(properties, "level");
        property.setInt(13);
        property = findProperty(properties, "wealth");
        property.setFloat(120.97f);
        property = findProperty(properties, "magic");
        property.setBoolean(true);
        write(properties);
    }

    public PropertyRecord findProperty(ArrayList<PropertyRecord> properties, String name) {
        Iterator<PropertyRecord> propertiesIterator = properties.iterator();
        PropertyRecord property = null;
        while (propertiesIterator.hasNext()) {
            property = propertiesIterator.next();
            if (property.name.equals(name)) {
                return property;
            }
        }
        return null;
    }

    public void displayCharacterStats(Iterator<PropertyRecord> propertiesIterator) {
        PropertyRecord property = null;
        while (propertiesIterator.hasNext()) {
            property = propertiesIterator.next();
            if (property.name.equals("name")) {
                System.out.println("The characters name is: " + property.value);
            }
            if (property.name.equals("birth")) {
                System.out.println("The characters creation date was: " + dateFormat.format(property.getDate()));
            }
            if (property.name.equals("level")) {
                System.out.println("The character has obtained level: " + property.getInt());
            }
            if (property.name.equals("wealth")) {
                System.out.println("The character has in his purse: $" + property.getFloat());
            }
            if (property.name.equals("magic")) {
                System.out.println("The character" + ((property.getBoolean()) ? " is " : " is not ") + "a magic user");
            }
        }
    }

    public void write(ArrayList<PropertyRecord> properties) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
            Iterator<PropertyRecord> propertiesIterator = properties.iterator();
            while (propertiesIterator.hasNext()) {
                bw.write(propertiesIterator.next().toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PropertiesReaderWriter();
    }
}
