package com.pubsub;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.pubsub.dao.PublisherArticle;
import com.pubsub.dao.User;

public class Editor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -795301287419819751L;

	public void readArticle(PublisherArticle publisherArticle) {

		JPanel mainPanel = new JPanel();
		mainPanel = (JPanel) getContentPane();
		mainPanel.setLayout(new FlowLayout());

		JTextArea editorArea = new JTextArea(20, 70);
		JScrollPane scrollPane = new JScrollPane(editorArea);

		JLabel articleLable = new JLabel("Article:");
		JLabel likeLable = new JLabel("Likes:");
		JLabel publishedByLable = new JLabel("Published By:");
		JLabel articleTitle = new JLabel(publisherArticle.getArticleTitle());

		articleLable.setForeground(Color.DARK_GRAY);
		likeLable.setForeground(Color.DARK_GRAY);
		publishedByLable.setForeground(Color.DARK_GRAY);

		JLabel publishedByField = new JLabel(publisherArticle.getPublishedBy().getName());
		publishedByField.setForeground(Color.BLACK);

		JLabel likeField = new JLabel(String.valueOf(publisherArticle.getLikes()));
		likeField.setForeground(Color.BLACK);

		editorArea.setText(publisherArticle.getArticle());
		editorArea.setLineWrap(true);
		editorArea.setWrapStyleWord(true);
		editorArea.setEditable(false);

		mainPanel.add(articleLable);
		mainPanel.add(articleTitle);
		mainPanel.add(scrollPane);
		mainPanel.add(publishedByLable);
		mainPanel.add(publishedByField);
		mainPanel.add(likeLable);
		mainPanel.add(likeField);

		this.setVisible(true);
		this.setSize(800, 500);
		this.setTitle("Editor");
		this.setLayout(new FlowLayout());
		this.setResizable(false);

	}

	public void writeArticle(User user) {
		JPanel mainPanel = new JPanel();
		mainPanel = (JPanel) getContentPane();
		mainPanel.setLayout(new FlowLayout());

		JTextArea editorArea = new JTextArea(20, 70);
		JScrollPane scrollPane = new JScrollPane(editorArea);

		JLabel articleLable = new JLabel("Article:");
		JLabel articleTitle = new JLabel("Article Title: ");
		JLabel tagCategory = new JLabel("Choose Category: ");

		JTextField articleTitleField = new JTextField();
		articleTitleField.setColumns(70);

		articleLable.setForeground(Color.DARK_GRAY);

		editorArea.setLineWrap(true);
		editorArea.setWrapStyleWord(true);
		editorArea.setEditable(true);

		mainPanel.add(articleTitle);
		mainPanel.add(articleTitleField);
		mainPanel.add(articleLable);
		mainPanel.add(scrollPane);
		mainPanel.add(tagCategory);

		Set<String> userInterest = user.getUserInterest();
		ButtonGroup categoryGroup = new ButtonGroup();

		JRadioButton[] buttons = new JRadioButton[userInterest.size()];

		int i = 0;
		for (String interest : userInterest) {
			buttons[i] = new JRadioButton(interest);
			categoryGroup.add(buttons[i]);
			mainPanel.add(buttons[i]);
			i++;
		}
		
		JButton publish = new JButton("publish");
		mainPanel.add(publish);

		this.setVisible(true);
		this.setSize(800, 500);
		this.setTitle("Editor");
		this.setLayout(new FlowLayout());
		this.setResizable(false);
	}

	public static void main(String[] args) {
		// new Editor().writeArticle();
	}
}
