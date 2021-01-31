package com.chatroom.server;

import java.net.Socket;
import java.util.*;


//an extra functionality where server can also send a message to every clients connected to it,but not via Swing GUI as it's name suggests
public class ThreadOut extends Thread{
    Socket socket=null;
    ChatServer server=null;
    String send_to_clients=null;

    public ThreadOut(Socket socket){ this.socket=socket;}

    @Override
    public void run() {
        server = new ChatServer();
        do{

            try {
                Scanner sc =new Scanner(System.in);
                send_to_clients=sc.nextLine();
                String broadcast="server :"+send_to_clients;
                System.out.println("you :"+send_to_clients);
                server.broadcastMessage1(broadcast);               //broadcasting the message that server wants

            }catch (Exception e){e.printStackTrace();}
        }
        while(true);

    }

}
