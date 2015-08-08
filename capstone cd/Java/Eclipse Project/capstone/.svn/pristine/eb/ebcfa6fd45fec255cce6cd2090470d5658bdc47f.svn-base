package deprecated;
import java.util.ArrayList;
import java.util.List;



/**
 * 
 */

/**
 * @author mcdonadj
 *
 */
public class JointRecorder implements JointChangeListener {
	private boolean recording;
	private List<Double> pointsList;
public JointRecorder(){
		recording = false;
		pointsList = new ArrayList<Double>();
	}

	public void start(){
		recording = true;
	}

	public List<Double> stop(){
		recording = false;

		return pointsList;
	}

	/* (non-Javadoc)
	 * @see JointChangeListener#jointChanged(JointEvent)
	 */
	public void jointChanged(JointEvent je) {
		if(recording)
			try{
				pointsList.add(je.getPosition());
			}catch(NullPointerException npe){}

	}
}
