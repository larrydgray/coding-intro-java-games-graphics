package net.sf.javagg.gnl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 
 * @author Larry
 *
 * 
 */
public class ClientThread extends Thread {
	/** */
	private static int IDcount = 0;
	/** */
	private int ID = 0;
	/** */
	private String name = "Guest";
    /**
     * 
     * @return
     */
	public int getID() {
		return ID;
	}
    /**
     * 
     * @param name
     */
	public void setClientName(String name) {
		this.name = name;
	}
    
	/**
	 * 
	 * @return
	 */
	public String getClientName() {
		return name;
	}
    
	/**
	 * 
	 * @param socket
	 * @param server
	 */
	public ClientThread(Socket socket, sourceforge.jgg.gnl.Server<T> server) {
		this.ID = IDcount++;
		this.client = socket;
		this.server = server;
		try {
			connect();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		listen();
	} // constructor ClientThread

	/**
	 * 
	 */
	private void listen() {
		T t;
		while (true) {
			while ((t = receive()) != null) {
				server.relay(ID, t);
			}
		}
	} // method listen

	/**
	 * A boolean flag indicating the streams are currently opened.
	 */
	private boolean connected;
	/**
	 * The ObjectInputStream used to receive packets through the stream from the
	 * client.
	 */
	private ObjectInputStream in;

	/**
	 * The ObjectOutputStream used to send packets through the stream to the
	 * client.
	 */
	private ObjectOutputStream out;
	/**
	 * The reference to the <tt>Server</tt> which instantiated this thread.
	 */
	protected sourceforge.jgg.gnl.Server<T> server;

	/**
	 * The client's <tt>Socket</tt> which is used to communicate through TCP/IP
	 * Protocol.
	 */
	protected Socket client;

	/**
	 * 
	 * @throws IOException
	 */
	public void connect() throws IOException {
		in = new ObjectInputStream(client.getInputStream());
		out = new ObjectOutputStream(client.getOutputStream());
		connected = true;
		server.connected(ID, name);
	}

	/**
	 * 
	 */
	public void disconnect() {
		try {
			connected = false;
			in.close();
			out.close();
			client.close();
			server.disconnected(ID, name);
		} catch (IOException e) {
		} // catch
	}
    
	/**
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return connected;

	}
 
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T receive() {
		try {
			return (T) in.readObject();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	} // method receive
	
    /**
     * 
     * @param t
     */
	public void send(T t) {
		try {
			out.writeObject(t);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	} // method send
} // class ClientThread
