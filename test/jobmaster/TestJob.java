package jobmaster;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	
}
