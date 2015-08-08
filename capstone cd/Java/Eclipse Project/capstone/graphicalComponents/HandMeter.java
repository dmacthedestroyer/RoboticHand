package graphicalComponents;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logicalComponents.Hand;
import logicalComponents.HandChangeListener;
import logicalComponents.HandEvent;
import logicalComponents.JointName;

/**
 * This class is a component which displays the current state of a {@link Hand}.  
 * It gets information from the {@link JointName} class, which it shouldn't.  That is an example of
 * poor coding and lack of motivation to fix it.  It implements {@link HandChangeListener}, which means
 * that updating the image is done automatically when the Hand that is listens to changes state.
 * @author dmac
 *
 */
public class HandMeter extends JPanel implements HandChangeListener {
	private final int JOINTMETER_WIDTH = 40;
	private final int JOINTMETER_HEIGHT = 70;

	private Image bgImage;

	private Hashtable<JointName, JointMeter> hMeter;



	public HandMeter(){
		super(null);

		hMeter = new Hashtable<JointName, JointMeter>();

		try{
			bgImage = ImageIO.read(new File(getClass().getResource("../images/Hand.gif").toURI()));
			JLabel bgLabel = new JLabel(new ImageIcon(getClass().getResource("../images/Hand.gif")));
			bgLabel.setLocation(15, 15);
			add(bgLabel);
			System.out.println("bg image added");
		}catch(Exception e){e.printStackTrace();}
		
		for(JointName jn : JointName.values()){
			hMeter.put(jn, new JointMeter(JOINTMETER_WIDTH, JOINTMETER_HEIGHT));
			hMeter.get(jn).setLocation(jn.getX()+15, jn.getY()+15);
			add(hMeter.get(jn));
		}
	}

	public static HandMeter createHandMeter(Hand h){
		HandMeter hm = new HandMeter();
		h.addHandChangeListener(hm);

		return hm;
	}

	public int getWidth(){
		return bgImage.getWidth(null) + 30;
	}

	public int getHeight(){
		return bgImage.getHeight(null) + 30;
	}

	public Dimension getPreferredSize(){
		return new Dimension(getWidth(), getHeight());
	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g.create();
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Shape bg = new RoundRectangle2D.Float(0,0,getWidth(), getHeight(), 8, 8);
		g2d.setClip(bg);
//
//
//
//		g2d.setPaint(new GradientPaint(getWidth()/2, getHeight()/4, getBackground(), getWidth(), getHeight(), new Color(22, 240, 217)));
//		g2d.fill(bg);
		
		g2d.setPaint(Color.BLACK);
		g2d.setStroke(new BasicStroke(3.0f));

		g2d.draw(bg);
		

//////	draw the picture of the hand

		Shape imageShape = new RoundRectangle2D.Float(15, 15, bgImage.getWidth(null), bgImage.getHeight(null), 8, 8);
		g2d.setClip(imageShape);

		g2d.drawImage(bgImage, 15, 15, null);
//		paintChildren(g2d);

		g2d.setPaint(Color.BLACK);
		g2d.setStroke(new BasicStroke(3.0f));

		g2d.draw(imageShape);

		g2d.dispose();


	}

	public void handChanged(HandEvent he) {
		JointName jn = he.getJointName();

		hMeter.get(jn).updateUI(he.getPosition());

		repaint();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame("new hand graph class");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.add(new HandMeter());

		f.pack();
		f.setVisible(true);


	}

}
