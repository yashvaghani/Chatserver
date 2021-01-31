
package com.chatroom.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//Yashkumar Vaghani


public class ChatServer {         //server class to respond and retrieve data from clients


	static int PORT = 9858;
	public static List<ThreadServer> userList;       //thread list for each clients
	private static int i=0;                          //used for only one execution of threadout object for server response


	public static void main(String[] args) {
		ServerSocket server;
		userList = new ArrayList<ThreadServer>();
		Socket client = null;
			try {
				server = new ServerSocket(PORT);
				System.out.println("Server started...");
				do
				{
					client = server.accept();
					System.out.println("Client Accepted...");

					ThreadServer userThread = new ThreadServer(client);  //initializing threadserver object for receiving datafrom client
					userList.add(userThread);                            //list for thread record of clients
					if(i==0){
						ThreadOut t=new ThreadOut(client);               //execution of threadout for once
						i++;
						t.start();
					}


					userThread.start();                                        //starting a thread of client


				}
				while(true);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	//to broadcast message to every client once server gets some data with some checks of sending data
	 public void broadcastMessage(ThreadServer userThread, String message,List<String> e)
	{
		for(ThreadServer item : userList)
		{	String temp=message;
			if(item != userThread) {                   //to broadcast to other clients

				if(temp.contains("joined"))
				{
					Timestamp t = new Timestamp(System.currentTimeMillis());
					temp=temp+"           "+t;
				}
				item.sendMessage(temp);
			}
			else                                             //to broadcast to the client who is broadcasting the data itself
			{
				if(temp.contains(":"))
				{
				temp=temp.substring(temp.indexOf(":")+1);
				temp="you :"+temp;
				item.sendMessage(temp);
				}
				else if(temp.contains("joined the group"))
				{	String msg[]=temp.split(" joined the group",2);
					String temp_for_userlist=e.get(userList.indexOf(item));
					e.remove(temp_for_userlist);
					e.add(userList.indexOf(item),msg[0]);                  //adding a username in userName list
					Timestamp t = new Timestamp(System.currentTimeMillis());
					temp=temp.substring(temp.indexOf("joined the group")+0);
					temp="you "+temp+"           "+t;
					System.out.println("users are"+e);
					item.sendMessage(temp);

				}
			}

		}
	}

	//to send data to every clients when server itself is sending data or while client gets disconnected
	public void broadcastMessage1(String message)
	{
		for(ThreadServer item : userList)
		{
			item.sendMessage(message);

		}
	}

	//to remove the user from userthread and list of string which used to keep track of clientname
	void removeUser(ThreadServer userThread,List<String> e) {
		String a=null;
		for(ThreadServer item : userList)
		{
			if(item == userThread)
			{
				a=e.get(userList.indexOf(item));
			}
		}
			userList.remove(userThread);
			System.out.println(a+" left the group");
		broadcastMessage1(a+" left the group");
		e.remove(a);
	}
}
// Reference Idea : https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server