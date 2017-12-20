package com.pubsub;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.pubsub.dao.User;
import com.pubsub.utils.PasswordHash;

public class Login {

	private static Map<String, User> allUsers = new HashMap<>();

	public void login(String username, String password, Scanner sc) throws IOException, ClassNotFoundException {
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

	public void landingMenu(Scanner sc, User user)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException {
		while (true) {
			System.out.println("1. View Feed");
			System.out.println("2. View/Edit Profile");
			if (user.isPublisher()) {
				System.out.println("3. Publish an Article");
			} else {
				System.out.println("3. Become a Publisher");
			}
			System.out.println("4. Find Publishers");
			System.out.println("5. Top 5 Publishers");
			System.out.println("6. Suggetions");
			System.out.println("7. Logout");
			System.out.println("Enter your choice: ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				new ViewFeed().showFeeds(user, sc);
				break;
			case 2:
				new Profile().userProfileMenu(user, sc);
				break;
			case 3:
				new PublishArticle().publishArticle(user, sc);
				break;
			case 4:
				sc.nextLine();
				new FindPublishers().findPublisher(user,sc);
				break;
			case 5:
				topPublishers(user, sc);
				break;
			case 6:
				suggetionOfPublishers(user, sc);
				break;
			case 7:
				MainMenu.mainmenu();
				break;
			default:
				System.out.println("Invalid choice");
			}
		}
	}

	private void suggetionOfPublishers(User user, Scanner sc) throws IOException {
		// TODO Auto-generated method stub

		System.out.println("1. Suggest top publishers");
		System.out.println("2. Show all publishers");
		System.out.println("3. Show publisher based on interest");
		System.out.println("4. Exit");

		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			topPublishers(user, sc);
			break;
		case 2:
			showAllPublishers(user, sc);
			break;
		case 3:
			showPublisherBasedOnInterest(user, sc);
			break;
		case 4:
			break;
		default:
			System.out.println("Invalid choice");
			break;
		}
	}

	private void showPublisherBasedOnInterest(User user, Scanner sc) throws IOException {
		// TODO Auto-generated method stub
		Map<String, User> allPublishers = User.readPublisher();
		allPublishers.remove("initializedArticles");
		allPublishers.remove("initiallizedFollowers");

		List<User> publishers = new ArrayList<>();
		publishers.addAll(allPublishers.values());

		Set<String> userInterest = user.getUserInterest();
		List<User> matchedInterest = new ArrayList<>();

		if (userInterest != null && !(userInterest.isEmpty())) {

			for (String interest : userInterest) {
				for (User u : publishers) {
					Set<String> publisherInterest = u.getUserInterest();
					if (publisherInterest.contains(interest)) {
						matchedInterest.add(u);
					}
				}
			}
			if (!matchedInterest.isEmpty()) {
				while (true) {
					int i = 1;
					for (User u : matchedInterest) {
						System.out.println(i + ". " + u.getName());
						i++;
					}
					System.out.println(i + ". Exit");
					System.out.println("Choose publisher: ");
					int choice = sc.nextInt();
					if (choice > i || choice < 1) {
						System.out.println("Invalid choice");
					} else if (choice == i) {
						break;
					} else {
						Set<User> subscribedPublishers = user.getSubscribedPublishers();
						if (subscribedPublishers != null) {
							subscribedPublishers.add(matchedInterest.get(choice - 1));
							user.setSubscribedPublishers(subscribedPublishers);
						} else {
							subscribedPublishers = new HashSet<>();
							subscribedPublishers.add(matchedInterest.get(choice - 1));
							user.setSubscribedPublishers(subscribedPublishers);
						}

						User u = allPublishers.get(matchedInterest.get(choice - 1).getName());
						Set<User> followers = u.getFollowers();
						if (followers != null) {
							followers.add(user);
							u.setFollowers(followers);
							allPublishers.put(u.getName(), u);
						} else {
							followers = new HashSet<>();
							followers.add(user);
							u.setFollowers(followers);
							allPublishers.put(u.getName(), u);
						}

						User.updateUser(u);
						User.updateUser(user);
					}
				}
			}
		} else {
			System.out.println("You did not set any interest");
			System.out.println("Want to add some?(y/n):");
		}
	}

