
package com.chatroom.server;

import java.io.*;
import java.net.Socket;
import java.util.*;

//class to handle read operation from every client and to respond to every client what they send to server
public class ThreadServer extends Thread{
	Socket client;
	private static String message;
	ChatServer server;
	DataOutputStream dos = null;
	static String serverstring="";
	public static List<String> userName=new ArrayList<String>();         //to record the client name


	public ThreadServer(Socket socket)  {

		this.client = socket;
		userName.add("");                                                 //to initialy add before client give input of their name

	}
	
	@Override
	public void run()
	{
		message = null;

		server = new ChatServer();
		try {
			while(true)
			{
				if(client.isClosed())
				{
					break;
				}
				serverstring=null;
				DataInputStream dis = new DataInputStream(client.getInputStream());
				message = dis.readUTF();                                            //server is reading data from respective client

				server.broadcastMessage(this, message,userName);           //broadcasting to every clients
				System.out.println(message);
				if(message.contains(" : "))                                         //condition to close the  client when  says bye(just an extra rule apart from closingthe window)
				{
				String []m=message.split(" : ",2);
				if(m[1].equals("bye") )
				{

					dis.close();
					client.close();
					server.removeUser(this,userName);
					break;
				}}
			}
		}
		catch(EOFException f)
		{
			server.removeUser(this,userName);          //handles the situation when client gets disconnect by closing the chat
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	//helps server to send message to respective client
	public void sendMessage(String message)
	{
		try {
			dos = new DataOutputStream(client.getOutputStream());
			dos.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
