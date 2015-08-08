package graphicalComponents;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.Timer;

import logicalComponents.Controller;

public class RockPaperScissorsWindow extends JFrame implements ActionListener{
	public static final int ROCK = 0;
	public static final int PAPER = 1;
	public static final int SCISSORS = 2;
	public static final int NEUTRAL = 3;

	private static final int WIN = 0;
	private static final int LOSE = 1;
	private static final int DRAW = 2;

	private int playerPoints, computerPoints, numGames;

	private Controller controller;
	private JFrame parentFrame;

	private JRadioButton[] seriesButtons;
	private JProgressBar playerProgress, computerProgress;

	private int playerChoice, computerChoice, countdownStep;

	private JButton exitButton, startGameButton, playAgainButton;
	private Timer actionTimer;

	private ImageIcon[] countdown;
	private ImageIcon win, lose, draw;
	private ImageIcon[] rps;
	private JLabel playerLabel, computerLabel, outcomeLabel, countdownLabel;

	private CenteredVerticalJPanel centerPanel;
	private JPanel introPanel, gamePanel;

	public RockPaperScissorsWindow(JFrame parent, Controller cont){
		super("Rock Paper Scissors");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		parentFrame = parent;
		parentFrame.setVisible(false);
		controller = cont;

		exitButton = new JButton("quit");
		startGameButton = new JButton("Start");
		playAgainButton = new JButton("play again");
		
		exitButton.addActionListener(this);
		startGameButton.addActionListener(this);
		playAgainButton.addActionListener(this);

		win = new ImageIcon(getClass().getResource("../images/left.png"));
		lose = new ImageIcon(getClass().getResource("../images/right.png"));
		draw = new ImageIcon(getClass().getResource("../images/draw.png"));

		countdown = new ImageIcon[4];
		countdown[0] = new ImageIcon(getClass().getResource("../images/rockPrompt.png"));
		countdown[1] = new ImageIcon(getClass().getResource("../images/paperPrompt.png"));
		countdown[2] = new ImageIcon(getClass().getResource("../images/scissorsPrompt.png"));
		countdown[3] = new ImageIcon(getClass().getResource("../images/shootPrompt.gif"));

		rps = new ImageIcon[3];
		rps[ROCK]= new ImageIcon(getClass().getResource("../images/rock.gif"));
		rps[PAPER] = new ImageIcon(getClass().getResource("../images/paper.gif"));
		rps[SCISSORS] = new ImageIcon(getClass().getResource("../images/scissors.gif"));

		playerLabel = new JLabel(rps[0]);
		computerLabel = new JLabel(rps[0]);
		outcomeLabel = new JLabel();
		countdownLabel = new JLabel();

		seriesButtons = new JRadioButton[3];
		ButtonGroup seriesGroup = new ButtonGroup();
		for(int i=0; i<3; i++){
			seriesButtons[i] = new JRadioButton("Best of " + (i*2+3));
			seriesGroup.add(seriesButtons[i]);
		}
		seriesButtons[0].setSelected(true);

		playerProgress = new JProgressBar(0,7);
		computerProgress = new JProgressBar(0,7);
		
//		Dimension progressBarSize = new Dimension(rps[0].getIconWidth()-(exitButton.getWidth()*2), exitButton.getHeight());
//		playerProgress.setSize(progressBarSize);
//		playerProgress.setPreferredSize(progressBarSize);
//		playerProgress.setMaximumSize(progressBarSize);
//		playerProgress.setMinimumSize(progressBarSize);
//		computerProgress.setSize(progressBarSize);
//		computerProgress.setPreferredSize(progressBarSize);
//		computerProgress.setMaximumSize(progressBarSize);
//		computerProgress.setMinimumSize(progressBarSize);
		
		JLabel titleLabel = new JLabel(new ImageIcon(getClass().getResource("../images/RPSTitle.png")));

		introPanel = createIntroPanel();
		gamePanel = createGamePanel();
		gamePanel.setVisible(false);

		CenteredVerticalJPanel playerPanel = CenteredVerticalJPanel.createPanel("");
		playerPanel.addComponent(new JLabel(new ImageIcon(getClass().getResource("../images/man.png"))));
		playerPanel.addComponent(playerLabel);

		CenteredVerticalJPanel computerPanel = CenteredVerticalJPanel.createPanel("");
		computerPanel.addComponent(new JLabel(new ImageIcon(getClass().getResource("../images/machine.png"))));
		computerPanel.addComponent(computerLabel);

		centerPanel = CenteredVerticalJPanel.createPanel();
		centerPanel.addComponent(introPanel);
		centerPanel.addComponent(gamePanel);


		JPanel exitPanel = new JPanel();
		exitPanel.setLayout(new BoxLayout(exitPanel, BoxLayout.X_AXIS));
		exitPanel.add(Box.createHorizontalStrut(exitButton.getWidth()));
		exitPanel.add(playerProgress);
		exitPanel.add(Box.createHorizontalGlue());
		exitPanel.add(computerProgress);
		exitPanel.add(exitButton);

		add(titleLabel, BorderLayout.NORTH);
		add(playerPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(computerPanel, BorderLayout.EAST);
		add(exitPanel, BorderLayout.SOUTH);

		pack();
		setVisible(true);

		actionTimer = new Timer(500, new GamePlayTimer());
		actionTimer.setRepeats(false);
//		actionTimer.start();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exitButton){
			actionTimer.stop();
			setVisible(false);
			controller.reset();
			parentFrame.setVisible(true);
		}
		else if(e.getSource() == startGameButton){
			for(int i=0; i<seriesButtons.length; i++){
				if(seriesButtons[i].isSelected()){
					numGames = (i*2)+3;
					break;
				}
			}
			startGame();
		}
		else if(e.getSource() == playAgainButton){
			playAgainButton.setVisible(false);
			gamePanel.setVisible(false);
			introPanel.setVisible(true);
			countdownStep = 0;
			countdownLabel.setIcon(null);
			outcomeLabel.setIcon(null);
			playerProgress.setValue(0);
			computerProgress.setValue(0);
		}
	}

