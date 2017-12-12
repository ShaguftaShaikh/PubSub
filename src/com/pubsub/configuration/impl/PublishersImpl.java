package com.pubsub.configuration.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pubsub.configuration.Publishers;
import com.pubsub.utils.PubSubConstants;

public class PublishersImpl implements Publishers {

	List<String> interest = new ArrayList<>();
	Map<String,List<String>> publishersInterest = new HashMap<>();

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<String>> initializePublishersInterest() throws IOException {
		// TODO Auto-generated method stub
		FileInputStream fileInputStream;
		try {
			if (publishersInterest == null) {
				fileInputStream = new FileInputStream(PubSubConstants.PUBLISHERS_FILE);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				publishersInterest = (HashMap<String, List<String>>) objectInputStream.readObject();
				objectInputStream.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			FileOutputStream fileOutputStream = new FileOutputStream(PubSubConstants.PUBLISHERS_FILE);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			interest.add(PubSubConstants.FOOD_AND_CULTURE);
			publishersInterest.put("Joanie Faletto", interest);

			interest.add(PubSubConstants.ART);
			publishersInterest.put("Ashley Hamer", interest);

			interest.clear();
			interest.add(PubSubConstants.SCIENCE_AND_TECH);
			interest.add(PubSubConstants.ALIENS);
			publishersInterest.put("Enna Stuart", interest);

			interest.clear();
			interest.add(PubSubConstants.HUMANITIES);
			interest.add(PubSubConstants.LITERATURE);
			interest.add(PubSubConstants.ART);
			publishersInterest.put("Joe Farnando", interest);

			interest.clear();
			interest.add(PubSubConstants.SCIENCE_AND_TECH);
			interest.add(PubSubConstants.DESIGN);
			publishersInterest.put("Rehan Charles", interest);

			interest.clear();
			interest.add(PubSubConstants.SPORTS);
			publishersInterest.put("Soumya Reddy", interest);

			interest.clear();
			interest.add(PubSubConstants.TECH);
			interest.add(PubSubConstants.SCIENCE_AND_TECH);
			interest.add(PubSubConstants.ALIENS);
			publishersInterest.put("Mayra Silbester", interest);

			interest.clear();
			interest.add(PubSubConstants.ALIENS);
			interest.add(PubSubConstants.SCIENCE_AND_TECH);
			interest.add(PubSubConstants.TECH);
			interest.add(PubSubConstants.DESIGN);
			publishersInterest.put("Suhasini Stephen", interest);

			interest.clear();
			interest.add(PubSubConstants.BUSINESS);
			publishersInterest.put("Bob Cyrus", interest);

			interest.clear();
			interest.add(PubSubConstants.BUSINESS);
			interest.add(PubSubConstants.SPORTS);
			interest.add(PubSubConstants.HUMANITIES);
			publishersInterest.put("Spencer Haung", interest);
			
			interest.clear();
			interest.add(PubSubConstants.FOOD_AND_CULTURE);
			interest.add(PubSubConstants.DESIGN);
			interest.add(PubSubConstants.HUMANITIES);
			interest.add(PubSubConstants.LITERATURE);
			publishersInterest.put("Liya Amel", interest);
			
			interest.clear();
			interest.add(PubSubConstants.TECH);
			publishersInterest.put("Robert Swan", interest);
			
			interest.clear();
			interest.add(PubSubConstants.ART);
			publishersInterest.put("Kristena Hemswarth", interest);
			
			interest.clear();
			interest.add(PubSubConstants.BUSINESS);
			publishersInterest.put("Issabella Stephen", interest);
			
			interest.clear();
			interest.add(PubSubConstants.FOOD_AND_CULTURE);
			publishersInterest.put("Jacob Cullen", interest);

			objectOutputStream.writeObject(publishersInterest);
			objectOutputStream.close();

			// System.out.println(publishers);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return publishersInterest;
	}

}
