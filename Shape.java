import java.awt.*;

/**
 * Shape.java
 * Abstract class for geometric shapes.
 * Provides three non-abstract methods: setColor, draw, and setCenter.
 * 
 * Written by THC for CS 5 Lab Assignment 3.
 *
 * @author Thomas H. Cormen
 * @author Jasper Bingham
 */
public abstract class Shape {
  private Color color; // Shape's color
  private Point origin; // origin of shape's creation
  private Point dragPoint; // point to which shape is dragged out
  
  public abstract void drawShape(Graphics page); // draw the Shape
  public abstract boolean containsPoint(Point p); // does the Shape contain Point p?
  public abstract void move(int deltaX, int deltaY); // move the Shape
  public abstract Point getCenter(); // return the Shape's center
  
  /**
   * Create a Shape, setting its color. 
   * @param c the color you wish the shape to initially have
   */
  public Shape(Color c) {
    color = c;
  }

  /**
   * Set the Shape's color.
   * @param newColor the new color of the shape
   */
  public void setColor(Color newColor) {
    color = newColor;
  }
  
  /**
   * Return the Shape's color.
   * @return the Shape's color
   */
  public Color getColor()
  {
  	return color;
  }
  
  /**
   * Draw the Shape.
   * 
   * @param page the page you wish to draw the shape on
   */
  public void draw(Graphics page) {
    Color savedColor = page.getColor(); //save page's original color
    page.setColor(color); //set page color to Shape's color
    drawShape(page); //draw Shape in correct color
    page.setColor(savedColor); //reset page's color
  }

  /**
   * Move the Shape to be a given center.
   * 
   * @param newCenter the new center of the shape
   */
  public void setCenter(Point newCenter) {
    Point oldCenter = getCenter(); //Shape's original center
    int deltaX = newCenter.x - oldCenter.x; //horizontal movement of center
    int deltaY = newCenter.y - oldCenter.y; //vertical movement of center
    //move origin by correct horizontal distance
    Point newOrigin = new Point(getOrigin().x + deltaX, getOrigin().y + deltaY);
    setOrigin(newOrigin);
    //move drag point by correct horizontal distance
    Point newDragPoint = new Point(getDragPoint().x + deltaX, getDragPoint().y + deltaY); 
    setDragPoint(newDragPoint);
  }
  
  /**
   * Set origin to new point.
   * @param orig the new point
   */
  public void setOrigin(Point orig)
  {
  	origin = orig;
  }
  
  /** 
   * Set drag point to new point
   * @param drag the new point
   */
  public void setDragPoint(Point drag)
  {
  	dragPoint = drag;
  }
  
  /**
   * Return the shape's origin.
   * @return the shape's origin
   */
  public Point getOrigin()
  {
  	return origin;
  }
  
  /**
   * Return the shape's drag point.
   * @return the shape's drag point
   */
  public Point getDragPoint()
  {
  	return dragPoint;
  }
  
  /**
   * Returns the top left corner of the shape
   * @return the top left corner of the shape
   */
  public Point getTopLeft()
  {
  	//top left corner will have lowest x and y values in shape
  	int lowX = 0;
  	int lowY = 0;
  	if(getOrigin().x < getDragPoint().x) //is origin left of drag point?
  	{
  		lowX = getOrigin().x; //if so, set top left x value to origin's x value
  	}
  	else lowX = getDragPoint().x; //otherwise set top left x value to drag point's x value
  	if(getOrigin().y < getDragPoint().y) //is origin above the drag point?
  	{
  		lowY = getOrigin().y; //if so, set top left y value to origin's y value
  	}
  	else lowY = getDragPoint().y; //otherwise set top left y value to drag point's y value
  	return new Point(lowX, lowY);
  }
  
  public Point getBottomRight()
  {
    //bottom right corner will have highest x and y values in shape
  	int highX = 0;
  	int highY = 0;
  	if(getOrigin().x > getDragPoint().x) //is origin right of drag point?
  	{
  		highX = getOrigin().x; //if so, set bottom right x value to origin's x value
  	}
  	else highX = getDragPoint().x; //otherwise set bottom right x value to drag point's x value
  	if(getOrigin().y > getDragPoint().y) //is origin below the drag point?
  	{
  		highY = getOrigin().y; //if so, set bottom right y value to origin's y value
  	}
  	else highY = getDragPoint().y; //otherwise set bottom right y value to drag point's y value
  	return new Point(highX, highY);
  }
}