package deprecated;
import java.util.EventObject;

import logicalComponents.Joint;
/**
 * An event that indicates that an action has occurred with a Joint.  This 
 * event is generated when a joint's location, minimum value, or maximum value
 * has been updated.
 * <p>
 * The object that implements the {@link JointChangeListener} interface will
 * receive these events to receive updated information about the joint 
 * concerned.
 * 
 * @author  Owner
 * 
 * @see JointChangeListener
 * @see Joint
 */
public class JointEvent extends EventObject {

	private static final long serialVersionUID = 769914406666198236L;
	private int maximum, minimum;
	private double position;

	/**
	 * Creates a JointEvent object with the position, max and min information.
	 * 
	 * @param source	The object that originated the event.
	 * @param pos	The position of the joint which fired the event.
	 * @param max	The maximum position of the joint which fired the event.
	 * @param min	The minimum position of the joint which fired the event.
	 */
	public JointEvent(Object source, double pos, int max, int min){
		super(source);
		position = pos;
		maximum = max;
		minimum = min;
	}

	/**
	 * returns the position of the Joint which fired the event.
	 * 
	 * @return the position of the Joint.
	 * 
	 * @see Joint
	 */
	public double getPosition(){
		return position;
	}

	/**
	 * Returns the maximum position of the Joint which fired the event.
	 * 
	 * @return the maximum position of the Joint.
	 * 
	 * @see Joint
	 */
	public int getMaxPosition(){
		return maximum;
	}

	/**
	 * Returns the minimumPosition of the Joint which fired the event.
	 * 
	 * @return	the minimum position of the Joint.
	 * 
	 * @see Joint
	 */
	public int getMinPosition(){
		return minimum;
	}
}
