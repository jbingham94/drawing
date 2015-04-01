/**
 * SentinelDLL.java
 * 
 * Implementation of circular, doubly linked list with a sentinel 
 * WARNING: This implementation is guaranteed to work only if always given
 *   objects that are immutable (or at least do not change).
 * Handling errors by throwing exceptions would be a better way of
 * dealing with errors, but this is a demo program.
 *   
 * @author THC
 * @author Scot Drysdale converted it to Java.
 * @author THC and Scot Drysdale have made a number of modifications.
 * @author Scot Drysdale most recently modified on 1/12/2011
 */
public class SentinelDLL<T> implements CS10LinkedList<T> {
  private Element<T> sentinel; // sentinel, serves as head and tail
  private Element<T> current;  // current position in the list
 
  /**
   * A private inner class representing the elements in the list.
   */
  private static class Element<T> {
    // Because this is a private inner class, these can be seen from SentinelDLL,
    // but not from outside SentinelDLL.
    private T data;                  // reference to data stored in this element
    private Element<T> next;            // reference to next item in list
    private Element<T> previous;        // reference to previous item in list
    
    /**
     * Constructor for a linked list element, given an object.
     * @param obj the data in the element.
     */
    public Element(T obj) {
      next = previous = null;   // no element before or after this one, yet
      data = obj;               // OK to copy reference, since obj references an immutable object
    }

    /**
     *  @return the String representation of a linked list element.
     */
    public String toString() {
      return data.toString();
    }
  }

  /**
   * Constructor for a circular, doubly linked list with a sentinel.
   * Makes an empty list.
   */
  public SentinelDLL() {
    sentinel = new Element<T>(null);
    clear();
  }

  /*
   * @see CS10LinkedList#clear()
   */
  public void clear() {
    // Make the list be empty by having the sentinel point to itself
    // in both directions.
    sentinel.next = sentinel.previous = sentinel;
  
    // There's only one place to put the current reference...
    current = sentinel;
  }

  /**
   * @see CS10LinkedList#add()
   */
  public void add(T obj) {
    Element<T> x = new Element<T>(obj);     // allocate a new element

    // Splice in the new element.
    x.next = current.next;
    x.previous = current;
    current.next.previous = x;
    current.next = x;

    current = x;                        // new element is current position
  }

  /**
   *   * @see CS10LinkedList#remove()
   */
  public void remove() {
    // Do not ever let the sentinel be deleted!
    if (hasCurrent()) {          
      // Splice out the current element.
      current.previous.next = current.next;
      current.next.previous = current.previous;
        
      current = current.next;           // make successor the new current
    }
    else 
      System.err.println("No current item");
  }

  /**
   * @return the String representation of this list.
   */
  public String toString() {
    String result = "";
    
    for (Element<T> x = sentinel.next; x != sentinel; x = x.next)    
      result += x.toString() + "\n"; 
    
    return result;
  }

  /**
   * @see CS10LinkedList#contains()
   */
  public boolean contains(T obj) {
    Element<T> x;
  
    // Since we have a sentinel that isn't being used for a "real" element,
    // we put the object we are looking for in the sentinel.  That way, we
    // know we'll find it some time during the search.
    sentinel.data = obj;
  
    for (x = sentinel.next; !x.data.equals(obj); x = x.next)
      ;
  
    // We dropped out of the loop because we found the target object in an element
    // that x references.
    // If we found it in the sentinel, it wasn't really in the list.
    // If we found it before getting back to the sentinel, it was in the list.

    // Put the sentinel back into its null state.
    sentinel.data = null;

    if (x == sentinel)
      return false;             // tell the caller that we did not find the object
    else {
      current = x;              // remember where we found it
      return true;              // and tell the caller
    }
  }

  /**
   * @see CS10LinkedList#isEmpty()
   */
  public boolean isEmpty() {
    return sentinel.next == sentinel;
  }
  
  /**
   * @see CS10LinkedList#hasCurrent()
   */
  public boolean hasCurrent() {
    return current != sentinel;
  }
  
  /**
   * @see CS10LinkedList#hasNext()
   */
  public boolean hasNext() {
    return hasCurrent() && current.next != sentinel;
  }

  /**
   * @see CS10LinkedList#getFirst()
   */
  public T getFirst() { 
    if(isEmpty()) {
      System.err.println("The list is empty");
      return null;
    }
    current = sentinel.next;
    return get();
  }

  /**
   * @see CS10LinkedList#getLast()
   */
  public T getLast() {
    if(isEmpty()) {
      System.err.println("The list is empty");
      return null;
    }
    current = sentinel.previous;
    return get();
  }

  /**
   * @see CS10LinkedList#addFirst()
   */
  public void addFirst(T obj) {
    current = sentinel;
    add(obj);
  }

  /**
   * @see CS10LinkedList#addLast()
   */
  public void addLast(T obj) {
    current = sentinel.previous;
    add(obj);
  }
  
  /**
   * @see CS10LinkedList#get()
   */
  public T get() {
    if (hasCurrent()) 
      return current.data;
    else {
      System.err.println("No current item");
      return null;
    }

  }
  
  /**
   * @see CS10LinkedList#set()  
   */
  // Error if there is no current item.
  public void set(T obj) {
    if (hasCurrent())
      current.data = obj;
    else
      System.err.println("No current item");
  }
  
  /**
   * @see CS10LinkedList#next()
   */
  // Moves current to the next position.  Error if there is no next item.
  public T next() {
    if (hasNext()) {
      current = current.next;
      return current.data;
    }
    else {
      System.err.println("No next item");
      return null;
    }
  }
  /** 
   * Appends the list "other" to the end of the list "this", 
   * and sets the "other" to an empty list. 
   * the current element of "this" remains the current element of the combined list. 
   * @param other the list to be appended to this list. 
   */ 
  public void append(SentinelDLL<T> other) 
  {
  	this.getLast();
  	current.next = other.getFirst();
  	other.getFirst().previous = current;
  }
  public static void main(String [] args) 
  { 
  	SentinelDLL<String> lst1 = new SentinelDLL<String>(); 
  	SentinelDLL<String> lst2 = new SentinelDLL<String>(); 
  	lst1.append(lst2); 
  	System.out.println("lst1:\n" + lst1); 
  	System.out.println("lst2:\n" + lst2); 
  	lst1.addFirst("cat"); 
  	lst1.addLast("dog"); 
  	lst1.append(lst2); 
  	System.out.println("lst1:\n" + lst1); 
  	System.out.println("lst2:\n" + lst2); 
  	lst1.clear(); 
  	lst2.addFirst("cat"); 
  	lst2.addLast("dog"); 
  	lst1.append(lst2); 
  	System.out.println("lst1:\n" + lst1); 
  	System.out.println("lst2:\n" + lst2); 
  	lst2 = new SentinelDLL<String>(); 
  	lst2.addFirst("eagle"); 
  	lst2.addLast("sheep"); 
  	lst1.append(lst2); 
  	System.out.println("lst1:\n" + lst1); 
  	System.out.println("lst2:\n" + lst2); 
  	System.out.println("lst1 in reverse:"); 
  	for (Element<String> x = lst1.sentinel.previous; x != lst1.sentinel; x = x.previous) 
  		System.out.println(x.data); 
  }
}
