package com.pubsub;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private List<String> userInterest;

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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " " + password + " " + userInterest;
	}
}
