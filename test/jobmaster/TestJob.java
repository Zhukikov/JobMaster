package jobmaster;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestJob {
	
	@Test
	public void testJobGetTitleEmpty() {
		Job job = new Job("");
		String expected = "";
		String result = job.getTitle();
		assertEquals("Strings should be equal.", expected, result);
	}

	@Test
	public void testJobGetTitle() {
		Job job = new Job("New job.");
		String expected = "New job.";
		String result = job.getTitle();
		assertEquals("Strings should be equal.", expected, result);
	}
	
	@Test
	public void testJobGetTitleNull() {
		Job job = new Job(null);
		String expected = "";
		String result = job.getTitle();
		assertEquals("Strings should be equal", expected, result);
	}
	
}
