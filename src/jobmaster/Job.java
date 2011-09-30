package jobmaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Job {

	private static final String JOBS_DIR_NAME = "jobs";
	private static final String JOB_PROPERTIES_NAME = "job.properties";
	private static final String HISTORY_NAME = "history.log";

	private Properties properties;

	private Job(int id, String newTitle) {
		this.properties = new Properties();
		this.properties.setProperty("id", String.valueOf(id));
		if (newTitle != null)
			this.properties.setProperty("title", newTitle);
		else
			this.properties.setProperty("title", "");
	}

	public Job(File jobDir) {
		try {
			FileReader fr = new FileReader(jobDir.getAbsolutePath() + "/"
					+ JOB_PROPERTIES_NAME);
			this.properties = new Properties();
			this.properties.load(fr);
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void addHistory(String string) {
		String id = this.properties.getProperty("id");

		File file = new File(JOBS_DIR_NAME + "/" + id + "/" + HISTORY_NAME);
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file, true);
			fw.write(string + "\n");
			fw.close();
		} catch (IOException e) {
			// TODO
		}
	}

	public static Job getJob(int i) {
		File jobsDir = new File(JOBS_DIR_NAME);
		if (!jobsDir.isDirectory()) {
			return null;
		}
		String[] list = jobsDir.list();
		for (String jobName : list) {
			if (jobName.equals("" + i)) {
				return new Job(new File(JOBS_DIR_NAME + "/" + jobName));
			}
		}
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Job) {
			return this.properties.equals(((Job) obj).getProperties());
		}
		return false;
	}

	public static List<Job> getJobs() {
		File jobsDir = new File (JOBS_DIR_NAME + "/");
		if (!jobsDir.isDirectory()) {
			return new ArrayList<Job>();
		}
		String[] list = jobsDir.list();
		ArrayList<Job> result = new ArrayList<Job>();
		for (String jobName : list) {
			result.add(new Job(new File(JOBS_DIR_NAME + "/" + jobName)));
		}
		return result;
	}

}
