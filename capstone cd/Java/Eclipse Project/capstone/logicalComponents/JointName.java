package logicalComponents;

import java.awt.Point;
import java.io.Serializable;

/**
 * An enum class which contains the information of the maximum and minimum locations of each servo in respect to the joint which it represents.  If any structural changes are to be made to the hand, then the software must be updated here to meet that.
 * @author dmac
 *
 */
public enum JointName implements Serializable{
//	T_D, //THUMB_DISTAL,		//blue-white on power cord
//	T_M,  //THUMB_METACARPAL,	//blue on power cord

	I_M(60, 270, 11, 30, true), //**INDEX_METACARPAL,	//orange-white
	I_D(55, 165, 18, 32, false), //**INDEX_DISTAL,		//orange  PAD2
	M_M(175, 265, 20, 30, true), //MIDDLE_METACARPAL,	//brown
	M_D(180, 165, 25, 28, false), //**MIDDLE_DISTAL,		//brown-white
	R_M(280,290, 16, 26, false), //**RING_METACARPAL,		//green-white
	R_D(295,190, 20, 31, true), //RING_DISTAL,			//green
	P_M(370,340, 33, 20, false), //**PINKY_METACARPAL		//blue
	P_D(390,260, 15, 36, true); //PINKY_DISTAL,		//blue-white
	
	/**
	 * The x-position of the repsective joint on the image given in {@link graphicalComponents.HandGraph}.  This should not be here, but was not migrated to the proper place.
	 */
	private int xPosition;
	
	/**
	 * The y-position of the repsective joint on the image given in {@link graphicalComponents.HandGraph}.  This should not be here, but was not migrated to the proper place. 
	 */
	private int yPosition;
	
	/**
	 * The minimum position of the servo which moves the respective joint.  The value corresponds to the pulse width modulation block of the Dragon12 board.
	 */
	private int min;
	
	/**
	 * The maximum position of the servo which moves the respective joint.  The value corresponds to the pulse width modulation block of the Dragon12 board. 
	 */
	private int max;
	
	/**
	 * Some of the servos are located on the hand in reverse order.  In this case, the calculations must be done differently, so this is a flag to determine how to calculate the PWM value for the servo.
	 */
	private boolean reversed;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param min
	 * @param max
	 * @param isReversed
	 */
	private JointName(int x, int y, int min, int max, boolean isReversed){
		xPosition = x;
		yPosition = y;
		this.min = min;
		this.max = max;
		reversed = isReversed;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getX(){
		return xPosition;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getY(){
		return yPosition;
	}
	
	/**
	 * 
	 * @param jointPosition
	 * @return
	 */
	public int getServoPosition(double jointPosition){
		if(reversed)
			jointPosition = 1 - jointPosition;
		
		return (int)(min + max * jointPosition);
	}
	
	/**
	 * 
	 * @return
	 */
	public Point getLocation(){
		return new Point(xPosition, yPosition); 
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isDistal(){
		return (this.equals(JointName.I_D) || this.equals(JointName.M_D) || this.equals(JointName.R_D) || this.equals(JointName.P_D));
	}
}