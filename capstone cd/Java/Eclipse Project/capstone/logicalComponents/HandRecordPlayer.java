package logicalComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * This class contains a collection of recordings which can be identified with
 * a title.  It reads in a file from a default location and holds information in the form of
 * a Hashtable with String as a key and ArrayList<Double> as the value.
 * @author dmac
 *
 */
public class HandRecordPlayer {
	private final String FILENAME = "binaries/recordings.hrp"; 
	
	private Hashtable<String, ArrayList<Double>> recordings;
	
	/**
	 * Default constructor.  Reads in from the default file location and loads a Hashtable.
	 *
	 */
	public HandRecordPlayer(){
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(FILENAME)));
			recordings = (Hashtable<String, ArrayList<Double>>)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns an array with all of the titles of recordings within this HandRecordPlayer.
	 * @return the set of titles
	 */
	public String[] getRecordingNames(){
		Object[] names = recordings.keySet().toArray();
		String[] stringNames = new String[names.length];
		for(int i=0; i<names.length; i++)
			stringNames[i] = ""+names[i];
		return stringNames;
	}
	
	/**
	 * Adds a recording with the name specified to this collection of recordings.  To see
	 * how the recording should be created, see {@link HandRecorder}.
	 * @param name the name which identifies this recording
	 * @param recording a list of Double values which map the movement of a {@link Hand}
	 */
	public void addRecording(String name, ArrayList<Double> recording){
		recordings.put(name, recording);
	}
	
	/**
	 * Deletes the recording with the given title.
	 * @param name
	 */
	public void removeRecording(String name){
		recordings.remove(name);
	}
	
	/**
	 * returns the recording with the given title
	 * @param name
	 * @return
	 */
	public ArrayList<Double> getRecording(String name){
		return recordings.get(name);
	}
	
	/**
	 * saves and changes made while the HandRecordPlayer was open.  It is recommended to end
	 * the use of this class with this method call, as it is the only way that information
	 * persists to the next load.
	 *
	 */
	public void close(){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("binaries/recordings.hrp")));
			oos.writeObject(recordings);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
