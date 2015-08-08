package deprecated;
import graphicalComponents.GraphList;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;

import logicalComponents.Joint;


/**
 * Graphically represents a series of positions of the Joint(s) which this 
 * JointGraph is registered to.
 * 
 * @author  Daniel McDonald
 */
public class JointGraph extends JComponent implements JointChangeListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5222692970398510843L;

	protected int height;
	protected int width;
	protected GraphList<Double> positionPoints;

	public JointGraph(int w, int ht){
		height = ht;
		width = w;
		positionPoints = new GraphList<Double>(getWidth()/8);
	}

	public JointGraph(){
		this(100, 250);
	}

	public JointGraph(Joint j){
		this();
	}

	public JointGraph(Joint j, int ht, int w){
		this(ht, w);
	}

	public void jointChanged(JointEvent j) {
		positionPoints.add(j.getPosition());

		this.repaint();

	}

	/**
	 * @return  the width
	 * @uml.property  name="width"
	 */
	@Override
	public int getWidth(){return width;}
	/**
	 * @return  the height
	 * @uml.property  name="height"
	 */
	@Override
	public int getHeight(){return height;}
	@Override
	public Dimension getPreferredSize(){return new Dimension(width, height);}

	@Override
	protected void paintComponent(Graphics g){
		RoundRectangle2D.Float bg = new RoundRectangle2D.Float(0.0f,0.0f,getWidth()-3, getHeight()-3, 12, 12);
		
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setClip(bg);
		
		if(isOpaque()){
			g2d.setPaint(getBackground());
			g2d.fill(new Rectangle2D.Float(0, 0, getWidth(), getHeight()));
		}

		int colWidth = getWidth()/(positionPoints.size()+1);
		int colX = 0;
		//Iterator<Double> currentPositionPointsIterator = positionPoints.iterator();

		for(int i=0; i<positionPoints.size(); i++){
			double current = positionPoints.get(i);

			int yOrigin = (int)(getHeight()* (1 - current));
			int colHeight = getHeight() - yOrigin;

			g2d.setPaint(Color.BLUE);
			RoundRectangle2D.Float col = new RoundRectangle2D.Float(colX+1, yOrigin+1, colWidth-2, colHeight, colWidth, 5);
			g2d.fill(col);
			
			g2d.setPaint(Color.black);
			g2d.draw(col);
			
			colX += colWidth;
			
		}

		g2d.setStroke(new BasicStroke(3.0f));
		g2d.draw(bg);

		g2d.dispose();

	}

}
