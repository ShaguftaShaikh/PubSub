package com.pubsub;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pubsub.dao.User;
import com.pubsub.utils.PasswordHash;

public class Login {

	private static List<User> allUsers = new ArrayList<>();

	public void login(String username, String password, Scanner sc) {
		// TODO Auto-generated method stub
		allUsers = User.readUser();
		boolean validate = false, userExist = false;
		;

		if (allUsers != null) {
			for (User user : allUsers) {
				if (user.getName().equals(username)) {
					String storedValue = user.getPassword();
					try {
						validate = PasswordHash.validatePassword(password, storedValue);
						if (validate) {
							landingMenu(sc);
						} else {
							System.out.println("Username or Password Incorrect!");
						}
					} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
						// TODO Auto-generated catch block

					}
					userExist = true;
					break;
				}
			}
			if (!userExist)
				System.out.println("Username or Password Incorrect!");
		} else {
			System.out.println("Username or Password Incorrect!");
		}
	}

	public void landingMenu(Scanner sc) {
		System.out.println("1. View Feed");
		System.out.println("2. View/Edit Profile");
		System.out.println("3. Become a Publisher");
		System.out.println("4. Find Publishers");
		System.out.println("5. Top 10 List");
		System.out.println("6. Suggetions");
		System.out.println("7. Logout");
		System.out.println("Enter your choice: ");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			break;
		case 2:
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
