package com.az.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Display {
	private final int WIDTH =390, HEIGHT=350;
	private JTextArea message;
	public Display(){
		message = create();
	}
	private JTextArea create(){
		JFrame frame = new JFrame ("Inbound Map Generator");
		frame.setSize(700, 400);
		frame.setAlwaysOnTop (true);
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                    System.exit(0);            }
        });
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		JTextArea textArea = new JTextArea ("");
		
		JScrollPane scroll = new JScrollPane (textArea, 
		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setSize(WIDTH,HEIGHT);
		frame.add(scroll);
		frame.setVisible (true);
		return textArea;
	}
	public void addMessage(String message){
		this.message.append(message+"\n");
	}
}
