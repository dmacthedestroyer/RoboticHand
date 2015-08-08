package logicalComponents;

import java.util.EventObject;

/**
 * The class which holds information about the changes in state of the {@link Hand}
 * class.  Whenever an event is fired by the Hand class, an instance of this class
 * is passed to each {@link HandChangeListener} which is registered to receive it.
 * @author DMac
 *
 */
public class HandEvent extends EventObject {
	/**
	 * The location of the joint which has changed state
	 */
	JointName loc;
	
	/**
	 * The new position of the joint which changed state
	 */
	double pos;
	
	/**
	 * The new minimum value of the joint which changed state
	 */
	int min;
	
	/**
	 * The new maximum value of the joint which changed state
	 */
	int max;
	
	/**
	 * Creates a new instance that has information about a particular {@link Joint}
	 * which has changed state.  Notice that with every change of state, both the min and max
	 * and position values are passed, so it is ambiguous to tell which values were modified.
	 * @param source The source of the event, in this case it is always from the Hand class
	 * @param location The location of this particular Joint which has changed state
	 * @param position The position of the Joint
	 * @param minPosition The minimum position of the Joint
	 * @param maxPosition The maximum position of the Joint
	 */
	public HandEvent(Object source, JointName location, double position, int minPosition, int maxPosition){
		super(source);
		
		loc = location;
		pos = position;
		min = minPosition;
		max = maxPosition;
	}

	/**
	 * Returns the {@link JointName} which refers to the Joint that changed state
	 * @return The JointName referencing which joint has changed state
	 */
	public JointName getJointName() {
		return loc;
	}

	/**
	 * Returns the maximum position of the Joint which changed state
	 * @return the maximum position
	 * @see Joint
	 */
	public int getMaxPosition() {
		return max;
	}

	/**
	 * Returns the minimum position of the Joint which changed state
	 * @return the minimum position
	 */
	public int getMinPosition() {
		return min;
	}

	/**
	 * Returns the value which would have returned by the 
	 * <code>Joint.getPosition</code> method for the Joint 
	 * that has changed state
	 * @return the position of the Joint relative to its min 
	 * and max values
	 */
	public double getPosition() {
		return pos;
	}
}
