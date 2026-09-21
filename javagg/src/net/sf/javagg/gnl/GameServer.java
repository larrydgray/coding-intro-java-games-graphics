package net.sf.javagg.gnl;

/**
 * 
 * @author Larry
 *
 */
public class GameServer extends ChatServer {
	
	/**
	 * 
	 * @author Larry
	 *
	 */
	public static class GameMessage extends ChatServer.Message {
		String gameMessage;
	}
    /**
     * 
     * @param max
     * @param port
     */
	public GameServer(int max, int port) {
		super(max, port);
		// TODO Auto-generated constructor stub
	} // constructor

} // class GameServer
