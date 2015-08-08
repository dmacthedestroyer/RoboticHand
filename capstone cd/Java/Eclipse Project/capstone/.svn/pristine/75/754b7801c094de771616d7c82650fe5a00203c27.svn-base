package graphicalComponents;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;

import logicalComponents.Controller;
import logicalComponents.HandRecordPlayer;
import logicalComponents.HandRecorder;

public class RecordingOptionsWindow extends JWindow implements ActionListener{
	private JFrame parentFrame;
	private Controller controller;
	private HandRecorder handRecorder;
	private HandRecordPlayer handRecordPlayer;
	private HandMeter handMeter;

	private JButton playButton, deleteButton, recordButton, closeButton;
	private JTextField recordNameTF;
	private JComboBox recordedNamesCB;

	public RecordingOptionsWindow(JFrame parent, Controller cont, HandMeter meter){
		super(parent);
		
		parentFrame = parent;
		controller = cont;
		
		handMeter = meter;
		controller.hand.removeHandChangeListener(handMeter);
		
		handRecorder = controller.getHandRecorder();
		handRecordPlayer = new HandRecordPlayer();

		init();

		setVisible(true);
	}

	private void init(){
		playButton = new JButton("play");
		deleteButton = new JButton("delete");
		recordButton = new JButton("start recording");
		closeButton = new JButton("close");
		recordNameTF = new JTextField(10);
		recordedNamesCB = new JComboBox(handRecordPlayer.getRecordingNames());
		recordedNamesCB.setEditable(false);

		playButton.addActionListener(this);
		deleteButton.addActionListener(this);
		recordButton.addActionListener(this);
		closeButton.addActionListener(this);

		JPanel topPanel = new JPanel();
		topPanel.add(recordedNamesCB);
		topPanel.add(Box.createHorizontalStrut(30));
		topPanel.add(playButton);
		topPanel.add(deleteButton);

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(new JLabel("Name of Recording:"));
		bottomPanel.add(recordNameTF);
		bottomPanel.add(Box.createHorizontalStrut(20));
		bottomPanel.add(recordButton);

		CenteredVerticalJPanel panel = CenteredVerticalJPanel.createPanel("Recording Options");
		panel.addComponent(Box.createVerticalStrut(20));
		panel.addComponent(topPanel);
		panel.addComponent(Box.createVerticalStrut(15));
		panel.addComponent(bottomPanel);
		panel.addComponent(Box.createVerticalStrut(20));

		JPanel closeButtonPanel = new JPanel();
		closeButtonPanel.setLayout(new BoxLayout(closeButtonPanel, BoxLayout.X_AXIS));
		closeButtonPanel.add(Box.createHorizontalGlue());
		closeButtonPanel.add(closeButton);

		panel.add(closeButtonPanel);

		add(panel, BorderLayout.CENTER);

		pack();
		setLocation(parentFrame.getX()+(parentFrame.getWidth()/2)-(getWidth()/2), parentFrame.getY()+(parentFrame.getHeight()/2)-(getHeight()/2));

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == recordButton){
			if(recordButton.getText().equals("start recording")){
				if(recordNameTF.getText().equals("")){
					setVisible(false);
					JOptionPane.showMessageDialog(null, "Specify a name for this recording", "Error", JOptionPane.ERROR_MESSAGE);
					setVisible(true);
					return;
				}
				playButton.setEnabled(false);
				deleteButton.setEnabled(false);
				closeButton.setEnabled(false);
				recordNameTF.setEnabled(false);
				recordedNamesCB.setEnabled(false);
				recordButton.setText("stop recording");
				
				handRecorder.start();
			}
			else{
				playButton.setEnabled(true);
				deleteButton.setEnabled(true);
				closeButton.setEnabled(true);
				recordNameTF.setEnabled(true);
				recordedNamesCB.setEnabled(true);
				recordButton.setText("start recording");
				
				handRecordPlayer.addRecording(recordNameTF.getText(), handRecorder.stop());
				recordedNamesCB.removeItem(recordNameTF.getText());
				recordedNamesCB.addItem(recordNameTF.getText());
			}
		}
		else if(e.getSource() == deleteButton){
			handRecordPlayer.removeRecording((String)recordedNamesCB.getSelectedItem());
			recordedNamesCB.removeItem(recordedNamesCB.getSelectedItem());
		}
		else if(e.getSource() == playButton){
			playButton.setEnabled(false);
			deleteButton.setEnabled(false);
			closeButton.setEnabled(false);
			recordNameTF.setEnabled(false);
			recordedNamesCB.setEnabled(false);
			recordButton.setEnabled(false);
			controller.playRecording(handRecordPlayer.getRecording(""+recordedNamesCB.getSelectedItem()));
			enableAllComponents();
		}
		else if(e.getSource() == closeButton){
			handRecordPlayer.close();
			setVisible(false);
			controller.reset();
			controller.hand.addHandChangeListener(handMeter);
		}
	}
	
	private void enableAllComponents(){
		playButton.setEnabled(true);
		deleteButton.setEnabled(true);
		closeButton.setEnabled(true);
		recordNameTF.setEnabled(true);
		recordedNamesCB.setEnabled(true);
		recordButton.setEnabled(true);
	}

}
