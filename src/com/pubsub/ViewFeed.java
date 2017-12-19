package com.pubsub;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.pubsub.dao.PublisherArticle;
import com.pubsub.dao.User;

public class ViewFeed {

	public static void showFeeds(User user, Scanner sc) {
		Set<String> userIntereset = user.getUserInterest();
		Set<User> subscribedPublisher = user.getSubscribedPublishers();

		List<PublisherArticle> allArticles = PublisherArticle.readArticles();

		if ((userIntereset == null || userIntereset.isEmpty())
				&& (subscribedPublisher == null || subscribedPublisher.isEmpty())) {
			int i = 1;
			for (PublisherArticle article : allArticles) {
				System.out.println(i + ". " + article.getArticleTitle() + " - " 
						+ article.getArticleCategory() + " by "
						+ article.getPublishedBy().getName());
				i++;
			}
			System.out.println(i + ". Exit");
			System.out.println("Choose Article to read: ");
			int choice = sc.nextInt();
			if (choice > i || choice < 1) {
				System.out.println("Invalid Choice!");
			} else if (choice == i) {

			} else {
				new Editor().readArticle(allArticles.get(choice - 1));
			}
		} else {
			List<PublisherArticle> articlesMapping = new ArrayList<>();

			if (!userIntereset.isEmpty()) {
				Iterator<String> itr = userIntereset.iterator();
				while (itr.hasNext()) {
					String interest = itr.next();
					for (PublisherArticle article : allArticles) {
						if (article.getArticleCategory().equalsIgnoreCase(interest)) {
							articlesMapping.add(article);
						}
					}
				}
			}
			if (!subscribedPublisher.isEmpty()) {
				Iterator<User> itr = subscribedPublisher.iterator();
				while (itr.hasNext()) {
					String publisher = itr.next().getName();
					for (PublisherArticle article : allArticles) {
						User u = article.getPublishedBy();
						if (u.getName().equalsIgnoreCase(publisher)) {
							articlesMapping.add(article);
						}
					}
				}
			}

			while (true) {
				int i = 1;
				for (PublisherArticle publisherArticle : articlesMapping) {
					System.out.println(i + ". " + publisherArticle.getArticleTitle() + " - "
							+ publisherArticle.getArticleCategory() + " by "
							+ publisherArticle.getPublishedBy().getName());
					i++;
				}
				System.out.println(i + ". Exit");
				System.out.println("Enter your choice to view the article: ");
				int choice = sc.nextInt();
				if (choice > i || choice < 1) {
					System.out.println("Invalid Choice!");
				} else if (choice == i) {

				} else {
					new Editor().readArticle(articlesMapping.get(choice - 1));
				}
			}
		}
	}
}
