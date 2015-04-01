/**
* Implementation of a queue using a linked list 
*
* @author Scot Drysdale 
* @see CS10Queue
*/
public class LinkedListQueue<T> implements CS10Queue<T> {
	
	private CS10LinkedList<T> queue;     // Holds the queue in a LL
	
	/**
	 *  Creates an empty queue
	 */
	public LinkedListQueue()  {
		queue = new SentinelDLL<T>();
	}

	/**
	 * <p>{@inheritDoc}
	 */
	public void enqueue(T item) {
		queue.addLast(item);
	}
	
	/**
	 * <p>{@inheritDoc}
	 */
	public T dequeue() {
		if (isEmpty())
		  return null;
		else
		{
		  T temp = queue.getFirst();
	  	queue.remove();   // Removes the thing we just got
		  return temp;
		}
	}
	
	/**
	 * <p>{@inheritDoc}
	 */
	public T front() {
		if (isEmpty())
		  return null;
		else
		{
		  return queue.getFirst();
		}
	}
	
	/**
	 * <p>{@inheritDoc}
	 */
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	 /**
	  * A testing program
	  */
  public static void main (String [] args)  {
  	LinkedListQueue<String> q = new LinkedListQueue<String>();
  	q.enqueue("cat");
  	q.enqueue("dog");
  	q.enqueue("bee");
  	System.out.println("Front is: " + q.dequeue());
  	System.out.println("Next front is: " + q.dequeue());
  	System.out.println("Is it empty? : " + q.isEmpty());
  	q.enqueue("eagle");
  	System.out.println("Next front is: " + q.dequeue());
  	System.out.println("Next front is: " + q.dequeue());
  	System.out.println("Is it empty? : " + q.isEmpty());
  	System.out.println("dequeue of empty stack: " + q.dequeue());
  	q.enqueue("bear");
  	System.out.println("front is: " + q.dequeue());
  	q.enqueue("cat");
  	q.enqueue("dog");
  	q.enqueue("sheep");
  	q.enqueue("cow");
  	q.enqueue("eagle");
  	q.enqueue("bee");
  	q.enqueue("lion");
  	q.enqueue("tiger");
  	q.enqueue("zebra");
  	q.enqueue("ant");
  	System.out.println("Bigger example:");
  	System.out.println("front is: " + q.dequeue());
  	System.out.println("Next front is: " + q.dequeue());
  	System.out.println("Next front is: " + q.dequeue());
  	System.out.println("Next front is: " + q.dequeue());
  	System.out.println("Next front is: " + q.dequeue());
  	System.out.println("Next front is: " + q.dequeue());
  	System.out.println("Next front is: " + q.dequeue());
  	System.out.println("Next front is: " + q.dequeue());
  	System.out.println("Next front is: " + q.dequeue());
  	System.out.println("Next front is: " + q.dequeue());
  	System.out.println("Next front is: " + q.dequeue());
  }
}
