package net.sf.javagg.mud.core.server;

import java.util.ArrayList;

import net.sourceforge.javagg.mud.core.view.MudTerminal;

/**
 * This class will be the game and it will simulate a client server atmosphere
 * and design. Later MudTerminals might be launched as WebStart apps. 
 * 
 * This class should startup a mud terminal or two. It should load all game world objects.
 * It should process request from terminals and send responses. It should track and keep data
 * pertaining to any changes in the game world. It should track data for individual players.
 * 
 * @author Larry Gray(caverdude)
 * 
 */
public class MudServer {
	public class Command {
		public String id;
		public String command;

		Command(String id, String command) {
			this.id = id;
			this.command = command;
		}
	}

	ArrayList<MudTerminal> mudTerminalConnections = new ArrayList<>();

	public void connect(MudTerminal aMudTerminal) {
		mudTerminalConnections.add(aMudTerminal);
	}

	@SuppressWarnings("unused")
	private void sendAll(String aString) {

	}

	public void receive(String from, String aCommand) {
		commands.add(new Command(from, aCommand));
	}

	private ArrayList<Command> commands = new ArrayList<>();

	public MudServer() {
		new MudTerminal("john", this);
		new MudTerminal("larry", this);
		while (true) {
			while (commands.size() > 0) {
				
				processCommand(commands.remove(0));

			}
		}
	}
    private void processCommand(Command aCommand){
    	System.out.println("id:"+aCommand.id+" command:"+aCommand.command);
    }
	public static void main(String args[]) {
		new MudServer();
	}
}
