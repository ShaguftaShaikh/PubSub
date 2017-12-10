package com.pubsub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Publishers {

	static List<String> interest = new ArrayList<>();
	static Map<String, List<String>> publishers = new HashMap<>();
	
	public void initializePublishers();
}
