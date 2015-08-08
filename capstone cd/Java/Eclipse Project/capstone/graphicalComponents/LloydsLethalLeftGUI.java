package graphicalComponents;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import logicalComponents.Controller;
import logicalComponents.HandRecordPlayer;
import logicalComponents.HandRecorder;

/**
 * <p>This is the main GUI which interfaces with the user and connects with all of the logical components via the {@link Controller}
 * class.  This was created very rapidly and not with as much forethought as the logical aspect of the project.  It presents a 
 * title bar on top, a menu on the left, a {@link HandMeter} on the right, and an exit button on the bottom, as well as a display
 * to show the current transfer rate through the serial port.</p>
 * <p>The menu on the left leads to three subwindows that have the following functions:
 * <ul>
 * 	<li> {@link QuickConfigureWindow}: configuring the glove for the user by implementing the set min/max methods</li>
 * 	<li> {@link RecordingOptionsWindow}: making and playing recordings by using the {@link HandRecorder} and {@link HandRecordPlayer} classes</li>
 * 	<li> {@link RockPaperScissorsWindow}: play a game of Rock Paper Scissors against the computer, where your input is recorded through the sensor glove and
 * the computer moves are displayed through the robotic hand and also on screen</li>
 * </ul>
 * @author dmac
 *
 */
public class LloydsLethalLeftGUI extends JFrame implements ActionListener {
	private Controller controller;
	
	private HandMeter handMeter;
	private JButton quickConfigureButton, createUserButton, recordingOptions, rockPaperScissorsButton, exitButton;
	private JComboBox usersBox;
	private JTextField transferRateTF;

	private RecordingOptionsWindow recordingWindow;
	private RockPaperScissorsWindow rockPaperScissorsWindow;
	private QuickConfigureWindow quickConfigureWindow;
	
	private boolean subWindowOpen;

	public LloydsLethalLeftGUI(){
		super("LLoyd's Lethal Left: The Robotic Hand");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		controller = new Controller();
		
		subWindowOpen = false;

		init();

		new Timer(1000, new TransferSpeedListener()).start(); 

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exitButton){
			controller.close();
			System.exit(0);
		}
		
		if(e.getSource() == quickConfigureButton){
			controller.reset();
			QuickConfigureWindow.createQuickConfigureWindow(this, controller);
//			controller.hand.addHandChangeListener(handMeter);
		}
		else if(e.getSource() == recordingOptions){
			recordingWindow = new RecordingOptionsWindow(this, controller, handMeter);
		}
		else if(e.getSource() == rockPaperScissorsButton){
			rockPaperScissorsWindow = new RockPaperScissorsWindow(this, controller);
		}
		
//		controller.getHand().addHandChangeListener(handMeter);
	}


	private void init(){
		handMeter = HandMeter.createHandMeter(controller.hand); //new HandMeter();//
//		controller.hand.removeHandChangeListener(handMeter);

		quickConfigureButton = new JButton("quick configure");
		createUserButton = new JButton("create new user");
		createUserButton.setEnabled(false);
		recordingOptions = new JButton("recording options");
		rockPaperScissorsButton = new JButton("ROCK PAPER SCISSORS!!");
		exitButton = new JButton("exit");

		usersBox = new JComboBox();
		usersBox.setEnabled(false);

		transferRateTF = new JTextField(8);
		transferRateTF.setHorizontalAlignment(JTextField.RIGHT);
		transferRateTF.setEditable(false);
		transferRateTF.setMaximumSize(new Dimension(20, 18));

		quickConfigureButton.addActionListener(this);
		createUserButton.addActionListener(this);
		recordingOptions.addActionListener(this);
		rockPaperScissorsButton.addActionListener(this);
		exitButton.addActionListener(this);

		JPanel eastPanel = new JPanel();
		eastPanel.add(handMeter);

		CenteredVerticalJPanel westNorthPanel = CenteredVerticalJPanel.createPanel();
		westNorthPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		westNorthPanel.addComponent(Box.createVerticalGlue());
		westNorthPanel.addComponent(Box.createVerticalStrut(15));
		westNorthPanel.addComponent(quickConfigureButton);
		westNorthPanel.addComponent(Box.createVerticalStrut(10));
		westNorthPanel.addComponent(createUserButton);
		westNorthPanel.addComponent(Box.createVerticalStrut(10));
		westNorthPanel.addComponent(usersBox);
		westNorthPanel.addComponent(Box.createVerticalStrut(15));
		westNorthPanel.addComponent(Box.createVerticalGlue());

		CenteredVerticalJPanel westMiddlePanel = CenteredVerticalJPanel.createPanel();
		westMiddlePanel.setLayout(new BoxLayout(westMiddlePanel, BoxLayout.Y_AXIS));
		westMiddlePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		westMiddlePanel.addComponent(Box.createVerticalGlue());
		westMiddlePanel.addComponent(Box.createVerticalStrut(15));
		westMiddlePanel.addComponent(recordingOptions);
		westMiddlePanel.addComponent(Box.createVerticalStrut(15));
		westMiddlePanel.addComponent(Box.createVerticalGlue());

		CenteredVerticalJPanel westSouthPanel = CenteredVerticalJPanel.createPanel();
		westSouthPanel.setLayout(new BoxLayout(westSouthPanel, BoxLayout.Y_AXIS));
		westSouthPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		westSouthPanel.addComponent(Box.createVerticalGlue());
		westSouthPanel.addComponent(Box.createVerticalStrut(15));
		westSouthPanel.addComponent(rockPaperScissorsButton);
		westSouthPanel.addComponent(Box.createVerticalStrut(15));
		westSouthPanel.addComponent(Box.createVerticalGlue());

		CenteredVerticalJPanel westPanel = CenteredVerticalJPanel.createPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		westPanel.addComponent(Box.createVerticalGlue());
		westPanel.addComponent(Box.createVerticalStrut(20));
		westPanel.addComponent(westNorthPanel);
		westPanel.addComponent(Box.createVerticalStrut(20));
		westPanel.addComponent(westMiddlePanel);
		westPanel.addComponent(Box.createVerticalStrut(20));
		westPanel.addComponent(westSouthPanel);
		westPanel.addComponent(Box.createVerticalStrut(20));
		westPanel.addComponent(Box.createVerticalGlue());

		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.add(Box.createHorizontalStrut(25));
		centerPanel.add(westPanel);
		centerPanel.add(Box.createHorizontalStrut(40));
		centerPanel.add(eastPanel);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		southPanel.add(new JLabel("Lloyd is one bad mumba jumba..."));
		southPanel.add(Box.createHorizontalGlue());
		southPanel.add(transferRateTF);
		southPanel.add(Box.createHorizontalStrut(5));
		southPanel.add(exitButton);

		add(centerPanel, BorderLayout.CENTER);
		add(new JLabel(new ImageIcon(getClass().getResource("../images/TitleBar.gif"))), BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
	}

	public JFrame getThisFrame(){
		return this;
	}

	class TransferSpeedListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			transferRateTF.setText("" + controller.updateTransferRateInfo() + " bytes/sec");
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new LloydsLethalLeftGUI();

		frame.pack();
		frame.setVisible(true);
	}

}
