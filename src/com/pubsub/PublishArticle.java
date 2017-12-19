package com.pubsub;

import java.util.Scanner;

import com.pubsub.dao.User;

public class PublishArticle {

	public void publishArticle(User user,Scanner sc){
		new Editor().writeArticle(user);
	}
}
