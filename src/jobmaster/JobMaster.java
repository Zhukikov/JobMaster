package jobmaster;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class JobMaster extends JFrame {

	JPanel newJobPanel = new JPanel();
	JButton newJobButton = new JButton();
	JTextField newJobTextField = new JTextField();
	DefaultListModel listModel = new DefaultListModel();
	JList jobList = new JList(this.listModel);
	JScrollPane jobScrollPane = new JScrollPane(this.jobList);

	public JobMaster() {
		super("JobMaster");
		this.newJobButton.setName("new-job-button");
		this.newJobButton.setText("Add new job");
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String description = JobMaster.this.newJobTextField.getText();
				Job job = Job.saveJob(description);
				JobMaster.this.listModel.addElement(job);
			}

		};
		this.newJobButton.addActionListener(actionListener);

		this.newJobTextField.setName("new-job-textfield");
		this.newJobTextField.setText("Job description");

		this.jobList.setName("job-list");
		List<Job> jobs = Job.getJobs();
		for (Job job : jobs) {
			this.listModel.addElement(job);
		}

		this.jobScrollPane.setName("job-scrollpane");

		this.newJobPanel.setName("new-job");
		this.newJobPanel.add(this.newJobButton);
		this.newJobPanel.add(this.newJobTextField);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.newJobPanel, BorderLayout.NORTH);
		this.getContentPane().add(this.jobScrollPane, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
	}
	public static void main(String[] args) {
		new JobMaster().setVisible(true);
	}

}
