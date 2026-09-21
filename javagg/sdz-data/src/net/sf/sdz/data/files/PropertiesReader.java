package net.sf.sdz.data.files;

import java.util.regex.*;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 * Parses a name=value property file into PropertyRecord objects, with typed
 * getters/setters, then displays and mutates the stats of an RPG character.
 */
public class PropertiesReader {

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

    public PropertiesReader() {
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
        Iterator<PropertyRecord> propertiesIterator = properties.iterator();
        displayCharacterStats(propertiesIterator);
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
        System.out.println();
        propertiesIterator = properties.iterator();
        displayCharacterStats(propertiesIterator);
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

    public static void main(String[] args) {
        new PropertiesReader();
    }
}
