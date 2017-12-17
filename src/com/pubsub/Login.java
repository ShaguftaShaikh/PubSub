package com.pubsub;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.pubsub.dao.User;
import com.pubsub.utils.PasswordHash;

public class Login {

	private static Map<String, User> allUsers = new HashMap<>();

	public void login(String username, String password, Scanner sc) throws IOException {
		// TODO Auto-generated method stub
		allUsers = User.readAllUser();
		boolean validate = false;

		if (allUsers != null) {
			if (allUsers.containsKey(username)) {
				User user = allUsers.get(username);
				String storedValue = user.getPassword();
				try {
					validate = PasswordHash.validatePassword(password, storedValue);
					if (validate) {
						landingMenu(sc, user);
					} else {
						System.out.println("Username or Password Incorrect!");
					}
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					System.out.println("Exception here");
				}
			}
		} else {
			System.out.println("Username or Password Incorrect!");
		}
	}

	public void landingMenu(Scanner sc, User user) throws IOException {
		while (true) {
			System.out.println("1. View Feed");
			System.out.println("2. View/Edit Profile");
			if (user.isPublisher()) {
				System.out.println("3. Publish an Article");
			} else {
				System.out.println("3. Become a Publisher");
			}
			System.out.println("4. Find Publishers");
			System.out.println("5. Top 10 List");
			System.out.println("6. Suggetions");
			System.out.println("7. Logout");
			System.out.println("Enter your choice: ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				ViewFeed.showFeeds(user, sc);
				break;
			case 2:
				new Profile().userProfileMenu(user, sc);
				break;
			case 3:

				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				MainMenu.mainmenu();
				break;
			default:
				System.out.println("Invalid choice");
			}
		}
	}
}
