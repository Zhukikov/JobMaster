package jobmaster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.awt.Container;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

public class TestJobMaster {

	JobMaster jm;

	@Before
	public void runBefore() {
		File jobDir = new File("jobs");
		delete(jobDir);
		File skeletonDir = new File("skeleton");
		delete(skeletonDir);
		this.jm = new JobMaster();
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

	private static Object findComponent(Component[] components, String name) {
		for (Component comp : components) {
			if (comp.getName().equals(name)) {
				return comp;
			}
			if (comp instanceof JScrollPane) {
				Object result2 = findComponent(((JScrollPane) comp)
						.getViewport().getComponents(), name);
				if (result2 != null) {
					return result2;
				}
			} else if (comp instanceof Container) {
				Object result2 = findComponent(
						((Container) comp).getComponents(), name);
				if (result2 != null) {
					return result2;
				}
			}
		}
		return null;
	}

	@Test
	public void testWindowIsShowing() {
		this.jm.setVisible(true);
		assertTrue("Should be visible", this.jm.isShowing());
	}

	@Test
	public void testTitle() {
		String title = this.jm.getTitle();
		assertEquals("Title should be \"JobMaster\"", "JobMaster", title);
	}

	@Test
	public void testAddNewPanelShowing() {
		JPanel newJobPanel = (JPanel) findComponent(this.jm.getContentPane()
				.getComponents(), "new-job");
		assertNotNull("JPanel should not be null.", newJobPanel);
	}

	@Test
	public void testAddNewButtonShowing() {
		JButton newJobButton = (JButton) findComponent(this.jm.getContentPane()
				.getComponents(), "new-job-button");
		assertNotNull("JButton should not be null.", newJobButton);
	}

	@Test
	public void testAddNewDescriptionShowing() {
		JTextField newJobTextField = (JTextField) findComponent(this.jm
				.getContentPane().getComponents(), "new-job-textfield");
		assertNotNull("JTextField should not be null.", newJobTextField);
	}

	@Test
	public void testAddNewButtonText() {
		String expected = "Add new job";
		JButton newJobButton = (JButton) findComponent(this.jm.getContentPane()
				.getComponents(), "new-job-button");
		assertEquals("Strings should be equal.", expected,
				newJobButton.getText());
	}

	@Test
	public void testAddNewTextFieldInitialText() {
		String expected = "Job description";
		JTextField newJobTextField = (JTextField) findComponent(this.jm
				.getContentPane().getComponents(), "new-job-textfield");
		assertEquals("Strings should be equal.", expected,
				newJobTextField.getText());
	}

	@Test
	public void testAddNewButtonClickAddsNewJob() {
		JButton newJobButton = (JButton) findComponent(this.jm.getContentPane()
				.getComponents(), "new-job-button");
		newJobButton.doClick();
		File newJobDir = new File("jobs/1");
		assertTrue("Directory should exist.", newJobDir.isDirectory());
	}

	@Test
	public void testCloseButton() {
		assertEquals("Should be equal.", JFrame.EXIT_ON_CLOSE,
				this.jm.getDefaultCloseOperation());
	}

	@Test
	public void testJobListEmptyOnStart() {
		JList jobList = (JList) findComponent(this.jm.getContentPane()
				.getComponents(), "job-list");
		int size = jobList.getModel().getSize();
		assertEquals("Should be empty.", 0, size);

	}

	@Test
	public void testJobListNotEmptyOnStart() {
		Job.saveJob("aaa");
		this.jm = new JobMaster();
		JList jobList = (JList) findComponent(this.jm.getContentPane()
				.getComponents(), "job-list");
		int size = jobList.getModel().getSize();
		assertEquals("Should have one element.", 1, size);
	}

	@Test
	public void testNewJobAddsToTheList() {
		JButton newJobButton = (JButton) findComponent(this.jm.getContentPane()
				.getComponents(), "new-job-button");
		JList jobList = (JList) findComponent(this.jm.getContentPane()
				.getComponents(), "job-list");
		int size = jobList.getModel().getSize();
		newJobButton.doClick();
		assertEquals("Should have one more element.", size + 1, jobList.getModel().getSize());
	}

}
