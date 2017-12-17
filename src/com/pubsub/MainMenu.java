package com.pubsub;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import com.pubsub.configuration.impl.InitializeAppImpl;

public class MainMenu implements Serializable {

	private static final long serialVersionUID = 1L;
	

	static {
		InitializeAppImpl impl = new InitializeAppImpl();
		impl.initializeUsers();
		impl.initializeDefaultArticles();
		impl.initializePublisherToArticles();
		impl.initializeApplicationUser();
		impl.initializeFollowersToPublishers();
	}

	public static void main(String[] args)
			throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		mainmenu();

	}

	public static void mainmenu() throws IOException {
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
				new Login().login(username, password,sc);
				break;
			case 2:
				try {
					sc.nextLine();
					new SignUp().signup(sc);
				} catch (NoSuchAlgorithmException | InvalidKeySpecException | ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
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
}
