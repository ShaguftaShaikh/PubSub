package com.pubsub.configuration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Publishers {

	public Map<String, List<String>> initializePublishersInterest() throws IOException;
}
