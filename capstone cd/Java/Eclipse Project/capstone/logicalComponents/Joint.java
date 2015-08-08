package logicalComponents;
/**
 * Logically represents a single hinged joint with a fixed range of motion.  
 * The position of the joint is monitored in relation to a maximum and minimum
 * value, and its position is reported as a value between 0 and {@link Joint.MAX_VALUE}.  
 * <p>
 * The maximum value need not be greater than the minimum value in order to 
 * record accurate values for the position of a joint.  The position of a joint
 * is interpreted to be the percentage of the range from the minimum value to 
 * the position in comparison to the total fixed range of motion of the joint. 
 * This percentage is then multiplied by the maximim value to represent a point
 * in a fixed range regardless of the limitation of the actual maximum and 
 * minimum values. 
 * 
 * @author Daniel McDonald
 */
public class Joint {
	/**
	 * The minimum position at which this joint can be moved to.
	 */
	private int min;
	
	/**
	 * The maximum position at which this joint can be moved to.
	 */
	private int max;
	
	/**
	 * The current position of this joint.  This value must be between min and max.
	 */
	private int position;
	
	/**
	 * Creates an instance of the Joint class with default minimum and maximum
	 * values of 0 and {@link Joint.MAX_VALUE}, respectively.
	 * 
	 *@see Joint.MAX_VALUE
	 */
	public Joint(){
		this(0, 255);
	}

	/**
	 * Creates an instance of the Joint class with specified minimum and 
	 * maximum values of minimum and maximum, respectively.
	 * 
	 * @param minimum 	The minimum value of this Joint's range of motion.
	 * @param maximum	The maximum value of this Joint's range of motion.
	 */
	public Joint(int minimum, int maximum){
		min = minimum;
		max = maximum;
		position = 0;
	}

	/**
	 * Sets the position of this Joint to the specified value, if the argument
	 * is within the range of the Joint's minimum and maximum values.
	 * 
	 * @param pos	The position of the Joint
	 */
	public void setPosition(int pos){
		if(pos <= max && pos >= min || pos >=max && pos <= min){
			position = pos;
		}else if(pos > max)
			position = max;
		else if(pos < min)
			position = min;
	}

	/**
	 * Sets the maximum range of motion value for this Joint.  If the max 
	 * position is the same as the min position, then the min position is 
	 * decremented by 1 so as to prevent the joint from appearing fixed.
	 * This property has no use for this project.
	 * 
	 * @param pos	The new maximum position for this Joint's range of motion.
	 */
	public void setMax(int pos){
		max = pos;
		if(pos == min)
			min--;
	}
	
	/**
	 * Sets the minimum range of motion value for this Joint.  If the max 
	 * position is the same as the min position, then the max position is 
	 * incremented by 1 so as to prevent the joint from appearing fixed.
	 * This property has no use for this project.
	 * 
	 * @param pos	The new minimum position for this Joint's range of motion.
	 */
	public void setMin(int pos){
		min = pos;
		if(pos == max)
			max++;
	}

	/**
	 * Returns the position of this Joint in relation to {@link Joint.MAX_VALUE}.
	 * It determines the percentage of the "bendedness" of the Joint and then 
	 * reflects that percentage in terms of the range from 0 to 1.
	 * 
	 * @return The position of the Joint, between 0 and 1.
	 * 
	 * @see Joint.MAX_VALUE
	 */
	public double getPosition(){ 
		if(max>min)
			return (position-min)/((double)max-min);
		else
			return (min-position)/((double)min-max);
	}

	/**
	 * Returns the maximum value of this Joint's range of motion, which is not necessarily a value that is less than the maximum value.
	 * 
	 * @return	The maximum value of this Joint's range of motion.
	 */
	public int getMax(){ 
		return max;}
	
	/**
	 * Returns the minimum value of this Joint's range of motion, which is not necessarily a value that is less than the maximum value.
	 * 
	 * @return	The minimum value of this Joint's range of motion.
	 */
	public int getMin(){ 
		return min;}
}
