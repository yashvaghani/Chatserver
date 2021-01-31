package com.chatroom.client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;


//class to recieve the data from server and then show it on the chatbox
public class ReadThread extends Thread{
	Socket client;
	String receivedMessage;
	DataInputStream dis;
	gui client_web=null;
	public static String new_line="\n";

	public ReadThread(Socket socket,gui client_web) {
		this.client = socket;
		this.client_web=client_web;
	}
	
	@Override
	public void run()
	{
		do
		{
			check_onclose(client_web,client);                       //invokes a method to the condtion when client clicks the close button
			try {
				if(client.isClosed())
				{
					break;
				}
			dis = new DataInputStream(client.getInputStream());
			receivedMessage = dis.readUTF();                               //reads the data broadcasted by server
			client_web.chatarea.append(receivedMessage+new_line);                 //appends the data to textarea
			}
				catch(SocketException e)
			{
				System.out.println("socket is closed");
			} catch (IOException e ) {

				e.printStackTrace();
			}

		}
		while(true);
	}


	//a method to the condtion when client clicks the close button
	public static void check_onclose(gui client_web,Socket client)
	{
		client_web.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					client.close();
					System.out.println("gayele");
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
				System.exit(0);
			}
		});
	}
}
