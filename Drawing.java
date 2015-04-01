import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Class which represents a drawing of different shapes.
 * Shapes in the drawing have different depth levels.
 * @author Jasper Bingham
 */
public class Drawing {
	private Color myColor = null;
	private Shape currShape = null;
	private ArrayList<Shape> myShapes = new ArrayList<Shape>(); //list to store shapes in drawing
	
	/**
	 * Constructor for objects of type Drawing.
	 * @param initialColor the color the drawing starts as
	 */
	public Drawing(Color initialColor) {
		myColor = initialColor;
	}

	/**
	 * Draws the entire drawing on a given page.
	 * @param page the page to draw on
	 */
	public void draw(Graphics page) {
		for(int i = myShapes.size() - 1; i >= 0; i--) //draws starting with back shape
		{
			Shape s = myShapes.get(i);
			page.setColor(s.getColor()); //set drawing color to shape color
			s.drawShape(page); //draw shape on page
		}
	}

	/**
	 * Returns the frontmost shape in the drawing under a given point.
	 * @param p the given point
	 * @return the frontmost shape in the drawing under the point
	 */
	public Shape getFrontmostContainer(Point p) {
		Shape front = null;
		for(int i = myShapes.size() - 1; i >= 0; i--) //starts at back of drawing
		{
			Shape s = myShapes.get(i);
			if(s.containsPoint(p)) //is the shape under the click point?
			{
				front = s; //set new front
			}
		}
		return front; //return shape closest to front
	}
	
	/**
	 * Sets the drawing color to a new color.
	 * @param newColor the new color
	 */
	public void setColor(Color newColor)
	{
		myColor = newColor;
	}
	
	/**
	 * Returns the current drawing color.
	 * @return the current drawing color
	 */
	public Color getColor()
	{
		return myColor;
	}
	
	/**
	 * Sets the current shape to a new shape.
	 * @param newShape the new shape
	 */
	public void setShape(Shape newShape)
	{
		currShape = newShape;
	}
	
	/**
	 * Stores the current shape in the drawing.
	 */
	public void addCurrent()
	{
		myShapes.add(0, currShape); //adds current shape to front of drawing
	}
	
	/**
	 * Returns the current shape in the drawing.
	 * @return the current shape in the drawing
	 */
	public Shape getCurrent()
	{
		return currShape;
	}
	
	/**
	 * Sets the current shape to be a new shape of the same type 
	 * and color as the current shape if the current shape is stored.
	 */
	public void resetCurrent()
	{
		//Check type of current shape, then create new shape of same type/color.
		if(currShape instanceof Rect)
		{
			currShape = new Rect(getColor());
		}
		else if(currShape instanceof Ellipse)
		{
			currShape = new Ellipse(getColor());
		}
		else if(currShape instanceof Segment)
		{
			currShape = new Segment(getColor());
		}
	}
	
	/**
	 * Returns the shape at the front of the drawing.
	 * @return the shape at the front of the drawing
	 */
	public Shape getFront()
	{
		return myShapes.get(0); //get front shape
	}
	
	/**
	 * Remove a given shape from the drawing.
	 * @param s the shape to remove
	 */
	public void removeShape(Shape s)
	{
		for(int i = 0; i < myShapes.size(); i++) //search shapes
		{
			if(myShapes.get(i).equals(s))
			{
				myShapes.remove(myShapes.get(i)); //remove shape
			}
		}
	}
	
	/**
	 * Moves a given shape to the front of the drawing.
	 * @param s the shape to move to the front
	 */
	public void moveToFront(Shape s)
	{
		for(int i = 0; i < myShapes.size(); i++)
		{
			if(myShapes.get(i).equals(s)) //search shapes
			{
				Shape temp = myShapes.get(i); //save shape to move
				myShapes.remove(i);
				myShapes.add(0, temp); //put shape at front
			}
		}
	}
	
	/**
	 * Moves a given shape to the back of the drawing.
	 * @param s the shape to move to the back
	 */
	public void moveToBack(Shape s)
	{
		for(int i = 0; i < myShapes.size(); i++) //search shapes
		{
			if(myShapes.get(i).equals(s))
			{
				Shape curr = myShapes.get(i); //shape to move
				Shape switchTarget = myShapes.get(myShapes.size() - 1); //back shape
				//switch shape to move and back shape
				myShapes.set(myShapes.size() - 1, curr);
				myShapes.set(i, switchTarget);
			}
		}
	}
}