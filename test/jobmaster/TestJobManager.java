package jobmaster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestJobManager {
	
	@Before
	public void runBefore() {
		File jobDir = new File("jobs");		
		delete(jobDir);
	}
	
	private static boolean delete(File oFile) {
		if (oFile.isDirectory()) {
			File[] aFiles = oFile.listFiles();
			for (File oFileCur : aFiles) {
				delete(oFileCur);
			}
		}
		return oFile.delete();
	}

	@Test
	public void testSaveJobCreatesMainDirectory() {
		Job job = new Job("New job.");
		JobManager.saveJob(job);
		File mainDir = new File("jobs");
		assertTrue("Should exist and should be directory.", mainDir.isDirectory());
	}
	
	@Test
	public void testSaveJobCreatesMainAndJobDirectories() {
		Job job = new Job("New job.");
		JobManager.saveJob(job);
		File mainDir = new File("jobs/1");
		assertTrue("Should exist and should be directory.", mainDir.isDirectory());
	}
	
	@Test
	public void testSaveJobCreatesNextJobDirectory() {
		Job job = new Job("New job.");
		JobManager.saveJob(job);
		JobManager.saveJob(job);
		JobManager.saveJob(job);
		File mainDir = new File("jobs/2");
		assertTrue("Should exist and should be directory.", mainDir.isDirectory());
	}
	
	@Test
	public void testSaveJobCreatesPropertiesFile() {
		Job job = new Job("New job.");
		JobManager.saveJob(job);
		File propertiesFile = new File("jobs/1/job.properties");
		assertTrue("File should exist.", propertiesFile.exists());
	}
	
	@Test
	public void testSaveJobPropertiesContainsId() {
		Job job = new Job("New job.");
		JobManager.saveJob(job);
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(new File("jobs/1/job.properties")));
			String id = prop.getProperty("id");
			assertEquals("Id should be 1.", "1", id);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveJobPropertiesContainsTitle() {
		Job job = new Job("New job.");
		JobManager.saveJob(job);
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(new File("jobs/1/job.properties")));
			String title = prop.getProperty("title");
			assertEquals("Title should be \"New job.\"", "New job.", title);		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
