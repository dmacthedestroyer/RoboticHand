package deprecated;

import graphicalComponents.JointMeter;

import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logicalComponents.HandChangeListener;
import logicalComponents.HandEvent;
import logicalComponents.JointName;

public class HandMeterdeprecated extends JPanel implements HandChangeListener{
	private Hashtable<JointName, JointMeter> hMeter;
	
	public HandMeterdeprecated(int componentWidth, int componentHeight){
		super();
		
		hMeter = new Hashtable<JointName, JointMeter>();
		
		for(JointName jn : JointName.values()){
			hMeter.put(jn, new JointMeter(componentWidth, componentHeight));
			
			JPanel jointPanel = new JPanel();
			jointPanel.setLayout(new BoxLayout(jointPanel, BoxLayout.Y_AXIS));
			
			jointPanel.add(new JLabel(""+jn));
			jointPanel.add(hMeter.get(jn));
			
			add(jointPanel);
		}
	}
	
	public HandMeterdeprecated(){
		this(JointMeter.DEFAULT_WIDTH, JointMeter.DEFAULT_HEIGHT);
	}

	public void handChanged(HandEvent he) {
		JointName jn = he.getJointName();
		
		hMeter.get(jn).updateUI(he.getPosition());
	}
}
