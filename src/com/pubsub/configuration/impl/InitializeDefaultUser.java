package com.pubsub.configuration.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pubsub.dao.User;

public class InitializeDefaultUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;

	public static void initializeUsers() {
		List<User> allUsers = User.readUser();

		if (allUsers == null) {
			allUsers = new ArrayList<>();
			try {
				FileReader fileReader = new FileReader(new File("resources/users.txt"));
				BufferedReader bufferedReader = new BufferedReader(fileReader);

				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					String[] str = line.split(",");

					User u = new User();
					u.setName(str[0]);
					u.setPassword(str[1]);
					u.setPublisher(true);

					List<String> list = new ArrayList<>();

					for (int i = 2; i < str.length; i++) {
						list.add(str[i]);
					}
					u.setUserInterest(list);
					u.setSubscribedPublishers(null);
					allUsers.add(u);
				}
				bufferedReader.close();
				
				User.writeUser(allUsers);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} 
	}
}
