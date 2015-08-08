package logicalComponents;

import java.util.ArrayList;


public class HandRecorder implements HandChangeListener {
	private boolean recording, firstJointFound;
	
	private ArrayList<Double> handValues;
	
	private HandRecorder(){
		recording = firstJointFound = false;
		handValues = new ArrayList<Double>();		
	}
	
	/**
	 * Creates a HandRecorder which attaches itself to the given Hand.
	 * @param h The hand which this HandRecorder listens for updates
	 * @return A HandRecorder which listens to updates from the given Hand
	 * @see HandChangeListener
	 * @see Hand
	 */
	public static HandRecorder createHandRecorder(Hand h){
		HandRecorder hr = new HandRecorder();
		h.addHandChangeListener(hr);
		return hr;
	}

	/**
	 * Begins recording the joint movements of the Hand
	 *
	 */
	public void start(){
		handValues.clear();
		recording = true;
	}
	
	/**
	 * Stops recording the Hand and returns the recording.  If stop is invoked before
	 * start is invoked, then it will return the previous recording
	 * @return A list of Double values which iterate through each Joint in the order 
	 * specified by {@link JointName}, beginning with the first joint
	 */
	public ArrayList<Double> stop(){
		firstJointFound = recording = false;
		return handValues;
	}
	
	/**
	 * The method overridden in the {@link HandChangeListener} interface.
	 */
	public void handChanged(HandEvent he) {
		if(recording && he.getJointName().equals(JointName.values()[0]))
			firstJointFound = true;
			
		if(recording && firstJointFound)
			try{
				handValues.add(he.getPosition());
			}catch(NullPointerException npe){}
	}

}
