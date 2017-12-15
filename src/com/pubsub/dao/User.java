package com.pubsub.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pubsub.utils.PubSubConstants;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String password;
	private Set<String> userInterest;
	private boolean isPublisher;
	private Set<User> subscribedPublishers;
	private List<PublisherArticle> publishedArticles;

	public List<PublisherArticle> getPublishedArticles() {
		return publishedArticles;
	}

	public void setPublishedArticles(List<PublisherArticle> publishedArticles) {
		this.publishedArticles = publishedArticles;
	}

	public boolean isPublisher() {
		return isPublisher;
	}

	public void setPublisher(boolean isPublisher) {
		this.isPublisher = isPublisher;
	}

	public Set<String> getUserInterest() {
		return userInterest;
	}

	public void setUserInterest(Set<String> userInterest) {
		this.userInterest = userInterest;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<User> getSubscribedPublishers() {
		return subscribedPublishers;
	}

	public void setSubscribedPublishers(Set<User> subscribedPublishers) {
		this.subscribedPublishers = subscribedPublishers;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " " + password + " "+isPublisher+" " + userInterest + " " + subscribedPublishers;
	}

	@SuppressWarnings("unchecked")
	public static Map<String,User> readUser() {
		// TODO Auto-generated method stub
		Map<String,User> users = null;
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(PubSubConstants.USER_FILE);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			users = (Map<String,User>) objectInputStream.readObject();
			objectInputStream.close();
		} catch (FileNotFoundException e) {

		} catch (ClassNotFoundException e) {

		} catch (IOException e) {

		}
		return users;
	}

	public static void writeUser(Map<String,User> users) throws IOException {
		// TODO Auto-generated method stub
		FileOutputStream fileOutputStream = new FileOutputStream(PubSubConstants.USER_FILE);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(users);
		objectOutputStream.close();
	}
	
	public User getUserByName(String name){
		Map<String,User> allUsers = readUser();
		return allUsers.get(name);
	}
}
