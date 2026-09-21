package net.sf.javagg.gnl;

import java.util.Iterator;

/**
 * 
 * This class should provide methods for handling the following..
 * 
 * Getting a command code from the start of the string. passing the rest of the
 * string to the command method commands will be
 * 
 * Room Message (sent to all client threads) Nick Private Message (sent to a
 * client thread id) Get Nick list (nicks and client thread id's) Send join
 * message with nick(client thread id) Send part message with nick(client thread
 * id) Set nick associated with client thread id.
 * 
 * A nick will default as guest+Client Thread ID until set.
 * 
 * 
 * 
 * @author Larry Gray(caverdude)
 * @param <T>
 * 
 */
public class ChatServer extends Server<ChatServer.Message> {
	/**
	 * 
	 * @author Larry
	 *
	 */
	public static class Message {
		String command;
		String message;
	}
    /**
     * 
     * @param incoming
     */
	public void roomMessage(Message incoming) {
		sendAll(incoming);
	}
    /**
     * 
     * @param incoming
     */
	public void privateMessage(Message incoming) {
		String a = incoming.command;
		String[] params = a.split("|");
		int i = Integer.parseInt(params[1]);
		send(i, incoming);
	}
    /**
     * 
     * @param id
     */
	public void sendNickList(int id) {
		Iterator<ClientThread<Message>> clientThreads = super.clienIterator();
		ClientThread aClient = null;
		String nickList = "";
		while (clientThreads.hasNext()) {
			aClient = clientThreads.next();
			nickList += aClient.getClientName() + " " + aClient.getID() + "|";
		}
		Message message = new Message();
		message.command = "nicklist";
		message.message = nickList;
		send(id, message);
	} // method sendNickList
    /**
     * 
     * @param id
     * @param nick
     */
	public void setNick(int id, String nick) {
		Iterator<ClientThread<Message>> clientThreads = super.clienIterator();
		ClientThread aClient = null;

		while (clientThreads.hasNext()) {
			aClient = clientThreads.next();
			if (aClient.getID() == id) {
				aClient.setName(nick);
			}
		}

	} // method setNick

	
	/**
	 * 
	 */
	@Override
	public void connected(int id, String nick) {
		sendJoin(id, nick);
	}

	/**
	 * 
	 */
	@Override
	public void disconnected(int id, String nick) {
		sendPart(id, nick);
	}

	public void sendJoin(int id, String nick) {
		Message message = new Message();
		message.command = "join";
		message.message = "nick " + id;
		sendAll(message);

	}
    /**
     * 
     * @param id
     * @param nick
     */
	public void sendPart(int id, String nick) {
		Message message = new Message();
		message.command = "part";
		message.message = "nick " + id;
		sendAll(message);
	}
    
	/**
	 * 
	 * @param max
	 * @param port
	 */
	public ChatServer(int max, int port) {
		super(max, port);
	}
    
	/**
	 * 
	 */
	@Override
	public void relay(int id, Message message) {
		String command = message.command;
		String theMessage = message.message;
		String messageArgs[] = theMessage.split("|");
		if (command.equals("nick")) {
			setNick(id, theMessage);
		} else if (command.equals("nicks")) {

		} else if (command.equals("room")) {

			this.sendAll(message);
		} else if (command.equals("private")) {
			int client = Integer.parseInt(messageArgs[1]);
			this.send(client, message);
		} else if (command.equals("disconnect")) {

		}

	} // method relay

} // class CahtServer
