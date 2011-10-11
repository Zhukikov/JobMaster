package jobmaster;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JobMaster extends JFrame {

	JPanel newJobPanel = new JPanel();
	JButton newJobButton = new JButton();
	JTextField newJobTextField = new JTextField();

	public JobMaster() {
		super("JobMaster");
		this.newJobButton.setName("new-job-button");
		this.newJobButton.setText("Add new job");
		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String description = JobMaster.this.newJobTextField.getText();
				Job.saveJob(description);
			}

		};
		this.newJobButton.addActionListener(actionListener);

		this.newJobTextField.setName("new-job-textfield");
		this.newJobTextField.setText("Job description");

		this.newJobPanel.setName("new-job");
		this.newJobPanel.add(this.newJobButton);
		this.newJobPanel.add(this.newJobTextField);
		this.getContentPane().add(this.newJobPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}
	public static void main(String[] args) {
		new JobMaster().setVisible(true);
	}

}
