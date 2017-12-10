package com.pubsub;

import java.util.HashMap;
import java.util.Map;

import com.pubsub.utils.PubSubConstants;

public class User implements Publishers{
	
	private String name;
	private String password;
	private Map<String,Integer> userInterest;
	
	public User(){
		initializeInterest();
		initializePublishers();
	}
	
	private void initializeInterest() {
		// TODO Auto-generated method stub
		userInterest = new HashMap<>();
		
		userInterest.put(PubSubConstants.FOOD_AND_CULTURE, 0);
		userInterest.put(PubSubConstants.ALIENS, 0);
		userInterest.put(PubSubConstants.ART, 0);
		userInterest.put(PubSubConstants.BUSINESS, 0);
		userInterest.put(PubSubConstants.DESIGN, 0);
		userInterest.put(PubSubConstants.HUMANITIES, 0);
		userInterest.put(PubSubConstants.SCIENCE_AND_TECH, 0);
		userInterest.put(PubSubConstants.LITERATURE, 0);
		userInterest.put(PubSubConstants.SPORTS, 0);
		userInterest.put(PubSubConstants.TECH, 0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void initializePublishers() {
		// TODO Auto-generated method stub
		interest.add("Joanie Faletto");
		interest.add("Ashley Hamer");
		interest.add("Liya Amel");
		interest.add("Jacob Cullen");
		publishers.put(PubSubConstants.FOOD_AND_CULTURE, interest);
		
		interest.clear();
		interest.add("Kristena Hemswarth");
		interest.add("Ashley Hamer");
		interest.add("Joe Farnando");
		publishers.put(PubSubConstants.ART, interest);
		
		interest.clear();
		interest.add("Enna Stuart");
		interest.add("Rehan Charles");
		interest.add("Mayra Silbester");
		interest.add("Suhasini Stephen");
		publishers.put(PubSubConstants.SCIENCE_AND_TECH, interest);
		
		interest.clear();
		interest.add("Enna Stuart");
		interest.add("Mayra Silbester");
		interest.add("Suhasini Stephen");
		publishers.put(PubSubConstants.ALIENS, interest);
		
		interest.clear();
		interest.add("Joe Farnando");
		interest.add("Spencer Haung");
		interest.add("Liya Amel");
		publishers.put(PubSubConstants.HUMANITIES, interest);
		
		interest.clear();
		interest.add("Joe Farnando");
		interest.add("Liya Amel");
		publishers.put(PubSubConstants.LITERATURE, interest);
		
		interest.clear();
		interest.add("Rehan Charles");
		interest.add("Suhasini Stephen");
		interest.add("Liya Amel");
		publishers.put(PubSubConstants.DESIGN, interest);
		
		interest.clear();
		interest.add("Soumya Reddy");
		interest.add("Spencer Haung");
		publishers.put(PubSubConstants.SPORTS, interest);
		
		interest.clear();
		interest.add("Mayra Silbester");
		interest.add("Suhasini Stephen");
		interest.add("Robert Swan");
		publishers.put(PubSubConstants.TECH, interest);
		
		interest.clear();
		interest.add("Bob Cyrus");
		interest.add("Spencer Haung");
		interest.add("Issabella Stephen");
		publishers.put(PubSubConstants.BUSINESS, interest);
	}

}
