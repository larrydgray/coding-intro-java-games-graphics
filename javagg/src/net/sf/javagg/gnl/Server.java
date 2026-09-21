package net.sf.javagg.gnl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This will be a basic server that brokers objects in an object stream between
 * client and server.
 *
 * @author Larry Gray(caverdude)
 *
 */
public class Server<T> implements Runnable, ServerNetListener<T> {

 /**
  *
  */
 private ServerNetListener<T> netListener;

 /**
  *
  * @param aNetListener
  */
 public void setNetListener(ServerNetListener<T> aNetListener) {
  netListener = aNetListener;
 }

 /**
  * A list of client connections. *
  */
 private ArrayList<ClientThread<T>> clientThreads = new ArrayList<ClientThread<T>>();

 /**
  *
  * @return
  */
 public Iterator<ClientThread<T>> clienIterator() {
  return clientThreads.iterator();
 }

 /**
  * Indicates whether the Server is online.
  */
 protected boolean connected;

 /**
  * an Internet address used for getting system IP
  */
 private InetAddress inetAddress;
 /**
  * The maximum number of clients which are allowed to connect to this Server.
  */
 @SuppressWarnings("unused")
 private int max;
 /**
  * Port for which this server will accept incoming connections from clients
  */
 protected int port;
 /**
  * Used to listen for new connections from the clients on a specified local
  * port (The <tt>ServerSocket</tt> will be bound to this port given in to the
  * constructor).
  */
 private ServerSocket ss;

 /**
  *
  */
 Socket clientSocket;

 /**
  * Builds a Server using two parameters, the maximum number of clients that the
  * server will allow to connect and the local port number on which the Server
  * will be listening.
  *
  * @param max An integer of the maximum number of clients that may connect to
  * the server.
  * @param port The local port of the Server.
  */
 public Server(int max, int port) {
  this.max = max;
  this.port = port;

 }

 /**
  * Has the server started and is waiting on connections?
  *
  * @return server started status.
  */
 public boolean isStarted() {
  if (ss != null) {
   return true;
  } else {
   return false;
  }
 }

 /**
  * Gets the InetAddress for this server in order to retrieve IP address.
  *
  * @return InetAddress for IP.
  */
 public InetAddress getInetAddress() {
  this.inetAddress = ss.getInetAddress();
  return inetAddress;
 }

 /**
  * Accepts incoming client's Socket information and initiates a new
  * <tt>ClientThread</tt> with it.
  *
  * @param cs A client's Socket.
  */
 public void accept(Socket socket) {

  ClientThread<T> aClientThread;
  aClientThread = new ClientThread<T>(socket, this);
  clientThreads.add(aClientThread);
  aClientThread.start();
 }

 /**
  * <p>
  * Terminates the connection of the Server but first disconnects all the
  * clients which are connected to this Server. Note that since the seats array
  * may contain references to the same Threads as the clients array, it will be
  * default be cleared out and collected as well.
  */
 public void disconnect() {
  int size = clientThreads.size();
  for (int i = 0; i < size; i++) {
   clientThreads.get(i).disconnect();
  } // Loop until all clients have been disconnected.
  connected = false;
  try {
   ss.close();
  } catch (IOException ioe) {
   ioe.printStackTrace();
  }
 }

 /**
  *
  * @param client
  * @param t
  */
 public void send(int client, T t) {
  ClientThread<T> clientThread;
  int size = clientThreads.size();
  for (int i = 0; i < size; i++) {
   clientThread = clientThreads.get(i);
   if (clientThread.getID() == client) {
    clientThread.send(t);
   }
  }
 }

 /**
  * Relays a Packet between the connected clients. NOTE This method will be
  * depricated once the game and chat protocols are in place.
  *
  * @param packet A packet of data from a client.
  *
  */
 public void sendAll(T t) {
  int size = clientThreads.size();
  for (int i = 0; i < size; i++) {
   clientThreads.get(i).send(t);
  } // Loop until all clients have been reached.

 }

 /**
  * A server runs on its own thread so that it will not hang up the GUI.
  *
  */
 @Override
 public void run() {

  this.connected = true;
  try {
   ss = new ServerSocket(port);
   inetAddress = ss.getInetAddress();
   while (connected) {
    accept(ss.accept()); // Loop while connected.
   }
  } catch (IOException e) {
   System.err.println("Socket initiation failed.\n" + e);
   e.printStackTrace();
   System.exit(-1);
  } // End of try/catch block.
 }

 /**
  *
  * @param id
  * @param name
  */
 public void connected(int id, String name) {

 }

 /**
  *
  * @param id
  * @param name
  */
 public void disconnected(int id, String name) {

 }

 /**
  *
  */
 @Override
 public void relay(int id, T t) {
  if (netListener != null) {
   netListener.relay(id, t);
  }

 } // method relay

} // class Server
