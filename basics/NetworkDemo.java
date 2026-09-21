// Networking: ask a web server for a page, just like your browser does.
// Compare this with IODemo.java: reading a file and reading from the network
// use the very same idea in Java - an InputStream that you read from.
//   javac NetworkDemo.java
//   java NetworkDemo
//   java NetworkDemo https://www.wikipedia.org     (or any address you like)
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class NetworkDemo {
    public static void main(String[] args) {
        String address = args.length > 0 ? args[0] : "https://example.com";
        System.out.println("Asked for: " + address);

        try {
            // Open a connection. What comes back is an InputStream, just like a file's.
            InputStream stream = new URI(address).toURL().openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            System.out.println("--- first 10 lines ---");
            String line;
            int count = 0;
            while (count < 10 && (line = reader.readLine()) != null) {
                System.out.println(line);
                count++;
            }
            reader.close();
        } catch (Exception e) {
            // Networks fail all the time: no connection, a bad address, a blocked site...
            System.out.println("Could not reach it: " + e.getClass().getSimpleName()
                    + (e.getMessage() == null ? "" : " - " + e.getMessage()));
        }
    }
}
