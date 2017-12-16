package com.pubsub.configuration.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pubsub.configuration.InitializeApp;
import com.pubsub.dao.PublisherArticle;
import com.pubsub.dao.User;

public class InitializeAppImpl implements InitializeApp {

	@Override
	public void initializeDefaultArticles() {
		// TODO Auto-generated method stub
		List<PublisherArticle> articles = PublisherArticle.readArticles();

		if (articles == null) {
			articles = new ArrayList<>();
			try {
				FileReader fileReader = new FileReader(new File("resources/defaultArticles.txt"));
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

				String line = "";
				String publishedBy = "";
				while ((line = bufferedReader.readLine()) != null) {
					String[] str = line.split("\\|");

					PublisherArticle p = new PublisherArticle();
					publishedBy = str[0];
					p.setPublishingDate((Date) simpleDateFormat.parse(str[1]));
					p.setLikes(Integer.parseInt(str[2]));
					p.setArticleCategory(str[3]);
					p.setArticleTitle(str[4]);
					p.setArticle(str[5]);

					User user = User.getUserByName(publishedBy);
					p.setPublishedBy(user);

					articles.add(p);
				}
				bufferedReader.close();
				PublisherArticle.writeArticles(articles);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initializeUsers() {
		// TODO Auto-generated method stub
		Map<String, User> allUsers = User.readPublisher();

		if (allUsers == null) {
			allUsers = new HashMap<>();
			try {
				FileReader fileReader = new FileReader(new File("resources/users.txt"));
				BufferedReader bufferedReader = new BufferedReader(fileReader);

				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					String[] str = line.split(",");

					User u = new User();
					u.setName(str[0]);
					u.setPassword(str[1]);
					u.setPublisher(true);

					Set<String> list = new HashSet<>();

					for (int i = 2; i < str.length; i++) {
						list.add(str[i]);
					}
					u.setUserInterest(list);
					u.setSubscribedPublishers(null);

					allUsers.put(u.getName(), u);
				}
				bufferedReader.close();
				User.writePublisher(allUsers);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initializePublisherToArticles() {
		// TODO Auto-generated method stub
		List<PublisherArticle> articles = PublisherArticle.readArticles();
		Map<String, User> allUsers = User.readPublisher();

		if (!allUsers.containsKey("initializedArticles")) {

			for (Map.Entry<String, User> entry : allUsers.entrySet()) {
				User entryUser = entry.getValue();
				List<PublisherArticle> publishedArticles = new ArrayList<>();

				for (PublisherArticle article : articles) {
					User user = article.getPublishedBy();
					if (entryUser.getName().equalsIgnoreCase(user.getName())) {
						publishedArticles.add(article);
					}
				}
				entryUser.setPublishedArticles(publishedArticles);
				entry.setValue(entryUser);
			}
			allUsers.put("initializedArticles", null);
			try {
				User.writePublisher(allUsers);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void initializeApplicationUser() {
		Map<String, User> allUsers = User.readUser();
		if (allUsers == null) {
			allUsers = new HashMap<>();
			try {
				FileReader fileReader = new FileReader(new File("resources/normalUsers.txt"));
				BufferedReader bufferedReader = new BufferedReader(fileReader);

				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					String[] str = line.split("\\|");

					User u = new User();
					u.setName(str[0]);
					u.setPassword(str[1]);
					u.setPublisher(false);

					Set<String> list = new HashSet<>();
					try {
						String[] interest = str[2].split(",");
						for (int i = 0; i < interest.length; i++) {
							list.add(interest[i]);
						}
					} catch (Exception e) {

					}
					u.setUserInterest(list);
					Set<User> subscribedPublisher = new HashSet<>();

					if (str.length > 3) {
						if (str[3] != null) {
							String[] publisher = str[3].split(",");
							for (int i = 0; i < publisher.length; i++) {
								User user = User.getUserByName(publisher[i]);
								subscribedPublisher.add(user);
							}
						}
					}
					u.setSubscribedPublishers(subscribedPublisher);
					allUsers.put(u.getName(), u);
				}
				bufferedReader.close();
				User.writeUser(allUsers);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initializeFollowersToPublishers() {
		// TODO Auto-generated method stub
		Map<String, User> publishers = User.readPublisher();
		Map<String, User> users = User.readUser();

		if (!publishers.containsKey("initiallizedFollowers")) {

			for (Map.Entry<String, User> entry : users.entrySet()) {
				User user = entry.getValue();
				Set<User> subscribedPublishers = user.getSubscribedPublishers();

				Iterator<User> itr = subscribedPublishers.iterator();
				while (itr.hasNext()) {
					User u = itr.next();
					User publisher = publishers.get(u.getName());
					Set<User> publisherFollowers = publisher.getFollowers();
					if (publisherFollowers == null) {
						publisherFollowers = new HashSet<>();
						publisherFollowers.add(user);
					} else {
						publisherFollowers.add(user);
					}
					publisher.setFollowers(publisherFollowers);
					publishers.put(publisher.getName(), publisher);
				}
			}
			publishers.put("initiallizedFollowers", null);
			/*
			 * for (Entry<String, User> e : publishers.entrySet()) { User u
			 * =e.getValue(); System.out.println(u.getName());
			 * System.out.println(u.getFollowers());
			 * System.out.println(u.getUserInterest());
			 * System.out.println(u.getPublishedArticles());
			 * System.out.println(); }
			 */
			try {
				User.writePublisher(publishers);
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
	}
}
