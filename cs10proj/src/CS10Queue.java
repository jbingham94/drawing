/**
 * An interface for a Queue ADT
 *
 * The "CS10" is to avoid conflicting with the Queue interface 
 * in the Java library.
 * @author Scot Drysdale
 */
public interface CS10Queue<T> {
	
	/**
	 * Add item to rear of queue
	 * @param item item to be enqueued
	 */
	public void enqueue(T item);
	
	/**
	 * Remove item from front of queue
	 * @return the item removed from the front of the queue
	 */
	public T dequeue();
	
	/**
	 * Return the item at the front of queue, but do not remove it
	 * @return the item at the front of the queue
	 */
	public T front();
	
	/**
	 * Is the queue empty?
	 * @return true iff queue is empty
	 */
	public boolean isEmpty();

}
