package com.pubsub.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.pubsub.utils.PubSubConstants;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String password;
	private List<String> userInterest;
	private boolean isPublisher;
	private Set<String> subscribedPublishers;
	// private Publisher publisher;

	public boolean isPublisher() {
		return isPublisher;
	}

	public void setPublisher(boolean isPublisher) {
		this.isPublisher = isPublisher;
	}

	public List<String> getUserInterest() {
		return userInterest;
	}

	public void setUserInterest(List<String> userInterest) {
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

	public Set<String> getSubscribedPublishers() {
		return subscribedPublishers;
	}

	public void setSubscribedPublishers(Set<String> subscribedPublishers) {
		this.subscribedPublishers = subscribedPublishers;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " " + password + " "+isPublisher+" " + userInterest + " " + subscribedPublishers;
	}

	@SuppressWarnings("unchecked")
	public static List<User> readUser() {
		// TODO Auto-generated method stub
		List<User> users = null;
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(PubSubConstants.USER_FILE);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			users = (List<User>) objectInputStream.readObject();
			objectInputStream.close();
		} catch (FileNotFoundException e) {

		} catch (ClassNotFoundException e) {

		} catch (IOException e) {

		}
		return users;
	}

	public static void writeUser(List<User> users) throws IOException {
		// TODO Auto-generated method stub
		FileOutputStream fileOutputStream = new FileOutputStream(PubSubConstants.USER_FILE);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(users);
		objectOutputStream.close();
	}
}
