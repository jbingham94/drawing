import java.util.*;

/**
 * Generic binary tree, storing data of a parametric type in each node
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Scot Drysdale, Winter 2012.  Numerous modifications.
 */

public class BinaryTree<E> {
	private BinaryTree<E> left, right;	// children; can be null
	private E data;

	/**
	 * Constructs leaf node -- left and right are null
	 */
	public BinaryTree(E data) {
		this.data = data; this.left = null; this.right = null;
	}

	/**
	 * Constructs inner node
	 */
	public BinaryTree(E data, BinaryTree<E> left, BinaryTree<E> right) {
		this.data = data; this.left = left; this.right = right;
	}

	/**
	 * Is it an inner node?
	 */
	public boolean isInner() {
		return left != null || right != null;
	}

	/**
	 * Is it a leaf node?
	 */
	public boolean isLeaf() {
		return left == null && right == null;
	}

	/**
	 * Does it have a left child?
	 */
	public boolean hasLeft() {
		return left != null;
	}

	/**
	 * Does it have a right child?
	 */
	public boolean hasRight() {
		return right != null;
	}

	/**
	 * @return its left child 
	 */
	public BinaryTree<E> getLeft() {
		return left;
	}

	/**
	 * @return its right child 
	 */
	public BinaryTree<E> getRight() {
		return right;
	}

	/**
	 * Sets its left child to be newLeft 
	 * @param newLeft - the new left child
	 */
	public void setLeft(BinaryTree<E> newLeft) {
		left = newLeft;
	}

	/**
	 * Sets its right child to be newRight 
	 * @param newRight - the new right child
	 */
	public void setRight(BinaryTree<E> newRight) {
		right = newRight;
	}

	/**
	 * @return its data value 
	 */
	public E getValue() {
		return data;
	}

	/**
	 * Sets its data value 
	 * @param newValue - the new data value
	 */
	public void setValue(E newValue) {
		data = newValue;
	}

	/**
	 * Number of nodes (inner and leaf) in tree
	 */
	public int size() {
		int num = 1;
		if (hasLeft()) 
			num += left.size();
		if (hasRight()) 
			num += right.size();
		return num;
	}

	/**
	 * Longest path to a leaf node from here
	 */
	public int height() {
		if (isLeaf()) 
			return 0;
		else {
			int h = 0;
			if (hasLeft()) 
				h = Math.max(h, left.height());
			if (hasRight()) 
				h = Math.max(h, right.height());
			return h+1;						// inner: one higher than highest child
		}
	}	

	/**
	 * Same structure and data?
	 */
	public boolean equals(Object other) {
		if(!(other instanceof BinaryTree<?>))
			return false;
		BinaryTree<E> t2 = (BinaryTree<E>) other;
		if (hasLeft() != t2.hasLeft() || hasRight() != t2.hasRight()) return false;
		if (!data.equals(t2.data)) return false;
		if (hasLeft() && !left.equals(t2.left)) return false;
		if (hasRight() && !right.equals(t2.right)) return false;
		return true;
	}

	/**
	 * Leaves, in order from left to right
	 */
	public ArrayList<E> fringe() {
		ArrayList<E> f = new ArrayList<E>();
		addToFringe(f);
		return f;
	}

	/**
	 * Helper for fringe, adding fringe data to the list
	 */
	private void addToFringe(ArrayList<E> fringe) {
		if (isLeaf()) {
			fringe.add(data);
		}
		else {
			if (hasLeft()) 
				left.addToFringe(fringe);
			if (hasRight()) 
				right.addToFringe(fringe);
		}
	}

	/**
	 * Returns a string representation of the tree
	 */
	public String toString() {
		return toStringHelper("");
	}

	/**
	 * Recursively constructs a String representation of the tree from this node, 
	 * starting with the given indentation and indenting further going down the tree
	 */
	private String toStringHelper(String indent) {
		String ret = "";
		if (hasRight()) 
			ret += right.toStringHelper(indent+"  ");
		ret += indent + data + "\n";
		if (hasLeft()) 
			ret += left.toStringHelper(indent+"  ");
		return ret;
	}

	/** 
	 * Creates a list storing the the data values in the subtree of a node,
	 * ordered according to the preorder traversal of the subtree. 
	 * @param dataList - the list to be returned.
	 */
	public void preorder(List<E> dataList) {
		dataList.add(data);
		if (this.hasLeft())
			left.preorder(dataList);	// recurse on left child
		if (this.hasRight())
			right.preorder(dataList);	// recurse on right child
	}

