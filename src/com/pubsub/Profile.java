package com.pubsub;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.pubsub.dao.User;

public class Profile {

	public void userProfileMenu(User user, Scanner sc)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException {
		System.out.println("Name: " + user.getName());
		System.out.println();

		if (user.isPublisher()) {
			if (user.getPublishedArticles() != null) {
				System.out.println("Number of Articles Published: " + user.getPublishedArticles().size());
			}
		} else {
			System.out.println("Number of Articles Published: 0");
		}
		System.out.println();
		System.out.println("Intersted Category: ");
		Set<String> category = user.getUserInterest();
		if (category.size() > 0) {
			Iterator<String> itr = category.iterator();
			int i = 1;
			while (itr.hasNext()) {
				System.out.println(i + ". " + itr.next());
				i++;
			}
		} else {
			System.out.println("No interested category found");
		}

		System.out.println();
		System.out.println("Subscribed Publishers: ");

		Set<User> users = user.getSubscribedPublishers();
		if (users != null) {
			if (users.size() > 0) {
				int i = 1;
				Iterator<User> itr = users.iterator();
				while (itr.hasNext()) {
					System.out.println(i + ". " + itr.next().getName());
					i++;
				}
			} else {
				System.out.println("No subscribed publishers found");
			}
		} else {
			System.out.println("No subscribed publishers found");
		}
		System.out.println();

		System.out.println("Followers: ");
		Set<User> followers = user.getFollowers();
		if (followers != null) {
			System.out.println("Number of followers: " + followers.size());
			System.out.println();
			int i = 1;
			Iterator<User> itr = followers.iterator();
			while (itr.hasNext()) {
				System.out.println(i + ". " + itr.next().getName());
				i++;
			}
		} else {
			System.out.println("No followers found");
		}

		System.out.println();
		while (true) {
			System.out.println("Edit profile?(y/n): ");
			char ch = sc.next().charAt(0);
			if (ch == 'y') {
				while (true) {
					System.out.println("1. Edit publishers you follow");
					System.out.println("2. Edit category you are interested in");
					System.out.println("3. Remove article you have publsihed");
					System.out.println("4. Change password");
					System.out.println("5. Exit");

					int choice = sc.nextInt();
					switch (choice) {
					case 1:
						user = editUserSubscribedPublishers(user, sc);
						if (!user.isPublisher()) {
							Map<String, User> allUser = User.readUser();
							allUser.put(user.getName(), user);
							User.writeUser(allUser);
						} else {
							Map<String, User> allUser = User.readPublisher();
							allUser.put(user.getName(), user);
							User.writePublisher(allUser);
						}
						break;
					case 2:
						user = editInterestedCategory(user, sc);
						if (!user.isPublisher()) {
							Map<String, User> allUser = User.readUser();
							allUser.put(user.getName(), user);
							User.writeUser(allUser);
						} else {
							Map<String, User> allUser = User.readPublisher();
							allUser.put(user.getName(), user);
							User.writePublisher(allUser);
						}
						break;
					case 3:
						break;
					case 4:
						break;
					case 5:
						new Login().landingMenu(sc, user);
						break;
					default:
						System.out.println("Invalid choice");
					}
				}
			} else if (ch == 'n') {
				break;
			} else {
				System.out.println("Inavlid Input");
			}
		}
	}

	public User editUserSubscribedPublishers(User user, Scanner sc) {

		System.out.println("1. Unsubscribe a publisher");
		System.out.println("2. Subscribe a publisher");
		System.out.println("3. Exit");

		int c = sc.nextInt();
		if (c == 1) {
			return unSubScribePublisher(user, sc);
		} else if (c == 2) {
			return subscribePublisher(user, sc);
		} else {
			return user;
		}
	}

	public User editInterestedCategory(User user, Scanner sc)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
		System.out.println("1. Remove Category");
		System.out.println("2. Add Category");
		System.out.println("3. Exit");

		int c = sc.nextInt();
		if (c == 1) {
			return removeCategory(user, sc);
		} else if (c == 2) {
			return addCategory(user, sc);
		} else {
			return user;
		}

	}

	public User unSubScribePublisher(User user, Scanner sc) {
		Set<User> subscribedPublishers = user.getSubscribedPublishers();
		if (subscribedPublishers != null && !(subscribedPublishers.isEmpty())) {
			while (true) {
				System.out.println("Subscribed publishers are: ");
				Iterator<User> itr = subscribedPublishers.iterator();
				User[] str = new User[subscribedPublishers.size()];
				int i = 0;
				while (itr.hasNext()) {
					str[i] = itr.next();
					System.out.println(i + 1 + ". " + str[i].getName());
					i++;
				}
				System.out.println(i + 1 + ". Exit");

				System.out.println("Choose to unsubscribe: ");
				int choice = sc.nextInt();
				if (choice > i + 1 || choice < 1) {
					System.out.println("Invalid choice");
				} else if (choice == i + 1) {
					return user;
				} else {
					System.out.println(str[choice - 1] + " has sucessfully unsubscribed");
					subscribedPublishers.remove(str[choice - 1]);
					user.setSubscribedPublishers(subscribedPublishers);
				}
			}
		} else {
			System.out.println("You do not have any subscribed publisher");
			System.out.println("Want to follow some?(y/n): ");
			char ch = sc.next().charAt(0);
			if (ch == 'y') {
				Map<String, User> publishers = User.readPublisher();
				user.setSubscribedPublishers(new SignUp().choosePublishers(user, publishers, sc, subscribedPublishers));
			} else if (ch == 'n') {
				return user;
			} else {
				System.out.println("Invalid choice");
			}
		}
		return user;
	}

	public User subscribePublisher(User user, Scanner sc) {
		Set<User> subscribedPublishers = user.getSubscribedPublishers();
		Map<String, User> publishers = User.readPublisher();
		user.setSubscribedPublishers(new SignUp().choosePublishers(user, publishers, sc, subscribedPublishers));
		return user;
	}

	public User removeCategory(User user, Scanner sc)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
		Set<String> userInterest = user.getUserInterest();
		if (userInterest != null && !(userInterest.isEmpty())) {
			while (true) {
				System.out.println("Interested Categories are: ");
				Iterator<String> itr = userInterest.iterator();
				String[] str = new String[userInterest.size()];
				int i = 0;
				while (itr.hasNext()) {
					str[i] = itr.next();
					System.out.println(i + 1 + ". " + str[i]);
					i++;
				}
				System.out.println(i + 1 + ". Exit");

				System.out.println("Choose to remove: ");
				int choice = sc.nextInt();
				if (choice > i + 1 || choice < 1) {
					System.out.println("Invalid choice");
				} else if (choice == i + 1) {
					return user;
				} else {
					System.out.println(str[choice - 1] + " has sucessfully removed");
					userInterest.remove(str[choice - 1]);
					user.setUserInterest(userInterest);
				}
			}
		} else {
			System.out.println("You do not have set any interest");
			System.out.println("Want to add some?(y/n): ");
			char ch = sc.next().charAt(0);
			if (ch == 'y') {
				return new SignUp().chooseInterest(user, sc);
			} else if (ch == 'n') {
				return user;
			} else {
				System.out.println("Invalid choice");
			}
		}

		return user;
	}

	public User addCategory(User user, Scanner sc)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
		Set<String> userInterest = user.getUserInterest();
		User u = new SignUp().chooseInterest(user, sc);
		userInterest.addAll(u.getUserInterest());
		user.setUserInterest(userInterest);
		return user;
	}
}
