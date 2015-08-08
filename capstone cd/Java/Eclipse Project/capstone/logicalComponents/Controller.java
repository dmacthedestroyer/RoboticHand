package logicalComponents;

import graphicalComponents.RockPaperScissorsWindow;

import java.util.ArrayList;

import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

/**
 * This is the bondary class which interfaces with the GUI and acts as a bridge to all
 * of the data of the logical Hand.  It contains methods that are tailored to specific
 * tasks which came up through the GUI.  This class also contains a class which implements
 * {@link SerialPortEventListener}, and manages serial port data flow and updating the joint 
 * positions.
 * @author dmac
 *
 */
public class Controller{
	/**
	 * The boundary class that interfaces with the serial port for data IO
	 */
	private Port serialPort;
	
	/**
	 * The class that implements {@link SerialPortEventListener} and manages data flow
	 */
	private SerialIO serialIO;
	
	/**
	 * The class which logically represents a human hand.  
	 * @see Hand
	 */
	public Hand hand;


	/**
	 * Constructor which initializes all private fields and also starts data transmission
	 * through the serial port.
	 *
	 */
	public Controller(){
		serialPort = new Port();
		serialIO = new SerialIO();
		serialPort.addEventListener(serialIO);

		hand = new Hand();
		
		serialIO.reset();
	}

	/**
	 * This class is what is used for receiving notifications from the serial port
	 * for incoming data, and also manages output to the serial port based on the state
	 * of certain flags.  The details of this will be described in the serialEvent method.
	 * The information for each of the three flags are found below as well.
	 * @author dmac
	 *
	 */
	public class SerialIO implements SerialPortEventListener{
		/**
		 * The value that is used to interpret a break character with the development board.
		 * This value should not be changed unless it is changed on the development board as well.
		 */
		public final int BREAK = 255;
		
		/**
		 * The value that is used to interpret a continue character with the development board.
		 * This value should not be changed unless it is changed on the development board as well.
		 */
		public final int RESUME = 0;

		/**
		 * Holds the joint positions for the gesture which is currently being used.  It will
		 * become relevant only when the <em>sendingGesture</em> flag is set.
		 */
		private double[] currentGesture;
		
		/**
		 * The index of the current joint which is to be updated by the next incoming byte fromt the
		 * serial port
		 */
		private int ioIndex;
		
		/**
		 * Contains the last position of each joint received from the serial port.  This variable is used
		 * to call the setMax and setMin methods, and can be used with any application where a snapshot 
		 * of the current position of the hand is necessary.
		 */
		private int[] currentInput;
		
		/**
		 * If the hand is not to be sending back positions to the development board, then this flag is 
		 * turned off.
		 */
		private boolean isSending;
		
		/**
		 * If the serial port is currently being occupied by playing a recording, then this flag is set 
		 * and information about the position of the Hand is no longer sent out throught the serial port
		 * until this flag is no longer set.
		 */
		private boolean recordPlayback;
		
		/**
		 * This flag is set when the program requested to send one of the getsures from {@link HandGestures}.  
		 * It cycles through the values in currentGesture indefinitely until this flag is cleared.
		 */
		private boolean sendingGesture;

		/**
		 * Keeps track of the number of bytes which have come through the serial port.  It is intended to be
		 * reset every second and displayed to show the current transfer rate of the serial port.
		 */
		private int numIncomingBytes;

		/**
		 * The current recording being played when the recordPlayback flag is set.
		 * @see HandRecorder
		 */
		private ArrayList<Double> currentRecording;
		
		/**
		 * Act in the same way as the ioIndex variable, except keeps track of the current index of the recording
		 * being played.
		 */
		private int recordingIndex;

		/**
		 * Default contructor.
		 *
		 */
		public SerialIO(){
			ioIndex = 0;
			currentInput = new int[JointName.values().length];

			isSending = true;
			recordPlayback = false;

			numIncomingBytes = 0;
		}

