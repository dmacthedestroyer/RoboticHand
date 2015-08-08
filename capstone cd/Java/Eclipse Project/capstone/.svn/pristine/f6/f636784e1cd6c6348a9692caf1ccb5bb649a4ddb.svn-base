package graphicalComponents;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JComponent;

import logicalComponents.HandChangeListener;
import logicalComponents.HandEvent;
import logicalComponents.JointName;

/**
 * A component created which graphically displays all joints at the same time.  It was basically made to look cool, but never ended up in the final UI.
 * @author dmac
 *
 */
public class HandGraph extends JComponent implements HandChangeListener {
	private Map<JointName, GraphList<Double>> jointValues; 
	protected int width, height;
	private int maxSize;


	public HandGraph(){
		this(250, 100);
	}

	public HandGraph(int w, int ht){
		width = w;
		height = ht;
		maxSize = getWidth()/10;
		jointValues = new Hashtable<JointName, GraphList<Double>>();
		for(JointName jn : JointName.values())
			jointValues.put(jn, new GraphList<Double>(maxSize));
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}
	
	public int maxEntries(){
		return maxSize;
	}
	
	public void setMaxEntries(int entries){
		maxSize = entries;
		for(JointName jn : JointName.values()){
			jointValues.get(jn).setMaxSize(maxSize);
		}
	}

	public Dimension getPreferredSize(){
		return new Dimension(width, height);
	}

	public void handChanged(HandEvent je) {
		jointValues.get(je.getJointName()).add(je.getPosition());
		repaint();
	}

	protected void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		RoundRectangle2D.Float bgRect = new RoundRectangle2D.Float(0,0,getWidth()-3, getHeight()-3, 12, 12);
		g2d.setClip(bgRect);

		if(isOpaque()){
			g2d.setPaint(getBackground());
			g2d.fill(bgRect);
		}else{
			g2d.setPaint(new Color(1,1,1,0.5f));
			g2d.fill(bgRect);
		}
		
		g2d.setStroke(new BasicStroke(2.0f));

		for(int jointIndex = 0; jointIndex < JointName.values().length; jointIndex++){
			float[] xPositions, yPositions;
			xPositions = new float[jointValues.get(JointName.values()[jointIndex]).size()];
			yPositions = new float[xPositions.length];
			for(int valueIndex = 0; valueIndex<xPositions.length; valueIndex++){
				xPositions[valueIndex] = valueIndex * getWidth()/(1.0f*maxSize-1);
				yPositions[valueIndex] = (1 - (float)((double)jointValues.get(JointName.values()[jointIndex]).get(valueIndex))) * getHeight();
			}
			g2d.setPaint(new Color(jointIndex * 16777216/JointName.values().length));

			GeneralPath path = new GeneralPath();
			if(xPositions.length > 0)
				path.moveTo(xPositions[0], yPositions[0]);

			for(int index = 1; index<xPositions.length; index++)
				path.lineTo(xPositions[index], yPositions[index]);
			g2d.draw(path);
		}
		
		g2d.setStroke(new BasicStroke(5.0f));
		g2d.setPaint(Color.BLACK);
		g2d.draw(bgRect);
	}

}
