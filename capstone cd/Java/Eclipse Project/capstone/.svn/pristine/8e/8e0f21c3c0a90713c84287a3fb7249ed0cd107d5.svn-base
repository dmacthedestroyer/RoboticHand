package graphicalComponents;
/**
 * A simple implementation of a Queue which only contains a specified number of
 * entries.  Once the size limit has been reached, then the excess entries are
 * dequeued.  It is called GraphQueue because it was created for and used 
 * solely by the {@link JointGraph} class.
 * 
 * @author Daniel McDonald
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class GraphList<E> {
	
	private LinkedList<E> list;
	private int maxEntries;

	/**
	 * Creates a new GraphList instance with a default maximum size of 250.
	 *
	 */
	public GraphList() {
		this.list = new LinkedList<E>();
		this.maxEntries = 250;
	}
	
	/**
	 * Creates a new GraphList instance with the maximum size specified by max
	 * 
	 * @param max The maximum size allowed for this list.
	 */
	public GraphList(int max){
		this.list = new LinkedList<E>();
		this.maxEntries = max;
	}
	
	/**
	 * Enqueues item to this GraphList.  If the size limit has been reached, then the 
	 * next item to be dequeued is returned, otherwise null is returned.
	 *  
	 * @param item the item to enqueue to this GraphList
	 * @return	the dequeued item if the size limit is reached: otherwise null.
	 */
	public E add(E item){
		this.list.add(item);
		
		if(this.list.size() == this.maxEntries)
			return this.list.removeFirst();
		return null;
	}
	
	public E get(int index){
		return list.get(index);
	}
	
	/**
	 * Sets the maximum size of this GraphList.  If the previous size allowed for more
	 * items than this new size, and there are more items in the GraphList than the new
	 * size will allow, then enough items are dequeued until the size limitation is 
	 * satisfied.  These items are returned as a Collection.
	 * 
	 * @param max	The new maximum size of this GraphList.
	 * @return	A Collection of all excess items dequeued when the size changed.
	 * 
	 * @see java.util.Collection
	 */
	public Collection<E> setMaxSize(int max){
		this.maxEntries = max;
		
		List<E> excess = new LinkedList<E>();
		while(this.list.size() > this.maxEntries)
			excess.add(this.list.removeFirst());
		
		return excess;
	}
	
	/**
	 * Removes all items from this GraphList and returns them as a Collection.
	 * 
	 * @return	The Collection of all items removed from this GraphList.
	 * 
	 * @see java.util.Collection
	 */
	public Collection<E> clear(){
		List<E> excess = new LinkedList<E>();
		while(this.list.size() > 0)
			excess.add(this.list.removeFirst());
		
		this.list = new LinkedList<E>();
		
		return excess;
	}
	
	public Object[] copy(){
		return this.list.toArray();
	}
	
	/**
	 * Returns the number of items in this GraphList.  Notice this is different from the
	 * maximum size.
	 * 
	 * @return The number of items currently in this GraphList
	 */
	public int size(){
		return this.list.size();
	}
	
	public int maximumSize(){
		return maxEntries;
	}
	
	/**
	 * Returns an Iterator object to traverse through the items in this GraphList
	 * 
	 * @return The Iterator object for this GraphList.
	 * 
	 * @see	java.util.Iterator
	 */
	public Iterator<E> iterator(){
		return this.list.listIterator();
	}
}
