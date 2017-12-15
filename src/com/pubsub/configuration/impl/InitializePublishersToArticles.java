package com.pubsub.configuration.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pubsub.dao.PublisherArticle;
import com.pubsub.dao.User;

public class InitializePublishersToArticles implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1604914873967394516L;

	public static void initializePublisherToArticles() {

		List<PublisherArticle> articles = PublisherArticle.readArticles();
		Map<String, User> allUsers = User.readUser();

		
		for (Map.Entry<String, User> entry : allUsers.entrySet()) {
			User entryUser = entry.getValue();
			List<PublisherArticle> publishedArticles = new ArrayList<>();
			
			for (PublisherArticle article : articles) {
				User user = article.getPublishedBy();
				if(entryUser.getName().equalsIgnoreCase(user.getName())){
					publishedArticles.add(article);
				}
			}
			entry.setValue(entryUser);
		}
		try {
			User.writeUser(allUsers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
