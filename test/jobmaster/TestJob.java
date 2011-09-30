package jobmaster;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class TestJob {
	
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
		Job.saveJob("New job.");
		File mainDir = new File("jobs");
		assertTrue("Should exist and should be directory.", mainDir.isDirectory());
	}
	
	@Test
	public void testSaveJobCreatesMainAndJobDirectories() {
		Job.saveJob("New job.");
		File mainDir = new File("jobs/1");
		assertTrue("Should exist and should be directory.", mainDir.isDirectory());
	}
	
	@Test
	public void testSaveJobCreatesNextJobDirectory() {
		Job.saveJob("First");
		Job.saveJob("Second");
		Job.saveJob("Third");
		File mainDir = new File("jobs/2");
		assertTrue("Should exist and should be directory.", mainDir.isDirectory());
	}
	
	@Test
	public void testSaveJobCreatesPropertiesFile() {
		Job.saveJob("New job.");
		File propertiesFile = new File("jobs/1/job.properties");
		assertTrue("File should exist.", propertiesFile.exists());
	}
	
	@Test
	public void testSaveJobPropertiesContainsId() {
		Job.saveJob("New job.");
		Properties prop = new Properties();
		try {
			FileReader fr = new FileReader(new File("jobs/1/job.properties")); 
			prop.load(fr);
			fr.close();
			String id = prop.getProperty("id");
			assertEquals("Id should be 1.", "1", id);			
		} catch (IOException e) {
			fail("Exception during read.");
		}
	}
	
	@Test
	public void testSaveJobPropertiesContainsTitle() {
		Job.saveJob("New job.");
		Properties prop = new Properties();
		try {
			FileReader fr = new FileReader(new File("jobs/1/job.properties"));
			prop.load(fr);
			fr.close();
			String title = prop.getProperty("title");
			assertEquals("Title should be \"New job.\"", "New job.", title);		
		} catch (IOException e) {
			fail("Exception during file read.");
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
		
		Job.saveJob("New job.");
		boolean result = new File("jobs/1/bone1").exists();
		assertTrue("File should exist.", result);
	}
	
	@Test
	public void testJobGetTitleEmpty() {
		Job job = Job.saveJob("");
		String expected = "";
		String result = job.getTitle();
		assertEquals("Strings should be equal.", expected, result);
	}

	@Test
	public void testJobGetTitle() {
		Job job = Job.saveJob("New job.");
		String expected = "New job.";
		String result = job.getTitle();
		assertEquals("Strings should be equal.", expected, result);
	}
	
	@Test
	public void testJobGetTitleNull() {
		Job job = Job.saveJob(null);
		String expected = "";
		String result = job.getTitle();
		assertEquals("Strings should be equal", expected, result);
	}
	
	@Test
	public void testAddHistoryToExistingJobCreatesFile() {
		Job job = Job.saveJob("New job.");
		job.addHistory("New history line.");
		File file = new File("jobs/1/history.log");
		assertTrue("File should exist.", file.exists());
	}
	
	@Test
	public void testAddHistoryToExistingJobAppendsToFile() {
		Job job = Job.saveJob("New job.");
		job.addHistory("1");
		job.addHistory("2");
		File file = new File("jobs/1/history.log");
		long size = file.length();
		assertEquals("Size should be equal to 4", 4, size);
	}
	
	@Test
	public void testAddHistoryToSecondJob() {
		Job job1 = Job.saveJob("First job.");
		Job job2 = Job.saveJob("Second job.");
		job1.addHistory("first");
		job2.addHistory("second");
		File file = new File("jobs/2/history.log");
		assertTrue("File should exist.", file.exists());
	}
	
	@Test
	public void testGetJobDoesNotExist() {
		Job job = Job.getJob(1);
		assertNull("Should be null.", job);
	}
	
	@Test
	public void testGetJobExists() {
		Job.saveJob("New job.");
		Job job = Job.getJob(1);
		assertNotNull("Should not be null.", job);
	}
	
	@Test
	public void testGetJobReturnsSameJob() {
		Job job1 = Job.saveJob("New job.");
		Job job2 = Job.getJob(1);
		assertEquals("Should be equal.", job1, job2);
	}
	
	@Test
	public void testGetAllJobsWhenNoJobs() {
		List<Job> jobs = Job.getJobs();
		assertEquals("Size should be 0.", 0, jobs.size());
	}
	
	@Test
	public void testGetAllJobsWhenTwoJobs() {
		Job.saveJob("New job 1.");
		Job.saveJob("New job 2.");
		List<Job> jobs = Job.getJobs();
		assertEquals("Size should be 2.", 2, jobs.size());
	}
	
}
