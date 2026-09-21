package net.sf.javagg.mud.commands;

public interface CommandProcessor {
    public String process(String command, String[] params);
}
