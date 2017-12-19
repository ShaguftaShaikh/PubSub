package com.pubsub.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pubsub.utils.PubSubConstants;

public class PublisherArticle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;
	
	
	private String article;
	private User publishedBy;
	private String articleCategory;
	private int likes;
	private Date publishingDate;
	private String articleTitle;

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public User getPublishedBy() {
		return publishedBy;
	}

	public void setPublishedBy(User publishedBy) {
		this.publishedBy = publishedBy;
	}

	public String getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(String articleCategory) {
		this.articleCategory = articleCategory;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public Date getPublishingDate() {
		return publishingDate;
	}

	public void setPublishingDate(Date publishingDate) {
		this.publishingDate = publishingDate;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return articleTitle+" "+ article + " " + publishedBy + " " + articleCategory + " " + likes + " " + publishingDate;
	}

	@SuppressWarnings("unchecked")
	public static Map<String,List<PublisherArticle>> readArticles() {
		// TODO Auto-generated method stub
		Map<String,List<PublisherArticle>> publishers = null;
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(PubSubConstants.ARTICLE_FILE);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			publishers = (Map<String,List<PublisherArticle>>) objectInputStream.readObject();
			objectInputStream.close();
		} catch (FileNotFoundException e) {

		} catch (ClassNotFoundException e) {

		} catch (IOException e) {

		}
		return publishers;
	}

	public static void writeArticles(Map<String,List<PublisherArticle>> publishers) throws IOException {
		// TODO Auto-generated method stub
		FileOutputStream fileOutputStream = new FileOutputStream(PubSubConstants.ARTICLE_FILE);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(publishers);
		objectOutputStream.close();
	}
	
	public static void updateArticle(PublisherArticle article,User user){
		
	}
}
