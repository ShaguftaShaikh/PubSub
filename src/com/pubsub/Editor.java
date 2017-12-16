package com.pubsub;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Editor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -795301287419819751L;

	JPanel mainPanel;
	JTextArea editorArea;
	JScrollPane scrollPane;

	public Editor(String str) {
		this.setVisible(true);
		this.setSize(500, 300);
		this.setTitle("Editor");
		
		
	/*	this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});*/

		mainPanel = new JPanel();
		mainPanel = (JPanel) getContentPane();
		mainPanel.setLayout(new FlowLayout());
		
		editorArea = new JTextArea(100,100);
		scrollPane = new JScrollPane(editorArea);
		
		editorArea.setText(str);
		editorArea.setLineWrap(true);
		editorArea.setWrapStyleWord(true);
		editorArea.setEditable(false);
		
		mainPanel.add(editorArea);
		mainPanel.add(scrollPane);
	}

	public static void main(String[] args) {
		Editor e = new Editor("Suhasini Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque vel augue ut nisi fringilla scelerisque vel non nibh. Nam euismod quis sem non consequat. Quisque sem orci, pulvinar tempus tristique non, ultricies eget sem. Proin hendrerit posuere nibh nec convallis. Nam in laoreet mi. Nam volutpat molestie mauris luctus semper. Vivamus nec sapien ut sapien ornare faucibus. Vivamus id risus malesuada purus pharetra consectetur eu id odio. Curabitur ultricies eros sed iaculis imperdiet. Nulla cursus at eros at pretium. Nulla volutpat placerat tristique. Phasellus mattis tincidunt nunc, in consectetur enim interdum nec. Donec est nulla, facilisis eu aliquet quis, molestie suscipit sapien.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque vel augue ut nisi fringilla scelerisque vel non nibh. Nam euismod quis sem non consequat. Quisque sem orci, pulvinar tempus tristique non, ultricies eget sem. Proin hendrerit posuere nibh nec convallis. Nam in laoreet mi. Nam volutpat molestie mauris luctus semper. Vivamus nec sapien ut sapien ornare faucibus. Vivamus id risus malesuada purus pharetra consectetur eu id odio. Curabitur ultricies eros sed iaculis imperdiet. Nulla cursus at eros at pretium. Nulla volutpat placerat tristique. Phasellus mattis tincidunt nunc, in consectetur enim interdum nec. Donec est nulla, facilisis eu aliquet quis, molestie suscipit sapien.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque vel augue ut nisi fringilla scelerisque vel non nibh. Nam euismod quis sem non consequat. Quisque sem orci, pulvinar tempus tristique non, ultricies eget sem. Proin hendrerit posuere nibh nec convallis. Nam in laoreet mi. Nam volutpat molestie mauris luctus semper. Vivamus nec sapien ut sapien ornare faucibus. Vivamus id risus malesuada purus pharetra consectetur eu id odio. Curabitur ultricies eros sed iaculis imperdiet. Nulla cursus at eros at pretium. Nulla volutpat placerat tristique. Phasellus mattis tincidunt nunc, in consectetur enim interdum nec. Donec est nulla, facilisis eu aliquet quis, molestie suscipit sapien.");
		/*e.setVisible(true);
		e.setSize(500, 300);
		e.setTitle("Editor");
		e.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});*/
	}

}
