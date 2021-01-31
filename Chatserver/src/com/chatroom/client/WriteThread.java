/**
 *
 */
package com.chatroom.client;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;
import java.net.Socket;

//class to handle a data once client write on chatbox and send to server
public class WriteThread extends Thread{
	Socket client;
	String receivedMessage;
	DataInputStream dis;
	DataOutputStream dos;
	BufferedReader br;
	String sendMessage = null;
	String clientName = null;
	gui client_web=null;
	static String inputMessage=null;
	/**
	 * 
	 */
	public WriteThread(Socket socket, String userName,gui client_web) {
		this.client = socket;
		this.clientName = userName;
		this.client_web=client_web;

	}

	@Override
	public void run()
	{
		inputMessage ="init";                                       //parameter used to handle initial execution of loop

		do {
			check_onclose(client_web,client);                       	//invokes a method to the condtion when client clicks the close button
			try {

				dos = new DataOutputStream(client.getOutputStream());
					client_web.send.addActionListener(new ActionListener() {
						@Override

						public void actionPerformed(ActionEvent e) {

							inputMessage = client_web.input.getText();          //text from textfiels of input message

							client_web.input.setText(null);
							sendMessage = clientName + " : " + inputMessage;
							try {
								if(inputMessage != null && !inputMessage.trim().isEmpty()){
								dos.writeUTF(sendMessage);
								}                        //sends message to server
							} catch (IOException ioException) {
								ioException.printStackTrace();
							}
							checkStatus(inputMessage,client_web,client,dos);         //check the status ,if client says be then it closes the client and chatbox
						}															//with handling an exception(just an extra rule apart from closingthe window)
					});
				if(inputMessage=="init")                                            //handles the first execution
				{
					break;
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
			
		} while (true);
	}
	//to check closing messageby client
	public  void checkStatus(String inputMessage, gui client_web, Socket client, DataOutputStream dos)
	{
		if(inputMessage.equals("bye"))
		{
			try {
				client.close();
				dos.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
			client_web.setVisible(false);
			client_web.dispose();

		}
	}

	//a method to the condtion when client clicks the close button
	public static void check_onclose(gui client_web,Socket client)
	{
		client_web.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					client.close();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
				System.exit(0);
			}
		});
	}
}
