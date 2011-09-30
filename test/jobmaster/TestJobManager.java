package jobmaster;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestJobManager {
	
	@Before
	public void runBefore() {
		File jobDir = new File("jobs");		
		delete(jobDir);
		File skeletonDir = new File("skeleton");
		delete(skeletonDir);
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
	
	@Test
	public void testSaveJobCopiesSkeleton() {
		File skeletonDir = new File("skeleton");
		skeletonDir.mkdir();
		File bone = new File("skeleton/bone1");
		try {
			bone.createNewFile();
		} catch (IOException e) {
			fail("Failed to prepare skeleton directory.");
		}
		
		Job job = new Job("New job.");
		JobManager.saveJob(job);
		boolean result = new File("jobs/1/bone1").exists();
		assertTrue("File should exist.", result);
	}
	
	@Test
	public void testAddHistoryToExistingJobCreatesFile() {
		Job job = new Job("New job.");
		JobManager.saveJob(job);
		JobManager.addHistory(job, "New history line.");
		File file = new File("jobs/1/history.log");
		assertTrue("File should exist.", file.exists());
	}
	
	@Test
	public void testAddHistoryToExistingJobAppendsToFile() {
		Job job = new Job("New job.");
		JobManager.saveJob(job);
		JobManager.addHistory(job, "1");
		JobManager.addHistory(job, "2");
		File file = new File("jobs/1/history.log");
		long size = file.length();
		assertEquals("Size should be equal to 4", 4, size);
	}
	
}
