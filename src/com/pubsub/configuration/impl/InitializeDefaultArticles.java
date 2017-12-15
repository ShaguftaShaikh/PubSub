package com.pubsub.configuration.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pubsub.dao.PublisherArticle;
import com.pubsub.dao.User;

public class InitializeDefaultArticles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;
	

	public static void initializeDefaultArticles() {
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
					
					User user = new User();
					user = user.getUserByName(publishedBy);
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

}
