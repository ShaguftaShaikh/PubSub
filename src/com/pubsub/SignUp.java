package com.pubsub;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.pubsub.configuration.Category;
import com.pubsub.configuration.impl.CategoryImpl;
import com.pubsub.dao.User;
import com.pubsub.utils.PasswordHash;

/**
 * @author Shagufta
 * 
 * This class is responsible for signup process of the user
 *
 */
public class SignUp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	/**
	 * @param sc
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * 
	 * This will first check for unique name of the user.
	 * if the name is already been use it will show a message and ask user to reenter another name
	 * If all goes well it will further proceeds to ask for interest and publisher and at the end
	 * It will add that user into user list and save the object into user.obj file
	 */
	public void signup(Scanner sc)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {

		List<User> allUsers = User.readUser();
		boolean flag = false;
		String username = "";
		System.out.println("Enter name: ");
		username = sc.nextLine();

		if (allUsers != null) {
			User user = new User();
			Set<String> subscribedPublishers = new HashSet<>();

			//check if username already been in use
			for (User users : allUsers) {
				String name = users.getName();
				if (name.equalsIgnoreCase(username)) {
					flag = true;
					break;
				}
			}

			//username already in use
			if (flag) {
				System.out.println("User with name already exist choose another!");
				signup(sc);
				
			} else {
				//username is unique proceed
				System.out.println("Enter password: ");
				String password = sc.nextLine();
				password = PasswordHash.generatePasswordHash(password);
				user = chooseInterest(user, sc);

				user.setName(username);
				user.setPassword(password);
				user.setPublisher(false);

				//ask user to subscribe publisher
				subscribedPublishers = choosePublishers(user, allUsers, sc);
				user.setSubscribedPublishers(subscribedPublishers);

				allUsers.add(user);
				User.writeUser(allUsers);
				
				Login.landingMenu(sc,user);
			}
		} else {
			allUsers = new ArrayList<>();
			User user = new User();
			Set<String> subscribedPublishers = new HashSet<>();

			System.out.println("Enter password: ");
			String password = sc.nextLine();
			password = PasswordHash.generatePasswordHash(password);

			user = chooseInterest(user, sc);
			user.setName(username);
			user.setPassword(password);
			user.setPublisher(false);

			subscribedPublishers = choosePublishers(user, allUsers, sc);
			user.setSubscribedPublishers(subscribedPublishers);

			allUsers.add(user);
			
			//write all user to users.obj file
			User.writeUser(allUsers);
			
			Login.landingMenu(sc,user);
		}
	}

	private User chooseInterest(User user, Scanner sc)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		Category category = new CategoryImpl();
		List<String> categories = category.getCategoryList();
		Set<String> userChoiceCategory = new HashSet<>();
		int i;
		while (true) {
			for (i = 0; i < categories.size(); i++) {
				System.out.println(i + 1 + ". " + categories.get(i));
			}

			System.out.println(i + 1 + ". Exit");

			System.out.println("Choose your interest");
			int choice = sc.nextInt();

			if (choice > i + 1) {
				System.out.println("Invalid Choice");
			} else if (choice == i + 1) {
				user.setUserInterest(userChoiceCategory);
				return user;
			} else {
				userChoiceCategory.add(categories.get(choice - 1));
			}
		}
	}

	@SuppressWarnings("unused")
	private Set<String> choosePublishers(User user, List<User> allUsers, Scanner sc) {
		System.out.println("1. Show publishers based on your interest");
		System.out.println("2. Show all publishers");
		System.out.println("3. Exit");

		int choice = sc.nextInt();
		Set<String> subscribedPublishers = new HashSet<>();
		List<String> selectedPublisher = new ArrayList<>();
		Set<String> userInterest = user.getUserInterest();

		if (choice == 1 && (!userInterest.isEmpty())) {

			if (userInterest != null) {
				for (String interest : userInterest) {
					if (allUsers != null) {
						for (User u : allUsers) {
							Set<String> allUserInterests = u.getUserInterest();
							if (allUserInterests.contains(interest)) {
								selectedPublisher.add(u.getName());
							}
						}
					}
				}
				while (true) {
					Iterator<String> itr = selectedPublisher.iterator();
					int i = 1;
					while (itr.hasNext()) {
						System.out.println(i + ". " + itr.next());
						i++;
					}
					System.out.println(i + ". Exit");
					System.out.println("Choose publisher: ");
					int c = sc.nextInt();
					if (c > i || c <= 0) {
						System.out.println("Invalid Choice");
					} else if (c == i) {
						return subscribedPublishers;
					} else {
						subscribedPublishers.add(selectedPublisher.get(c - 1));
					}
				}
			}
		} else if (userInterest.isEmpty()) {
			if (choice == 2 || choice == 1) {
				while (true) {
					int i = 1;
					for (User u : allUsers) {
						if (u.isPublisher()) {
							System.out.println(i + ". " + u.getName());
							selectedPublisher.add(u.getName());
							i++;
						}
					}
					System.out.println(i + ". Exit");
					System.out.println("Choose publisher: ");
					int c = sc.nextInt();
					if (c > i || c <= 0) {
						System.out.println("Inavlid Choice");
					} else if (c == i) {
						return subscribedPublishers;
					} else {
						subscribedPublishers.add(selectedPublisher.get(c - 1));
					}
				}
			} else {
				return subscribedPublishers;
			}

		} else {
			return subscribedPublishers;
		}
		return subscribedPublishers;
	}

}
