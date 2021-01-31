package com.chatroom.client;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Locale;

//just one more class(same as ChatroomClient)  to execute properly,you can add as much as you want/run via command line
public class client_sample1 extends Thread{

    static Socket client;
    static int PORT = 9858;
    static String clientName;

    public static void main(String[] args) {

        gui client_web =new gui();                        //creates a GUI for a client

        try {
            client = new Socket("localhost", PORT);           //connenction request
            String client_ini=null;                                //used to give first notification of joining to server
            client_web.save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    clientName =client_web.client_name.getText();                //gets client  name from textfield
                    //System.out.println("in the box "+clientName);
                    client_web.client_name.setEditable(false);
                    //System.out.println(client_web.tt.isEditable()+"****");
                    client_web.setTitle("chatBox of : "+clientName.toUpperCase(Locale.ROOT));
                    client_web.save.setVisible(false);
                    client_web.client_name.setVisible(false);
                    client_web.name.setText(clientName.toUpperCase(Locale.ROOT));
                    client_web.name.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 17));
                    client_web.name.setBackground(Color.LIGHT_GRAY);
                    client_web.name.setFocusable(true);
                    client_web.name.setLocation(210,36);

                }
            });
            while(true) {
                Thread.sleep(900);                //to hold a loop for a time to trigger an actionevent to get clientname
                if(client_web.client_name.isEditable()==false){
                    client_ini = clientName + " joined the group";
                    DataOutputStream dos = new DataOutputStream(client.getOutputStream());

                    dos.writeUTF(client_ini);
                    dos.flush();
                    ReadThread clientRead = new ReadThread(client, client_web);           //thread to send a data
                    WriteThread clientWrite = new WriteThread(client, clientName, client_web);     //threa to create a data
                    if (client.isClosed() == false) {
                        clientWrite.start();              //...starts the write and read thread objects

                        clientRead.start();
                    }
                    break;
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
