package net.sf.javagg.gnl;

/**
 * This will provide methods for getting the message type from the incoming
 * string. It will then forward the message to the view. It will send a message
 * to the server with a command code prepended.
 * 
 * @author Larry
 * 
 */
public class ChatClient<T> extends Client<T> {
    /**
     * 
     * @param ip
     * @param port
     */
	public ChatClient(String ip, int port) {
		super(ip, port);
		
	}

} // class ChatClient
