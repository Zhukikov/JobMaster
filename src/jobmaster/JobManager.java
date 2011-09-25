package jobmaster;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class JobManager {

	public static void saveJob(Job job) {
		String newJobDirName = String.valueOf(getNewJobId());
		File mainDir = new File("jobs/" + newJobDirName);
		mainDir.mkdirs();
		File propertiesFile = new File("jobs/" + newJobDirName + "/job.properties");
		Properties prop = job.getProperties();
		prop.setProperty("id", newJobDirName);
		try {
			FileWriter fw = new FileWriter(propertiesFile, true);
			prop.store(fw, "Job properties file");
			fw.close();
		} catch (IOException e) {
			// TODO
		}
	}

	private static int getNewJobId() {		
		File mainDir = new File("jobs");
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

}
