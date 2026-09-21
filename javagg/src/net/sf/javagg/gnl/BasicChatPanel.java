package net.sf.javagg.gnl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.sourceforge.javagg.gnl.ChatServer.Message;


/**
 * 
 * @author Larry Gray(caverdude)
 *
 */
public class BasicChatPanel extends JPanel implements
		ClientNetListener<ChatServer.Message> {
	/** */
	JTextArea chatArea = new JTextArea();
	/** */
	JTextField chatLine = new JTextField();
	/** */
	JScrollPane scrollPane = new JScrollPane(chatArea);
	/** */
	ChatClient<ChatServer.Message> chatClient;
    /**
     * 
     * @param ip
     * @param port
     */
	public BasicChatPanel(String ip, int port) {
		chatClient = new ChatClient<ChatServer.Message>(ip, port);
		chatClient.setClientNetListener(this);
		chatClient.connect();
		this.setLayout(new BorderLayout());
		chatArea.setAutoscrolls(true);
		this.add(chatArea, BorderLayout.CENTER);
		this.add(chatLine, BorderLayout.SOUTH);
		chatLine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				chatArea.append(ae.getActionCommand());
				chatLine.setText("");
			}

		});
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame aFrame = new JFrame("Basic Chat Client");
		BasicChatPanel aBasicChatPanel = new BasicChatPanel("000.000.000.000",
				5000);
		aFrame.add(aBasicChatPanel);
		aFrame.setSize(500, 500);
		aFrame.setVisible(true);
	}

	/**
	 * 
	 */
	public void relay(Message t) {
		String command = t.command;
		String message = t.message;
		String[] params = message.split("|");
		if (params[0].equals("room")) {
			chatArea.append(params[1] + ">" + params[2] + "\n");
		}

	} // method

} // class
