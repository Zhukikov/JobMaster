package jobmaster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Properties;

public class JobManager {

	public static void saveJob(Job job) {
		String newJobDirName = String.valueOf(getNewJobId());
		File mainDir = new File("jobs/" + newJobDirName);
		mainDir.mkdirs();
		File propertiesFile = new File("jobs/" + newJobDirName
				+ "/job.properties");
		Properties prop = job.getProperties();
		prop.setProperty("id", newJobDirName);
		try {
			FileWriter fw = new FileWriter(propertiesFile, true);
			prop.store(fw, "Job properties file");
			fw.close();
		} catch (IOException e) {
			// TODO
		}

		File skeletonDir = new File("skeleton");
		if (skeletonDir.isDirectory()) {
			String[] list = skeletonDir.list();
			for (String bone : list) {
				File boneFile = new File("skeleton/" + bone);
				if (boneFile.exists()) {
					copyFile(boneFile, new File("jobs/" + newJobDirName + "/" + bone));
				}
			}
		}
	}

	private static void copyFile(File source, File dest) {
		if (!source.exists()) {
			return;
		}

		if (dest.exists()) {
			dest.delete();
		}
		
		try {
			dest.createNewFile();
	
			FileChannel sourceCh = null;
			FileChannel destinationCh = null;
			sourceCh = new FileInputStream(source).getChannel();
			destinationCh = new FileOutputStream(dest).getChannel();
			if (destinationCh != null && sourceCh != null) {
				destinationCh.transferFrom(sourceCh, 0, sourceCh.size());
			}
			if (sourceCh != null) {
				sourceCh.close();
			}
			if (destinationCh != null) {
				destinationCh.close();
			}
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

	public static void addHistory(Job job, String string) {
		String id = job.getProperties().getProperty("id");
		if (id == null || id.equals("")) {
			return;
		}
		File file = new File("jobs/" + id + "/history.log");
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file, true);
			fw.write(string + "\n");
			fw.close();
		} catch (IOException e) {
			// TODO
		}
	}

}
