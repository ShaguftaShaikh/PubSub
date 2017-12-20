package com.pubsub;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.pubsub.dao.User;

public class FindPublishers {

	public void findPublisher(User user, Scanner sc) throws IOException {
		System.out.println("Enter name of the publisher: ");
		String name = sc.nextLine();

		Map<String, User> allPublishers = User.readPublisher();
		allPublishers.remove("initializedArticles");
		allPublishers.remove("initiallizedFollowers");

		Set<String> publishersName = allPublishers.keySet();
		Set<User> matchList = new HashSet<>();

		if (publishersName.contains(name)) {
			matchList.add(allPublishers.get(name));
		}

		Iterator<String> itr = publishersName.iterator();
		while (itr.hasNext()) {
			String publisherName = itr.next();
			int len = publisherName.length();
			for (int i = 0; i < len; i++) {
				if (publisherName.regionMatches(true, i, name, 0, name.length())) {
					matchList.add(allPublishers.get(publisherName));
				}
			}
		}

		System.out.println("List of found publishers are: ");
		Iterator<User> itrUser = matchList.iterator();
		int i = 1;
		while (itrUser.hasNext()) {
			User u = itrUser.next();
			if (user.getSubscribedPublishers().contains(u)) {
				System.out.println(i + ". " + u.getName() + " - subscribed");
			} else {
				System.out.println(i + ". " + u.getName());
			}
			i++;
		}
		System.out.println();
		System.out.println("Want to follow them?(y/n): ");
		char ch = sc.next().charAt(0);
		if (ch == 'y') {
			User publisher = null;
			while (true) {
				itrUser = matchList.iterator();
				User[] users = new User[matchList.size()];
				i = 0;
				while (itrUser.hasNext()) {
					users[i] = itrUser.next();
					if (user.getSubscribedPublishers().contains(users[i])) {
						System.out.println(i + 1 + ". " + users[i].getName() + " - subscribed");
					} else {
						System.out.println(i + 1 + ". " + users[i].getName());
					}
					i++;
				}
				System.out.println(i + 1 + ". Exit");
				System.out.println("Enter your choice: ");
				int choice = sc.nextInt();
				if (choice > i + 1 || choice < 1) {
					System.out.println("Inavlid choice");
				} else if (choice == i + 1) {
					User.updateUser(user);
					if (publisher != null)
						User.updateUser(publisher);
					break;
				} else {
					User u = users[choice - 1];
					Set<User> subscribedPublisher = user.getSubscribedPublishers();
					publisher = allPublishers.get(u.getName());
					Set<User> publisherUser = publisher.getFollowers();

					if (subscribedPublisher != null) {
						subscribedPublisher.add(u);
						user.setFollowers(subscribedPublisher);
					} else {
						subscribedPublisher = new HashSet<>();
						subscribedPublisher.add(u);
						user.setFollowers(subscribedPublisher);
					}

					if (publisherUser != null) {
						publisherUser.add(user);
						publisher.setFollowers(publisherUser);
					} else {
						publisherUser = new HashSet<>();
						publisherUser.add(user);
						publisher.setFollowers(publisherUser);
					}
				}
			}
		} else if (ch == 'n') {

		} else {
			System.out.println("Invalid choice");
		}
	}
}
