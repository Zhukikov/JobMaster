package jobmaster;

import java.util.Properties;

public class Job {
	
	private Properties properties;

	public Job(String newTitle) {
		this.properties = new Properties();
		if (newTitle != null)
			this.properties.setProperty("title", newTitle);
		else
			this.properties.setProperty("title", "");
	}
	
	public Properties getProperties() {
		return this.properties;
	}

	public String getTitle() {
		return this.properties.getProperty("title");
	}

}
