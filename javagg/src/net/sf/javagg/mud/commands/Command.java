package net.sf.javagg.mud.commands;


public abstract class Command {
	
	

	private String commandString = "aCommand";
	
	@SuppressWarnings("unused")
	private String[] params = null;

	public Command(String commandString) {
		this.commandString = commandString;
	}
    
    public abstract String process(String params);
    
	public String toString() {
		return commandString;
	}

	
}
