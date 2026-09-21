package net.sf.javagg.mud.core.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sourceforge.jgg.gsc.TextScreenPanel;
import net.sf.javagg.mud.core.server.MudServer;

public class MudTerminal extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5175121291541898157L;
	private JTextArea textScreen = new JTextArea();
	private MudServer gameServer = null;

	private void send(String command) {
		gameServer.receive(id, command);
	}

	public void receive(String gameText) {

	}

	private String id = "";

	public String getId() {
		return id;
	}

	private JTextField commandLine = new JTextField();

	private TextScreenPanel textScreenPanel;

	public MudTerminal(String id, MudServer aMudServer) {
		textScreen.setSize(new Dimension(400, 400));
		aMudServer.connect(this);
		this.id = id;
		gameServer = aMudServer;

		commandLine.setSize(500, 30);

		JPanel scrollButtonPanel = new JPanel();
		JButton up = new JButton("U");
		JButton down = new JButton("D");
		JButton up2 = new JButton("UU");
		JButton up3 = new JButton("UUU");
		JButton down2 = new JButton("DD");
		JButton down3 = new JButton("DDD");

		scrollButtonPanel.setLayout(new GridLayout(6, 1));
		scrollButtonPanel.add(up3);
		scrollButtonPanel.add(up2);
		scrollButtonPanel.add(up);
		scrollButtonPanel.add(down);
		scrollButtonPanel.add(down2);
		scrollButtonPanel.add(down3);
		scrollButtonPanel.setPreferredSize(new Dimension(80, 100));
		JPanel combinedPanel = new JPanel();
		combinedPanel.setLayout(new BorderLayout());
		String fontFileName = "c:/java/workspace/jgg-mud/data/font.png";
		textScreenPanel = new TextScreenPanel(50,30,8,8,fontFileName);
		textScreenPanel.setPreferredSize(new Dimension(500, 500));
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				textScreenPanel.scrollUp();
			}
		});
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				textScreenPanel.scrollDown();
			}
		});
		up2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				textScreenPanel.scrollUp();
				textScreenPanel.scrollUp();
				textScreenPanel.scrollUp();

			}
		});
		down2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				textScreenPanel.scrollDown();
				textScreenPanel.scrollDown();
				textScreenPanel.scrollDown();
			}
		});
		up3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				textScreenPanel.scrollUp();
				textScreenPanel.scrollUp();
				textScreenPanel.scrollUp();
				textScreenPanel.scrollUp();
				textScreenPanel.scrollUp();
				textScreenPanel.scrollUp();
				textScreenPanel.scrollUp();
				textScreenPanel.scrollUp();
				textScreenPanel.scrollUp();
			}
		});
		down3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				textScreenPanel.scrollDown();
				textScreenPanel.scrollDown();
				textScreenPanel.scrollDown();
				textScreenPanel.scrollDown();
				textScreenPanel.scrollDown();
				textScreenPanel.scrollDown();
				textScreenPanel.scrollDown();
				textScreenPanel.scrollDown();
				textScreenPanel.scrollDown();
			}
		});
		combinedPanel.add(textScreenPanel, BorderLayout.CENTER);
		combinedPanel.add(scrollButtonPanel, BorderLayout.EAST);
		this.add(combinedPanel, BorderLayout.NORTH);
		this.add(commandLine, BorderLayout.SOUTH);
		this.setSize(new Dimension(400, 400));
		this.setVisible(true);
		commandLine.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String actionCommand = ae.getActionCommand();
		textScreenPanel.print(actionCommand);
		textScreenPanel.newLine();
		textScreenPanel.scrollUp();
		//send(actionCommand);
	}

	public static void main(String args[]) {

	}
}