	private void startGame() {
		controller.makeGesture(NEUTRAL);

		introPanel.setVisible(false);
		gamePanel.setVisible(true);

		playerPoints = computerPoints = 0;
		playerProgress.setMaximum((numGames/2)+1);
		computerProgress.setMaximum((numGames/2)+1);
		actionTimer.setInitialDelay(400);
		actionTimer.start();
	}

	private int determineOutcome(){
		if(playerChoice == computerChoice)
			return DRAW;
		if(playerChoice == ROCK)
			if(computerChoice == PAPER)
				return LOSE;
			else return WIN;
		if(playerChoice == PAPER)
			if(computerChoice == SCISSORS)
				return LOSE;
			else return WIN;
		if(playerChoice == SCISSORS)
			if(computerChoice == ROCK)
				return LOSE;
		return WIN;
	}

	private JPanel createIntroPanel(){
		CenteredVerticalJPanel introPanel =  CenteredVerticalJPanel.createPanel();
		introPanel.addComponent(new JLabel(new ImageIcon(getClass().getResource("../images/chooseGamePrompt.png"))));
		introPanel.addComponent(Box.createVerticalStrut(15));
		for(JRadioButton jrb : seriesButtons)
			introPanel.addComponent(jrb);
		introPanel.addComponent(Box.createVerticalStrut(20));
		introPanel.addComponent(startGameButton);

		return introPanel;
	}

	private JPanel createGamePanel(){
		CenteredVerticalJPanel outcomePanel = CenteredVerticalJPanel.createPanel();
//		outcomePanel.addComponent(Box.createVerticalStrut(rps[0].getIconHeight()/4));
		outcomePanel.addComponent(countdownLabel);
		outcomePanel.addComponent(outcomeLabel);
		outcomePanel.addComponent(playAgainButton);
		
		playAgainButton.setVisible(false);
		return outcomePanel;
	}

	private class GamePlayTimer implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(countdownStep < countdown.length-1){
				controller.makeGesture(NEUTRAL);
				outcomeLabel.setIcon(null);
				countdownLabel.setIcon(countdown[countdownStep]);
				countdownStep++;
				actionTimer.setInitialDelay(600);
				actionTimer.start();
				return;
			}else if(countdownStep == countdown.length-1){
				computerChoice = (int)(Math.random()*3);
				playerChoice = controller.getRockPaperScissorPlayerChoice();

				controller.makeGesture(computerChoice);
				playerLabel.setIcon(rps[playerChoice]);
				computerLabel.setIcon(rps[computerChoice]);

				countdownLabel.setIcon(countdown[countdownStep]);
				countdownStep = 0;
				
				int outcome = determineOutcome();
				if(outcome == WIN){
					outcomeLabel.setIcon(win);
					playerPoints++;
					playerProgress.setValue(playerPoints);
				}
				else if(outcome == LOSE){
					outcomeLabel.setIcon(lose);
					computerPoints++;
					computerProgress.setValue(computerPoints);
				}
				else{
					outcomeLabel.setIcon(draw);
				}
				outcomeLabel.setVisible(true);

				if(playerPoints<numGames/2+1 && computerPoints < numGames/2+1){
					actionTimer.setInitialDelay(2000);
					actionTimer.start();
					return;
				}
				else{
					countdownStep = countdown.length+1;
					actionTimer.setInitialDelay(2000);
					actionTimer.start();
					return;
				}
				
			}else{
				countdownLabel.setIcon(new ImageIcon(getClass().getResource("../images/winner.png")));
				if(playerPoints > computerPoints)
					outcomeLabel.setIcon(win);
				else outcomeLabel.setIcon(lose);
				playAgainButton.setVisible(true);
			}
		}
	}

}