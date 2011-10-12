package jobmaster;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JobMaster extends JFrame {

	JPanel newJobPanel = new JPanel();
	JButton newJobButton = new JButton();
	JTextField newJobTextField = new JTextField();
	DefaultListModel listModel = new DefaultListModel();
	JList jobList = new JList(this.listModel);
	JScrollPane jobScrollPane = new JScrollPane(this.jobList);
	JPanel jobDetails = new JPanel();
	JSplitPane splitPane;
	JLabel jobId;
	JTextField jobTitle;

	public JobMaster() {
		super("JobMaster");
		this.newJobButton.setName("new-job-button");
		this.newJobButton.setText("Add new job");
		this.newJobButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String description = JobMaster.this.newJobTextField.getText();
				Job job = Job.saveJob(description);
				JobMaster.this.listModel.addElement(job);
			}
			
		});
		this.newJobTextField.setName("new-job-textfield");
		this.newJobTextField.setText("Job description");

		this.newJobPanel.setName("new-job");
		this.newJobPanel.add(this.newJobButton);
		this.newJobPanel.add(this.newJobTextField);
		
		this.jobList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				Properties jobProperties = ((Job) ((JList) e.getSource())
						.getSelectedValue()).getProperties();
				String id = jobProperties.getProperty("id");
				String title = jobProperties.getProperty("title");
				JobMaster.this.jobId.setText(id);
				JobMaster.this.jobTitle.setText(title);
			}
		});

		this.jobList.setName("job-list");
		List<Job> jobs = Job.getJobs();
		for (Job job : jobs) {
			this.listModel.addElement(job);
		}

		this.jobScrollPane.setName("job-scrollpane");



		this.jobId = new JLabel();
		this.jobId.setName("job-details-id");

		this.jobTitle = new JTextField();
		this.jobTitle.setName("job-details-title");
		
		this.jobDetails.setName("job-details");
		this.jobDetails.setLayout(new BoxLayout(this.jobDetails,
				BoxLayout.Y_AXIS));
		this.jobDetails.add(this.jobId);
		this.jobDetails.add(this.jobTitle);
		
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				this.jobScrollPane, this.jobDetails);
		this.splitPane.setName("splitpane");
		this.splitPane.setDividerLocation(100);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.newJobPanel, BorderLayout.NORTH);
		this.getContentPane().add(this.splitPane, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}

	public static void main(String[] args) {
		new JobMaster().setVisible(true);
	}

}
