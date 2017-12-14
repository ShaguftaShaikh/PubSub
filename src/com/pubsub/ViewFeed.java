package com.pubsub;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import com.pubsub.dao.PublisherArticle;
import com.pubsub.dao.User;

public class ViewFeed {

	public static void showFeeds(User user, Scanner sc) {
		Set<String> userIntereset = user.getUserInterest();
		Set<String> subscribedPublisher = user.getSubscribedPublishers();

		if ((userIntereset == null || userIntereset.isEmpty())
				&& (subscribedPublisher == null || subscribedPublisher.isEmpty())) {

		} else {
			List<PublisherArticle> articlesMapping = new ArrayList<>();

			if (!userIntereset.isEmpty()) {
				Iterator<String> itr = userIntereset.iterator();
				while (itr.hasNext()) {
					String interest = itr.next();
					List<PublisherArticle> allArticles = PublisherArticle.readArticles();
					for (PublisherArticle article : allArticles) {
						if (article.getArticleCategory().equalsIgnoreCase(interest)) {
							articlesMapping.add(article);
						}
					}
				}
			}
			if (!subscribedPublisher.isEmpty()) {
				Iterator<String> itr = subscribedPublisher.iterator();
				while (itr.hasNext()) {
					String publisher = itr.next();
					List<PublisherArticle> allArticles = PublisherArticle.readArticles();
					for (PublisherArticle article : allArticles) {
						User u = article.getPublishedBy();
						if (u.getName().equalsIgnoreCase(publisher)) {
							articlesMapping.add(article);
						}
					}
				}
			}
			int i = 1;
			while (true) {
				for (PublisherArticle publisherArticle : articlesMapping) {
					System.out.println(i + ". " + publisherArticle.getArticleTitle());
					i++;
				}
				System.out.println(i + ". Exit");
				System.out.println("Enter your choice to view the article: ");
				int choice = sc.nextInt();
				if (choice > i || choice < 1) {
					System.out.println("Invalid Choice!");
				} else if (choice == i) {

				} else {
					new Editor(articlesMapping.get(choice - 1).getArticle());
				}
			}
		}
	}
}
