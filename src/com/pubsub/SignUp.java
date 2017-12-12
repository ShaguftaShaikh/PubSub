package com.pubsub;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pubsub.configuration.Category;
import com.pubsub.configuration.impl.CategoryImpl;
import com.pubsub.dao.User;
import com.pubsub.utils.PasswordHash;

public class SignUp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public void signup() throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
		User user = new User();
		List<User> allUsers = User.readUser();
		Scanner sc = new Scanner(System.in);
		boolean flag = false;
		System.out.println("Enter name: ");
		String username = sc.nextLine();

		if (allUsers != null) {
			for (User users : allUsers) {
				String name = users.getName();
				if (!name.equalsIgnoreCase(username)) {
					System.out.println("Enter password: ");
					String password = sc.nextLine();
					password = PasswordHash.generatePasswordHash(password);
					user = chooseInterest(user);

					user.setName(username);
					user.setPassword(password);
					user.setPublisher(false);

					allUsers.add(user);
					User.writeUser(allUsers);
					break;
				} else {
					flag = true;
				}
			}
		} else {
			allUsers = new ArrayList<>();

			System.out.println("Enter password: ");
			String password = sc.nextLine();
			password = PasswordHash.generatePasswordHash(password);

			user = chooseInterest(user);
			user.setName(username);
			user.setPassword(password);
			user.setPublisher(false);

			choosePublishers(user);
			allUsers.add(user);
			User.writeUser(allUsers);
		}

		if (flag) {
			System.out.println("Username already exist choose another!");
			signup();
		}
		sc.close();
	}

	private User chooseInterest(User user)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		Category category = new CategoryImpl();
		List<String> categories = category.getCategoryList();
		List<String> userChoiceCategory = new ArrayList<String>();
		Scanner sc = new Scanner(System.in);
		int i;
		while (true) {
			for (i = 0; i < categories.size(); i++) {
				System.out.println(i + 1 + ". " + categories.get(i));
			}

			System.out.println(i + 1 + ". EXIT");

			System.out.println("Choose your interest");
			int choice = sc.nextInt();

			if (choice > (i + 1)) {
				System.out.println("Invalid Choice");
			} else if (choice == (i + 1)) {
				sc.close();
				user.setUserInterest(userChoiceCategory);
				return user;
			} else {
				System.out.println(categories.get(choice - 1));
				userChoiceCategory.add(categories.get(choice - 1));
			}
		}
	}

	private void choosePublishers(User user) {
		List<String> userInterest = user.getUserInterest();
		if (userInterest != null) {
			for (String interest : userInterest) {

			}
		}
	}


}
