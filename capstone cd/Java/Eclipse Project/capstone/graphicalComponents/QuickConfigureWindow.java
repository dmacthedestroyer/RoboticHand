package graphicalComponents;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;

import logicalComponents.Controller;

public class QuickConfigureWindow extends JWindow implements ActionListener{
	private JButton okButton;
	private JFrame parentFrame;
	private CenteredVerticalJPanel messagePanel;
	private JLabel messageLabel;
	private Controller controller;
	private HandMeter handMeter;

	private int step;
	
	private static boolean windowOpen;

	
	public static boolean createQuickConfigureWindow(JFrame parent, Controller cont){
		
		new QuickConfigureWindow(parent, cont);	
		return windowOpen;
	}
	
	public QuickConfigureWindow(JFrame parent, Controller cont){
		super(parent);
		
		parentFrame = parent;
		controller = cont;
		
		step = 0;
		windowOpen = true;

		init();
		setVisible(true);
	}

	private void init(){
		okButton = new JButton("ok");
		okButton.addActionListener(this);
		messageLabel = new JLabel();

		messagePanel = CenteredVerticalJPanel.createPanel();
		messagePanel.addComponent(Box.createVerticalStrut(20));
		messagePanel.addComponent(messageLabel);
		messagePanel.addComponent(Box.createVerticalStrut(10));
		messagePanel.addComponent(okButton);
		messagePanel.addComponent(Box.createVerticalStrut(30));

		add(messagePanel, BorderLayout.CENTER);

		displaySetMin();
	}

	private void displaySetMin(){
		messageLabel.setText("Extend your hand so all your fingers are completely straight:");
		messagePanel.setBorder("Step 1 of 2");

		pack();
		setLocation(parentFrame.getX()+(parentFrame.getWidth()/2)-(getWidth()/2), parentFrame.getY()+(parentFrame.getHeight()/2)-(getHeight()/2));
	}

	private void displaySetMax(){
		messagePanel.setBorder("Step 2 of 2");
		messageLabel.setText("Clinch your hand into a loose fist:");
	}


	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton){
			switch(step){
			case 0:
				controller.setMin();
				displaySetMax();
				step++;
				break;
			case 1:
				controller.setMax();
				setVisible(false);
				windowOpen = false;
			}
		}
	}
}
