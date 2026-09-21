
package net.sourceforge.javagg.rpg;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
  A window that displays the bytes sent to System.out
  and System.err
*/
public class ConsoleWindow
{
  public static void init()
  {
    JFrame frame = new JFrame();
    frame.setTitle("ConsoleWindow");
    final JTextArea output = new JTextArea();
    output.setEditable(false);
    frame.getContentPane().add(new JScrollPane(output));
    frame.setSize(300,200);
    frame.setLocation(200,200);
    frame.show();

    // define a PrintStream that sends its bytes to the
    // output text area
    PrintStream consoleStream = new PrintStream( new
      OutputStream()
      {
        public void write(int b) {} // never called
        public void write(byte[] b, int off, int len)
        {
          output.append(new String(b,off,len));
          output.setCaretPosition((output.getText()).length()-1);
        }
      });
      // set both System.out and System.err to that stream
      System.setOut(consoleStream);
      System.setErr(consoleStream);
   }
}
