package com.chatroom.client;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

//class to create gui
public class gui extends Frame implements ActionListener, WindowListener {
    Button send,save;
    TextField client_name,input;
    TextArea chatarea;
    Label name;
    public gui(){

        setLayout(new FlowLayout());
        setSize(500,350);
        name=new Label("enter your name",Label.CENTER);
        client_name=new TextField(20);
        save=new Button("save name");
        send=new Button("send ");
        input=new TextField("enter the text you want to send!!!!!!!!!!!!!!!!!!!!!");
        chatarea=new TextArea();
        add(name);
        add(client_name);
        add(save);
        add(chatarea);
        add(input);
        add(send);
        chatarea.setBackground(new Color(255,255,204));
        input.setBackground(new Color(255,255,204));
        setBackground(new Color(235,230,230));
        input.setPreferredSize(new Dimension(70,50));
        input.setMaximumSize(new Dimension(400,20));
        client_name.setSize(150,20);
        setVisible(true);
        send.addActionListener(this::actionPerformed);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

//Reference :https://stackoverflow.com/questions/15363706/how-to-program-this-gui-in-java-swing
// https://www.javatpoint.com/java-awt