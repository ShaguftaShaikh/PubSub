package com.pubsub;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.pubsub.configuration.impl.Editor;
import com.pubsub.dao.PublisherArticle;
import com.pubsub.dao.User;

public class ViewFeed {

	public void showFeeds(User user, Scanner sc) {
		Set<String> userIntereset = user.getUserInterest();
		Set<User> subscribedPublisher = user.getSubscribedPublishers();

		Map<String, List<PublisherArticle>> allArticles = PublisherArticle.readArticles();
		List<PublisherArticle> articlesList = new ArrayList<>();

		if (user.isPublisher() && subscribedPublisher == null) {
			displayAllFeeds(allArticles, articlesList, sc);
		} else {

			if ((userIntereset == null || userIntereset.isEmpty())
					&& (subscribedPublisher == null || subscribedPublisher.isEmpty())) {
				displayAllFeeds(allArticles, articlesList, sc);
				
			} else {
				List<PublisherArticle> articlesMapping = new ArrayList<>();

				if (!userIntereset.isEmpty()) {
					Iterator<String> itr = userIntereset.iterator();
					while (itr.hasNext()) {
						String interest = itr.next();
						for (List<PublisherArticle> articles : allArticles.values()) {
							for (PublisherArticle article : articles) {
								if (article.getArticleCategory().equalsIgnoreCase(interest)) {
									articlesMapping.add(article);
								}
							}
						}
					}
				}
				if (!subscribedPublisher.isEmpty()) {
					Iterator<User> itr = subscribedPublisher.iterator();
					while (itr.hasNext()) {
						String publisher = itr.next().getName();
						for (List<PublisherArticle> articles : allArticles.values()) {
							for (PublisherArticle article : articles) {
								User u = article.getPublishedBy();
								if (u.getName().equalsIgnoreCase(publisher)) {
									articlesMapping.add(article);
								}
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
						break;
					} else {
						new Editor().readArticle(articlesMapping.get(choice - 1));
					}
				}
			}
		}
	}
	
	public void displayAllFeeds(Map<String, List<PublisherArticle>> allArticles,List<PublisherArticle> articlesList,Scanner sc){
		System.out.println("You did not have subscribed to any publisher");
		System.out.println("displaying all feeds");
		while (true) {
			int i = 1;
			for (List<PublisherArticle> articles : allArticles.values()) {
				for (PublisherArticle article : articles) {
					System.out.println(i + ". " + article.getArticleTitle() + " - "
							+ article.getArticleCategory() + " by " + article.getPublishedBy().getName());
					articlesList.add(article);
					i++;
				}
			}
			System.out.println(i + ". Exit");
			System.out.println("Choose Article to read: ");
			int choice = sc.nextInt();
			if (choice > i || choice < 1) {
				System.out.println("Invalid Choice!");
			} else if (choice == i) {
				break;
			} else {
				new Editor().readArticle(articlesList.get(choice - 1));
			}
		}
	}
}
