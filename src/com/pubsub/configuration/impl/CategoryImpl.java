package com.pubsub.configuration.impl;

import java.util.ArrayList;
import java.util.List;

import com.pubsub.configuration.Category;
import com.pubsub.utils.PubSubConstants;

public class CategoryImpl implements Category{
	
	@Override
	public List<String> getCategoryList() {
		// TODO Auto-generated method stub
		List<String> categoryList = new ArrayList<String>();
		
		categoryList.add(PubSubConstants.FOOD_AND_CULTURE);
		categoryList.add(PubSubConstants.ALIENS);
		categoryList.add(PubSubConstants.ART);
		categoryList.add(PubSubConstants.BUSINESS);
		categoryList.add(PubSubConstants.DESIGN);
		categoryList.add(PubSubConstants.HUMANITIES);
		categoryList.add(PubSubConstants.SCIENCE_AND_TECH);
		categoryList.add(PubSubConstants.LITERATURE);
		categoryList.add(PubSubConstants.SPORTS);
		categoryList.add(PubSubConstants.TECH);
		
		return categoryList;
	}

}