	private void showAllPublishers(User user, Scanner sc) throws IOException {
		// TODO Auto-generated method stub
		Map<String, User> allPublishers = User.readPublisher();
		allPublishers.remove("initializedArticles");
		allPublishers.remove("initiallizedFollowers");

		boolean flag = false;

		List<User> publishers = new ArrayList<>();
		publishers.addAll(allPublishers.values());
		while (true) {
			int i = 1;
			for (User u : publishers) {
				if (u.getFollowers() == null) {
					System.out.println(i + ". " + u.getName() + " - 0 followers");
				} else {
					System.out.println(i + ". " + u.getName() + " - " + u.getFollowers().size() + " followers");
				}
				i++;
			}
			System.out.println(i + ". Exit");
			System.out.println("Want to follow some?(y/n): ");
			char ch = sc.next().charAt(0);
			if (ch == 'y') {
				while (true) {
					i = 1;
					for (User u : publishers) {
						if (u.getFollowers() == null) {
							System.out.println(i + ". " + u.getName() + " - 0 followers");
						} else {
							System.out.println(i + ". " + u.getName() + " - " + u.getFollowers().size() + " followers");
						}
						i++;
					}
					System.out.println(i + ". Exit");
					System.out.println("Choose publisher to follow: ");
					int choice = sc.nextInt();
					if (choice > i || choice < 1) {
						System.out.println("Invalid choice");
					} else if (choice == i) {
						User.updateUser(user);
						flag = true;
						break;
					} else {
						Set<User> subscribedPublishers = user.getSubscribedPublishers();
						if (subscribedPublishers != null) {
							subscribedPublishers.add(publishers.get(choice - 1));
							user.setSubscribedPublishers(subscribedPublishers);
						} else {
							subscribedPublishers = new HashSet<>();
							subscribedPublishers.add(publishers.get(choice - 1));
							user.setSubscribedPublishers(subscribedPublishers);
						}

						User u = allPublishers.get(publishers.get(choice - 1).getName());

						Set<User> followers = u.getFollowers();

						if (followers != null) {
							followers.add(user);
							u.setFollowers(followers);
							allPublishers.put(u.getName(), u);
						} else {
							followers = new HashSet<>();
							followers.add(user);
							u.setFollowers(followers);
							allPublishers.put(u.getName(), u);
						}

						User.updateUser(u);
						User.updateUser(user);
					}
				}
			} else if (ch == 'n') {
				break;
			} else {
				System.out.println("Invalid choice");
			}

			if (flag) {
				break;
			}
		}
	}

	private void topPublishers(User user, Scanner sc) throws IOException {
		// TODO Auto-generated method stub
		Map<String, User> publishers = User.readPublisher();
		publishers.remove("initializedArticles");
		publishers.remove("initiallizedFollowers");

		List<User> allPublishers = new ArrayList<>();
		allPublishers.addAll(publishers.values());

		boolean f = false;

		Collections.sort(allPublishers);
		while (true) {
			int i;
			for (i = 0; i < 5; i++) {
				String name = allPublishers.get(i).getName();
				int followers = allPublishers.get(i).getFollowers().size();
				System.out.println(i + 1 + ". " + name + " - " + followers + " followers");
			}
			System.out.println();
			System.out.println("Want to follow them?(y/n): ");
			char ch = sc.next().charAt(0);
			if (ch == 'y') {
				while (true) {
					for (i = 0; i < 5; i++) {
						String name = allPublishers.get(i).getName();
						int followers = allPublishers.get(i).getFollowers().size();
						System.out.println(i + 1 + ". " + name + " - " + followers + " followers");
					}
					System.out.println(i + 1 + ". Exit");

					System.out.println("Enter your choice: ");
					int choice = sc.nextInt();

					if (choice > i + 1 || choice < 1) {
						System.out.println("Invalid choice");
					} else if (choice == i + 1) {
						User.updateUser(user);
						f = true;
						break;
					} else {
						Set<User> subscribedPublishers = user.getSubscribedPublishers();
						subscribedPublishers.add(allPublishers.get(choice - 1));
						user.setSubscribedPublishers(subscribedPublishers);

						User u = publishers.get(allPublishers.get(choice - 1).getName());

						Set<User> followers = u.getFollowers();
						followers.add(user);
						u.setFollowers(followers);
						publishers.put(u.getName(), u);

						User.updateUser(u);

					}
				}
			} else if (ch == 'n') {
				break;
			} else {
				System.out.println("Invalid choice");
			}

			if (f) {
				break;
			}
		}
	}
}
