/**
 * 
 */
package logicalComponents;

import java.awt.event.ActionListener;

/**
 * This is the interface all classes must implement if they wish to
 * receive event notifications from the {@link Hand} class.  All events 
 * are fired on the event thread, identically to {@link ActionListener}.
 * 
 * @author DMac
 *
 */
public interface HandChangeListener {
	
	/**
	 * This method is fired every time the state is changed in the Hand
	 * class.
	 * @param he the {@link HandEvent} which contains information about
	 * the change of state of the Hand class.
	 */
	public void handChanged(HandEvent he);
}
