package com.pubsub;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.pubsub.dao.User;

public class Profile {

	public static void userProfileMenu(User user, Scanner sc) {
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
			while (itr.hasNext()) {
				System.out.println(itr.next());
			}
		} else {
			System.out.println("No interested category found");
		}

		System.out.println();
		System.out.println("Subscribed Publishers: ");

		Set<User> users = user.getSubscribedPublishers();
		if (users != null) {
			if (users.size() > 0) {
				Iterator<User> itr = users.iterator();
				while (itr.hasNext()) {
					System.out.println(itr.next().getName());
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
			Iterator<User> itr = followers.iterator();
			while (itr.hasNext()) {
				System.out.println(itr.next().getName());
			}
		} else {
			System.out.println("No followers found");
		}

		System.out.println();
	}
}
