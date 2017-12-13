package com.pubsub;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.pubsub.configuration.impl.InitializeDefaultUser;

public class MainMenu implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Map<String, String> map = new HashMap<>();

	static {
		InitializeDefaultUser.initializeUsers();
	}

	public static void main(String[] args)
			throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		mainmenu();

	}

	private static void mainmenu() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String username, password;
		while (true) {
			System.out.println("1. Login");
			System.out.println("2. Signup");
			System.out.println("3. Forgot Password");

			System.out.println("Enter your choice: ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter username: ");
				sc.nextLine();
				username = sc.nextLine();
				System.out.println("Enter password: ");
				password = sc.nextLine();
				//login(username, password);
				break;
			case 2:
				try {
					new SignUp().signup(sc);
				} catch (NoSuchAlgorithmException | InvalidKeySpecException | ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				break;
			case 3:
				forgotPassword();
				break;
			default:
				System.out.println("Invalid Choice");
			}
		}
	}

	private static void forgotPassword() {
		// TODO Auto-generated method stub

	}

	/*private static void login(String username, String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		map = readUser();
		String storedValue = map.get(username);
		boolean validate = false;
		if (storedValue != null) {
			validate = PasswordHash.validatePassword(password, storedValue);
			if (validate) {
				System.out.println("1. View Feed");
				System.out.println("2. View/Edit Profile");
				System.out.println("3. Become a Publisher");
				System.out.println("4. Find Publishers");
				System.out.println("5. Top 10 List");
				System.out.println("6. Suggetions");
				System.out.println("7. Logout");
				System.out.println("Enter your choice: ");
				Scanner sc = new Scanner(System.in);
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
					break;
				default:
					System.out.println("Invalid choice");
				}
				sc.close();
			} else {
				System.out.println("Username or Password Incorrect!");
			}
		}
	}*/
}
