package com.pubsub;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.pubsub.configuration.Category;
import com.pubsub.configuration.impl.CategoryImpl;
import com.pubsub.dao.User;
import com.pubsub.utils.PasswordHash;

/**
 * @author Shagufta
 * 
 *         This class is responsible for signup process of the user
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
	 *             This will first check for unique name of the user. if the
	 *             name is already been use it will show a message and ask user
	 *             to reenter another name If all goes well it will further
	 *             proceeds to ask for interest and publisher and at the end It
	 *             will add that user into user list and save the object into
	 *             user.obj file
	 */
	public void signup(Scanner sc)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {

		Map<String, User> allUsers = User.readAllUser();
		Map<String, User> publishers = User.readPublisher();
		publishers.remove("initializedArticles");
		publishers.remove("initiallizedFollowers");

		Set<User> subscribedPublishers = new HashSet<>();
		String username = "";
		System.out.println("Enter name: ");
		username = sc.nextLine();

		if (allUsers != null) {
			User user = new User();

			// check if username already been in use
			if (allUsers.containsKey(username)) {
				System.out.println("User with name already exist choose another!");
				signup(sc);
			} else {
				// username is unique proceed
				System.out.println("Enter password: ");
				String password = new String();
				password = sc.nextLine();
				password = PasswordHash.generatePasswordHash(password);
				user = chooseInterest(user, sc);

				user.setName(username);
				user.setPassword(password);
				user.setPublisher(false);

				// ask user to subscribe publisher
				subscribedPublishers = choosePublishers(user, publishers, sc, subscribedPublishers);
				user.setSubscribedPublishers(subscribedPublishers);

				allUsers.put(user.getName(), user);
				User.writeUser(allUsers);

				new Login().landingMenu(sc, user);
			}
		} else {
			allUsers = new HashMap<String, User>();
			User user = new User();

			System.out.println("Enter password: ");
			String password = sc.nextLine();
			password = PasswordHash.generatePasswordHash(password);

			user = chooseInterest(user, sc);
			user.setName(username);
			user.setPassword(password);
			user.setPublisher(false);

			subscribedPublishers = choosePublishers(user, publishers, sc, subscribedPublishers);
			user.setSubscribedPublishers(subscribedPublishers);

			allUsers.put(user.getName(), user);

			// write all user to users.obj file
			User.writeUser(allUsers);

			new Login().landingMenu(sc, user);
		}
	}

	protected User chooseInterest(User user, Scanner sc)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		Category category = new CategoryImpl();
		List<String> categories = category.getCategoryList();
		Set<String> userChoiceCategory = new HashSet<>();
		int i;
		while (true) {
			for (i = 0; i < categories.size(); i++) {
				if (userChoiceCategory.contains(categories.get(i))) {
					System.out.println(i + 1 + ". " + categories.get(i) + " - Added");
				} else {
					System.out.println(i + 1 + ". " + categories.get(i));
				}
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
	protected Set<User> choosePublishers(User user, Map<String, User> publishers, Scanner sc,
			Set<User> subscribedPublishers) throws IOException {
		System.out.println("1. Show publishers based on your interest");
		System.out.println("2. Show all publishers");
		System.out.println("3. Exit");

		int choice = sc.nextInt();

		List<User> selectedPublisher = new ArrayList<>();
		Set<String> userInterest = user.getUserInterest();

		if (choice == 1 && (!userInterest.isEmpty())) {

			if (userInterest != null) {
				for (String interest : userInterest) {
					if (publishers != null) {
						for (Map.Entry<String, User> entry : publishers.entrySet()) {
							User publisher = entry.getValue();
							if (publisher != null) {
								Set<String> allUserInterests = publisher.getUserInterest();
								if (allUserInterests.contains(interest)) {
									if (publisher.isPublisher()) {
										selectedPublisher.add(publisher);
									}
								}
							}
						}
					}
				}
				while (true) {
					Iterator<User> itr = selectedPublisher.iterator();
					int i = 1;
					while (itr.hasNext()) {
						User u = itr.next();
						String name = u.getName();
						if (subscribedPublishers.contains(u))
							System.out.println(i + ". " + name + " - subscribed");
						else
							System.out.println(i + ". " + name);
						i++;
					}
					System.out.println(i + ". Exit");
					System.out.println("Choose publisher: ");
					int c = sc.nextInt();
					if (c > i || c <= 0) {
						System.out.println("Invalid Choice");
					} else if (c == i) {
						return choosePublishers(user, publishers, sc, subscribedPublishers);
					} else {
						subscribedPublishers.add(selectedPublisher.get(c - 1));

						// Updating publisher follower
						User u = publishers.get(selectedPublisher.get(c - 1).getName());
						Set<User> sub = u.getFollowers();
						if (sub != null) {
							sub.add(user);
							u.setFollowers(sub);
							User.updateUser(u);
						} else {
							sub = new HashSet<>();
							sub.add(user);
							u.setFollowers(sub);
							User.updateUser(u);
						}
					}
				}
			}
		} else if (choice == 2 || (choice == 1 && userInterest.isEmpty())) {
			selectedPublisher.addAll(publishers.values());
			while (true) {
				int i = 1;
				for (Entry<String, User> entry : publishers.entrySet()) {
					User u = entry.getValue();
					if (u != null) {
						String name = u.getName();
						if (subscribedPublishers.contains(u))
							System.out.println(i + ". " + name + " - subscribed");
						else
							System.out.println(i + ". " + name);
						i++;
					}
				}
				System.out.println(i + ". Exit");
				System.out.println("Choose publisher: ");
				int c = sc.nextInt();
				if (c > i || c <= 0) {
					System.out.println("Invalid Choice");
				} else if (c == i) {
					return choosePublishers(user, publishers, sc, subscribedPublishers);
				} else {
					subscribedPublishers.add(selectedPublisher.get(c - 1));

					// Updating publisher follower
					User u = publishers.get(selectedPublisher.get(c - 1).getName());
					Set<User> sub = u.getFollowers();
					if (sub != null) {
						sub.add(user);
						u.setFollowers(sub);
						User.updateUser(u);
					} else {
						sub = new HashSet<>();
						sub.add(user);
						u.setFollowers(sub);
						User.updateUser(u);
					}
				}
			}

		} else {
			return subscribedPublishers;
		}

		return subscribedPublishers;
	}

}
