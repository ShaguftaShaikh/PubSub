package com.pubsub;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.pubsub.dao.User;

public class Profile {

	public static void userProfileMenu(User user, Scanner sc) {
		System.out.println("Name: " + user.getName());
		System.out.println();
		try {
			System.out.println("Number of Articles Published: " + user.getPublishedArticles().size());
		} catch (NullPointerException e) {
			System.out.println("Number of Articles Published: 0");
		}
		System.out.println();
		System.out.println("Intersted Category: ");
		Set<String> category = user.getUserInterest();
		if (category.size() > 0) {
			Iterator<String> itr = category.iterator();
			while (itr.hasNext()) {
				System.out.println(itr.next());
			}
		} else {
			System.out.println("No interested category found");
		}
		Set<User> users = user.getSubscribedPublishers();
		System.out.println();
		System.out.println("Subscribed Publishers: ");
		if (users.size() > 0) {
			Iterator<User> itr = users.iterator();
			while (itr.hasNext()) {
				System.out.println(itr.next().getName());
			}
		} else {
			System.out.println("No subscribed publishers found");
		}
	}
}
