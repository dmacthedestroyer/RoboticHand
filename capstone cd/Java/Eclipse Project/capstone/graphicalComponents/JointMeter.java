package graphicalComponents;
/**
 *	JointMeter class
 *		This class graphically represents a joint by means of a bar that fills higher when the max is reached
 *		See Port.java for an example of what it looks like.
 */
 
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;

import logicalComponents.Joint;
import deprecated.JointChangeListener;
import deprecated.JointEvent;



/**
 * This class graphically represents a singe Joint as a vertical bar.  When the bar is completely filled, it is 
 * meant that the joint is completely flexed.  When the bar is completely empty, then the joint is interpreted 
 * as completely straight.  It was originally designed to rely on an ActionListener which kept track of a single
 * {@link Joint}'s state, but then was modified to act as a part of the {@link HandMeter} class, and is manually 
 * updated as a result of events received from the ActionListener of the Hand class.  A lot of the graphics have
 * been disabled as a result of poor performance, but remain in comments in the paintComponent method.
 * @author  Owner
 */
public class JointMeter extends JComponent{// implements JointChangeListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7528536609517938760L;
	public static final int DEFAULT_WIDTH = 50;
	public static final int DEFAULT_HEIGHT = 150;
	
	
	private int width;
	private int height;
	private double position;

	public JointMeter(Joint j, int w, int h){
		this(w, h);
	}
	
	public JointMeter(Joint j){
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public JointMeter(){
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public JointMeter(int w, int h){
		width = w;
		height = h;
		position = 0.0;
	}

	//getters used with packing for LayoutManagers
	/**
	 * @return  the height
	 * @uml.property  name="height"
	 */
	@Override
	public int getHeight(){return height;}
	/**
	 * @return  the width
	 * @uml.property  name="width"
	 */
	@Override
	public int getWidth(){return width;}
	@Override
	public Dimension getPreferredSize(){return new Dimension(width, height);}

	
	/**
	 *	paints this component
	 */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		int cornerArc = (int)(getWidth()/1.7);
		
		Graphics2D g2d = (Graphics2D)g.create();
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		RoundRectangle2D bgRect, positionRect;
		

		//draw the current value
		int yCoord = (int)(height*(1-position));	//the y-coordinate for the percentage

		bgRect = new RoundRectangle2D.Double(0,0,getWidth()-4, getHeight(), cornerArc, cornerArc);
		positionRect = new RoundRectangle2D.Double(0, yCoord-(getHeight()/8.0f), getWidth()-2, getHeight()-yCoord+(getHeight()/8.0f), cornerArc, cornerArc);
		
		g2d.setClip(bgRect);
		
		g2d.setPaint(new Color(1,1,1,0.3f));
		g2d.fill(bgRect);
		
		
//		float alpha = (float)(position/1.3);
//		if(alpha >1)
//			alpha = 1;
//		if(alpha < 0)
//			alpha = 0;
//		g2d.setPaint(new Color(1.0f, 0.0f, 0.0f,alpha));
//		g2d.fill(bgRect);
g2d.setPaint(Color.blue);
//		g2d.setPaint(new GradientPaint(getWidth()/2, getHeight()-(float)positionRect.getHeight(), new Color(1.0f,1.0f,1.0f, 0), 
//				getWidth()/2, getHeight()-(float)positionRect.getHeight() +getHeight()/8.0f, new Color(0.0f, 0.0f, 1.0f, 0.6f)));
		g2d.fill(positionRect);
		
//		g2d.setPaint(new Color(0.0f, 0.0f, 0.0f, 0.5f));
//		g2d.setStroke(new BasicStroke(getHeight()/50.0f));
		
//		g2d.draw(new Line2D.Double(0, getHeight()/2.0, getWidth(), getHeight()/2.0));
//		g2d.draw(new Line2D.Double(0, getHeight()/4.0, getWidth(), getHeight()/4.0));
//		g2d.draw(new Line2D.Double(0, 3*getHeight()/4.0, getWidth(), 3*getHeight()/4.0));
		
		g2d.setPaint(Color.BLACK);
		g2d.setStroke(new BasicStroke(getWidth()/6.0f));
		g2d.draw(bgRect);
		
		g2d.dispose();
	}
	
//	public void jointChanged(JointEvent j){
//		position = j.getPosition();
//		
//		this.repaint();
//	}

	public void updateUI(double position) {
		this.position = position;
		
		repaint();
	}
}