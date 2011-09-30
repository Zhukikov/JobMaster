package jobmaster;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Job {

	private static final String JOBS_DIR_NAME = "jobs";
	private static final String JOB_PROPERTIES_NAME = "job.properties";

	private Properties properties;

	private Job(int id, String newTitle) {
		this.properties = new Properties();
		this.properties.setProperty("id", String.valueOf(id));
		if (newTitle != null)
			this.properties.setProperty("title", newTitle);
		else
			this.properties.setProperty("title", "");
	}

	public void saveProperties() {
		String id = this.properties.getProperty("id");
		File propertiesFile = new File(JOBS_DIR_NAME + "/" + id + "/"
				+ JOB_PROPERTIES_NAME);
		try {
			FileWriter fw = new FileWriter(propertiesFile, true);
			this.properties.store(fw, "Job properties file");
			fw.close();
		} catch (IOException e) {
			// TODO
		}
	}

	public static Job saveJob(String title) {
		int id = getNewJobId();
		String jobDirName = JOBS_DIR_NAME + "/" + id;
		File jobDir = new File(jobDirName);
		jobDir.mkdirs();
		Job job = new Job(id, title);
		job.saveProperties();
		Skeleton.copyFiles(jobDirName);
		return job;

	}

	private static int getNewJobId() {
		File mainDir = new File(JOBS_DIR_NAME);
		if (!mainDir.isDirectory()) {
			return 1;
		}
		String[] dirList = mainDir.list();
		if (dirList.length == 0) {
			return 1;
		}
		Arrays.sort(dirList);
		int lastId = Integer.parseInt(dirList[dirList.length - 1]);
		return ++lastId;
	}

	public Properties getProperties() {
		return this.properties;
	}

	public String getTitle() {
		return this.properties.getProperty("title");
	}

}
