package com.pubsub;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import java.util.Set;

import com.pubsub.configuration.impl.Editor;
import com.pubsub.dao.User;

public class PublishArticle {

	public void publishArticle(User user, Scanner sc)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
		Set<String> userInterest = user.getUserInterest();
		if (userInterest == null || userInterest.isEmpty()) {
			
			System.out.println("You did not added any interest");
			System.out.println("Choose an interset before you write an article");
			System.out.println("Want to add some?(y/n): ");
			
			char ch = sc.next().charAt(0);
			if (ch == 'y') {
				user = new SignUp().chooseInterest(user, sc);
				User.updateUser(user);
				System.out.println("Interset successfully updated");
				System.out.println("Write an Article?(y/n)");
				char choice = sc.next().charAt(0);
				if (choice == 'y') {
					new Editor().writeArticle(user);
				} else if (choice == 'n') {

				} else {
					System.out.println("Invalid choice");
				}
			} else if (ch == 'n') {

			} else {
				System.out.println("Invalid choice");
			}
		} else {
			new Editor().writeArticle(user);
		}
	}
}