	/** 
	 * Creates a list storing the the data values in the subtree of a node,
	 * ordered according to the inorder traversal of the subtree. 
	 * @param dataList - the list to be returned.
	 */
	public void inorder(List<E> dataList) {
		if (this.hasLeft())
			left.inorder(dataList);	// recurse on left child
		dataList.add(data);
		if (this.hasRight())
			right.inorder(dataList);	// recurse on right child
	}

	/** 
	 * Creates a list storing the the data values in the subtree of a node,
	 * ordered according to the preorder traversal of the subtree. 
	 * @param dataList - the list to be returned.
	 */
	public void postorder(List<E> dataList) {
		if (this.hasLeft())
			left.postorder(dataList);	// recurse on left child
		if (this.hasRight())
			right.postorder(dataList);	// recurse on right child
		dataList.add(data);
	}

	/**
	 * Reconstructs tree from a preorder and inorder traversal, assuming that
	 * no value is repeated.
	 * Precondition: the traversals are valid.  (Otherwise need lots of error checks.)
	 */
	public static <E> BinaryTree<E> reconstructTree (List<E> preorder, 
			List<E> inorder) {

		BinaryTree<E> returnTree;  // Tree to return

		if(preorder.size() > 0) {  // Is tree non-empty?

			// Create iterators to walk through lists and new lists for recursive calls
			Iterator<E> iterPre = preorder.iterator();
			Iterator<E> iterIn = inorder.iterator();
			List<E> leftPre = new LinkedList<E>();
			List<E> rightPre = new LinkedList<E>();
			List<E> leftIn = new LinkedList<E>();
			List<E> rightIn = new LinkedList<E>();

			E rootValue = iterPre.next();  // First thing in preorder is root of tree
			E inValue = iterIn.next();

			// Find things in left subtree and copy into leftPre and leftIn
			while(!rootValue.equals(inValue)) {
				leftPre.add(iterPre.next());
				leftIn.add(inValue);
				inValue = iterIn.next();		  	
			}

			BinaryTree<E> leftSubtree =reconstructTree(leftPre, leftIn);

			// Copy things in right subtree into rightPre and rightIn
			while(iterPre.hasNext()) {
				rightPre.add(iterPre.next());
				rightIn.add(iterIn.next());		  
			}

			BinaryTree<E> rightSubtree = reconstructTree(rightPre, rightIn);

			returnTree = new BinaryTree<E>(rootValue, leftSubtree, rightSubtree);  
		}
		else
			returnTree = null;  // Empty tree in this case

		return returnTree;
	}

	/**	
	 * Testing program
	 */
	public static void main(String [] args) {	

		// Create a tree by building it up

		BinaryTree<String> leftChild = new BinaryTree<String>("bear",
				new BinaryTree<String>("ant"), new BinaryTree<String>("cat"));
		BinaryTree<String> tree= new BinaryTree<String>("cow", leftChild,
				new BinaryTree<String>("dog"));


		// Some tests of methods
		System.out.println("tree: \n" + tree); 
		System.out.println("Size of tree = " + tree.size());
		System.out.println("Height of tree = " + tree.height());
		System.out.println("Fringe of tree =" + tree.fringe());

		// Build a tree from traversals
		List<String> preList = new LinkedList<String>();
		tree.preorder(preList);
		System.out.println("Preorder traversal of the tree =" + preList);

		List<String> inList = new LinkedList<String>();
		tree.inorder(inList);
		System.out.println("Inorder traversal of the tree =" + inList);

		List<String> postList = new LinkedList<String>();
		tree.postorder(postList);
		System.out.println("Postorder traversal of the tree =" + postList);

		BinaryTree<String> tree1 = reconstructTree(preList, inList);
		System.out.println("tree1: \n" + tree1); 
		System.out.println("tree and tree1 are equal? " + tree.equals(tree1));

		tree1.setValue("cougar");
		System.out.println("tree: \n" + tree); 
		System.out.println("modified tree1: \n" + tree1); 
		System.out.println("tree and tree1 are equal? " + tree.equals(tree1));

		BinaryTree<String> tree2= reconstructTree(preList, inList);
		tree2.getLeft().setLeft(null);
		System.out.println("tree2: \n" + tree2); 
		System.out.println("Size of tree2 = " + tree2.size());
		System.out.println("Height of tree2 = " + tree2.height());
		System.out.println("Fringe of tree2 =" + tree2.fringe());
		System.out.println("tree and tree2 are equal? " + tree.equals(tree2));
	}		
}
