// Input and output: read from the keyboard, write a file, read it back.
//   javac IODemo.java
//   java IODemo
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class IODemo {
    public static void main(String[] args) throws IOException {

        // ===== KEYBOARD INPUT (Scanner) =====
        Scanner keyboard = new Scanner(System.in);
        System.out.print("What is your name? ");
        String name = keyboard.nextLine();
        System.out.print("What is your favorite number? ");
        String number = keyboard.nextLine();

        // ===== FILE WRITING (FileWriter) =====
        // Writes text to a file on disk. Try changing what gets written to output.txt.
        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write("Name: " + name + "\n");
            writer.write("Favorite number: " + number + "\n");
        }
        System.out.println("Saved output.txt");

        // ===== FILE READING (FileReader) =====
        System.out.println("--- what the file contains ---");
        try (BufferedReader reader = new BufferedReader(new FileReader("output.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