		/**
		 * <p>This method handles receiving data from the serial port and determining what to do based on the
		 * status of the three flags.</p>
		 * 
		 * <p>Essentially the process is as follows:<br>
		 * <ul>
		 * 	<li>An event is raised indicating that there is data in the input buffer of the serial port</li>
		 * 	<li>The buffer is flushed and the values are stored in a local variable</li>
		 * 	<li>the number of bytes that were received are added to the total of numIncomingBytes</li>
		 * 	<li>the isSending flag is evaluated.  If the flag is cleared, then the method returns at this point.</li>
		 * 	<li>if the isSending flag is set, then we iterate through the array containing each byte that was in the input buffer and do the following:</li>
		 * 		<ul>
		 * 			<li>if the sendingGesture flag is set, then send the next value in currentGesture</li>
		 * 			<li>if the recordPlayback flag is set, then send the next value in currentRecording, increment ioIndex and continue to the next value in the buffer</li>
		 * 			<li>otherwise the task is to record the movement from the glove.  So the value is stored in currentInput at the index specified by ioIndex</li>
		 * 			<li>set that value in the Hand class, so as to compute the position of the joint</li>
		 * 			<li>write that position out to the serial port</li>
		 * 			<li>update the ioIndex and continue to the next byte in the buffer</li>
		 * 		</ul>
		 * </ul>
		 * 		
		 */
		public void serialEvent(SerialPortEvent e) {
			switch(e.getEventType()){
			case SerialPortEvent.DATA_AVAILABLE:
				//read in from the input buffer of the serial port
				int[] dataBytes = serialPort.read();

				//update the number of bytes received for monitoring the transfer rate
				numIncomingBytes += dataBytes.length;


				//if the sending flag is off, then do nothing
				//this is done after reading in the buffer so the buffer will be cleared from extraneous data (something that has been observed to occur)
				if(!isSending)
					return;

				//read in each byte that was pulled form the buffer and process it
				for(int singleByte : dataBytes){
					JointName currentJoint = JointName.values()[ioIndex];
					
					if(sendingGesture){
						serialPort.write(currentJoint.getServoPosition(currentGesture[ioIndex]));
						ioIndex = (ioIndex+1)%JointName.values().length;
						continue;
					}
					
					if(recordPlayback){
						try{
							serialPort.write(currentJoint.getServoPosition(currentRecording.get(recordingIndex++)));
							ioIndex = (ioIndex+1) % JointName.values().length;
							continue;
						}catch(IndexOutOfBoundsException endOfRecording){
							recordPlayback = false;
							reset();
							return;
						}
					}
					//update the current value for this incoming byte
					//if the current joint is a distal joint, then we need to subtract the measurement from the metacarpal value
					currentInput[ioIndex] = (currentJoint.isDistal())?singleByte-currentInput[ioIndex-1] : singleByte;
					//if the subtracted value makes the value negative, then just insert 0, indicating the smallest value
					if(currentInput[ioIndex] < 0)
						currentInput[ioIndex] = 0;

					//update the position in the hand
					hand.setPosition(currentJoint, currentInput[ioIndex]);

					//send the position of the joint at the ioIndex location
					serialPort.write(currentJoint.getServoPosition(hand.getPosition(currentJoint)));

					//increment the index
					ioIndex = (ioIndex+1)%JointName.values().length;
				}//end FOR loop
			}//end SWITCH statement
		}//end serialEvent

		public void reset(){
			stopSending();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sendingGesture = false;
			beginSending();
		}

		private void stopSending(){
			isSending = false;
			serialPort.write(BREAK);
		}

		private void beginSending(){
			isSending = true;
			ioIndex = 0;
			serialPort.write(RESUME);
		}

		public void playRecording(ArrayList<Double> theRecording){
			reset();
			currentRecording = theRecording;
			recordingIndex = 0;
			recordPlayback = true;
		}
		
		public int getRockPaperScissorPlayerChoice(){
			boolean fits = true;
			//check for paper
			for(int i=0; i<JointName.values().length; i++){
				if(hand.getPosition(JointName.values()[i]) > .5)
					fits = false;
			}
			if(fits)
				return RockPaperScissorsWindow.PAPER;
			
			fits = true;
			//check for rock
			for(int i=0; i<JointName.values().length; i++){
				if(hand.getPosition(JointName.values()[i]) < .5)
					fits = false;
			}
			if(fits)
				return RockPaperScissorsWindow.ROCK;
			return RockPaperScissorsWindow.SCISSORS;
		}
		
		public void makeGesture(int gesture){
			switch(gesture){
			case RockPaperScissorsWindow.ROCK:
				currentGesture = HandGestures.ROCK;
				break;
			case RockPaperScissorsWindow.PAPER:
				currentGesture = HandGestures.PAPER;
				break;
			case RockPaperScissorsWindow.SCISSORS:
				currentGesture = HandGestures.SCISSORS;
				break;
			case RockPaperScissorsWindow.NEUTRAL:
				currentGesture = HandGestures.NEUTRAL;
				break;
			}
			sendingGesture = true;
		}
	}//end class SerialInputHandler

	public int updateTransferRateInfo(){
		int rate = serialIO.numIncomingBytes;
		serialIO.numIncomingBytes = 0;
		return rate;
	}
	
	public int getRockPaperScissorPlayerChoice(){
		return serialIO.getRockPaperScissorPlayerChoice();
	}

	public void setMin(){
		for(int i=0; i<JointName.values().length; i++)
			hand.setMin(JointName.values()[i], serialIO.currentInput[i]);
	}

	public void setMax(){
		for(int i=0; i<JointName.values().length; i++)
			hand.setMax(JointName.values()[i], serialIO.currentInput[i]);
	}

	public HandRecorder getHandRecorder(){
		return HandRecorder.createHandRecorder(hand);
	}

	public void playRecording(ArrayList<Double> theRecording){
		serialIO.playRecording(theRecording);
		while(serialIO.recordPlayback)
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	public void reset(){
		serialIO.reset();
	}
	
	public void close(){
		serialPort.close();
	}
	
	public void makeGesture(int gesture){
		serialIO.makeGesture(gesture);
	}
}