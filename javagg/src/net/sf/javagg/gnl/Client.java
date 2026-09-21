package net.sf.javagg.gnl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * @author Larry
 *
 * @param <T>
 */
public class Client<T> extends Thread {
	/**
	 * 
	 */
	private ClientNetListener<T> clientView;

	/**
	 * 
	 * @param view
	 */
	public void setClientNetListener(ClientNetListener<T> view) {
		clientView = view;
	}

	/** Are we connected yet? */
	private boolean connected;
	/** Stream used for receiving incoming data from server */
	private ObjectInputStream in;
	/** Stream used for sending outgoing data to the server */
	private ObjectOutputStream out;
	/** ip that we are connecting or connected to */
	private String ip;
	/** port that we are connecting or connected to */
	private int port;
	/** A client side socket */
	private Socket socket;

	/**
	 * 
	 * @param ip
	 * @param port
	 */
	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	/**
	 * 
	 */
	public void connect() {
		try {
			socket = new Socket(ip, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			connected = true;

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		listen();
	}
    
	/**
	 * 
	 */
	public void disconnect() {
		try {
			connected = false;
			socket.close();
			out.close();
			in.close();
		} catch (IOException e) {
			// Log.info("Unable to gracefully end connection.");
		}

	}

	/**
	 * 
	 * @param t
	 */
	public void send(T t) {
		try {

			out.writeObject(t);

		} catch (IOException e) {
			e.printStackTrace();

		} // catch
	}

	/** */
	T t;

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void listen() {

		while (connected) {
			try {
				t = (T) in.readObject();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();

				System.exit(-1);
			} // catch
		} // while(connected)
		clientView.relay(t);
	} // method listen

	/**
	 * 
	 */
	@Override
	public void run() {
		super.run();
		connect();
		listen();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return connected;
	}

} // class Client
