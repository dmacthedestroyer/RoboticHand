package logicalComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * <p>This class contains a collection of {@link logicalComponents.Joint}s 
 * which combined form a representation of the human hand.  It contains a 
 * key-value data set in which the key is an instance from the 
 * {@link logicalComponents.JointName} and the value is an instance from 
 * {@link Joint}.</p>  
 * <p>This class also utilizes a custom-built event propagation system 
 * which sends update notifications to all classes registered to it.  In 
 * order for a class to receive events based on this class, it must implement 
 * the {@link logicalComponents.HandChangeListener} interface, and then be 
 * added via the Hand.addHandChangeListener() method.</p>
 * <p>As a result of containing the {@link Joint} class, many of the methods
 * from that class are overridden.  To get a more detailed description of some 
 * of the methods, go to {@link Joint}.
 * @author DMac
 *
 */
public class Hand {
	/**
	 * The list of all classes registered to listen for events fired by this class.
	 */
	private List<HandChangeListener> handChangeListeners;
	/**
	 * The key-value data set used to contain the data for each joint.  
	 * @see JointName
	 * @see Joint
	 */
	private Hashtable<JointName, Joint> hand;
	
	/**
	 * Creates a new Hand instance, with the initial min and max 
	 * positions for each Joint set to 0 and 255, respectively.
	 * @see Joint
	 */
	public Hand(){
		this(0, 255);
	}
	
	/**
	 * Creates a new Hand instance, with the initial min and max 
	 * positions for each Joint set to 'min' and 'max', respectively.
	 * @param min the minimum position of all joints in the Hand
	 * @param max the maximum position of all joints in the Hand
	 */
	public Hand(int min, int max){
		handChangeListeners = new ArrayList<HandChangeListener>();
		hand = new Hashtable<JointName, Joint>();
		
		for(JointName jn : JointName.values())
			hand.put(jn, new Joint(min,max));
	}
	
	/**
	 * Sets the position value for the {@link Joint} at the key 'location'
	 * @param location The joint whose position is being set
	 * @param position The position value of the {@link Joint}
	 */
	public void setPosition(JointName location, int position){
		hand.get(location).setPosition(position);
		
		fireHandChangeEvent(new HandEvent(this, location, hand.get(location).getPosition(), hand.get(location).getMin(), hand.get(location).getMax()));
	}
	
	/**
	 * Sets the maximum position for the {@link Joint} at the key 'location'
	 * @param location The joint whose position is being set
	 * @param maxPos The maximum position for the given {@link Joint}
	 */
	public void setMax(JointName location, int maxPos){
		hand.get(location).setMax(maxPos);
		
		fireHandChangeEvent(new HandEvent(this, location, hand.get(location).getPosition(), hand.get(location).getMin(), hand.get(location).getMax()));
	}
	
	/**
	 * Sets the minimum position for the {@link Joint} at the key 'location'
	 * @param location The joint whose position is being set
	 * @param minPos The minimum position for the given {@link Joint}
	 */
	public void setMin(JointName location, int minPos){
		hand.get(location).setMin(minPos);
		
		fireHandChangeEvent(new HandEvent(this, location, hand.get(location).getPosition(), hand.get(location).getMin(), hand.get(location).getMax()));
	}
	
	/**
	 * Returns the position value of the {@link Joint} at the given location
	 * @param location The joint whose position is being set
	 * @return A double value between 1 and 0, where 1 is completely flexed and 0 is completely straightened
	 */
	public double getPosition(JointName location){
		return hand.get(location).getPosition();
	}
	
	/**
	 * Returns the maximum position of the {@link Joint} at the given {@link JointName}
	 * @param location The joint whose position is being set
	 * @return The maximum value this {@link Joint} can have.
	 */
	public int getMax(JointName location){
		return hand.get(location).getMax();
	}
	
	/**
	 * Returns the minimum position of the {@link Joint} at the given {@link JointName}
	 * @param location The joint whose position is being set
	 * @return the minimum value this {@link Joint} can have.
	 */
	public int getMin(JointName location){
		return hand.get(location).getMin();
	}

	/**
	 * Registers the {@link HandChangeListener} to listen for events from this Hand 
	 * @param hcl The {@link HandChangeListener} which is to be registered to receive 
	 * events from this Hand
	 */
	public void addHandChangeListener(HandChangeListener hcl){
		handChangeListeners.add(hcl);
	}
	
	/**
	 * Removes the given {@link HandChangeListener} from the list of listeners for this class. 
	 * @param hcl The {@link HandChangeListener} which is to end receiving events
	 */
	public void removeHandChangeListener(HandChangeListener hcl){
		handChangeListeners.remove(hcl);
	}
	
	/**
	 * Removes all {@link HandChangeListener}s from this class.
	 *
	 */
	public void removeAllHandChangeListeners(){
		handChangeListeners = new ArrayList<HandChangeListener>();
	}
	
	/**
	 * Propagates an event to all listeners, notifying them that the state of the Hand 
	 * has changed
	 * @param e the {@link HandEvent} which contains detailed information about the 
	 * changed state of the Hand
	 * @see HandEvent
	 */
	private void fireHandChangeEvent(HandEvent e){
		for(HandChangeListener hcl : handChangeListeners)
			hcl.handChanged(e);
	}
}
