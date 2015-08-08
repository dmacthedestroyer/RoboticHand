package logicalComponents;

/**
 * <p>This class contains an assortment of hand gestures that are used primarily
 * by the {@link Controller} class to make the robotic hand gesture in a certain way.</p> 
 * <p>This class is dependent upon the {@link JointName} class in that, if that class is modified, 
 * this class also must be modified to work successfully.</p>
 * @author dmac
 *
 */
public class HandGestures {
	public static final double[] ROCK = {1,1,1,1,1,1,1,1};
	public static final double[] PAPER = {0,0,0,0,0,0,0,0};
	public static final double[] SCISSORS = {0,0,0,0,1,1,1,1};
	public static final double[] NEUTRAL = {.5,.5,.5,.5,.5,.5,.5,.5};
	public static final double[] HOOKEMHORNS = {1,1,0,0,0,0,1,1};
	public static final double[] DOCTOREVIL = {0,0,0,0,0,0,1,1};
	public static final double[] THEBIRD = {0,0,1,1,0,0,0,0,};
}
