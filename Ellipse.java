import java.awt.*;

/**
 * Ellipse.java
 * Class for an ellipse.
 * 
 * Written by THC for CS 5 Lab Assignment 3.
 *
 * @author Thomas H. Cormen 
 * @author Jasper Bingham
 */
public class Ellipse extends Shape {

	/**
	 * Constructor
	 * @param color the color of the new ellipse
	 */
	public Ellipse(Color color)
	{
		super(color);
	}
	
  //Helper method that returns whether Point p is in an Ellipse with the given
  // top left corner and size.
  private static boolean pointInEllipse(Point p, int left, int top, int width,
      int height) {
    double a = width / 2.0; // half of the width
    double b = height / 2.0; // half of the height
    double centerx = left + a; // x-coord of the center
    double centery = top + b; // y-coord of the center
    double x = p.x - centerx; // horizontal distance between p and center
    double y = p.y - centery; // vertical distance between p and center

    // Now we just apply the standard geometry formula.
    // (See CRC, 29th edition, p. 178.)
    return Math.pow(x / a, 2) + Math.pow(y / b, 2) <= 1;
  }

	/**
	 * Draws the ellipse on a given page.
	 * @param page the page where the ellipse is drawn
	 */
	public void drawShape(Graphics page) {
	  //draws shape using top left coordinates as the starting point
	  //and x and y distances between the origin and drag point as width 
		//and height, respectively
		page.fillOval(getTopLeft().x, getTopLeft().y, Math.abs(getOrigin().x - getDragPoint().x), Math.abs(getOrigin().y - getDragPoint().y));
	}

	/**
	 * Tests if the ellipse contains a given point.
	 * @param p the given point
	 */
	public boolean containsPoint(Point p) {
		//use helper method
		if(pointInEllipse(p, getTopLeft().x, getTopLeft().y, Math.abs(getOrigin().x - getDragPoint().x), Math.abs(getOrigin().y - getDragPoint().y)))
		{
			return true;
		}
		return false;
	}

  /**
   * Moves the ellipse by a certain horizontal and vertical amount.
   * @param deltaX the horizontal distance to move
   * @param deltaY the vertical distance to move
   */
	public void move(int deltaX, int deltaY) {
		int newX = getCenter().x + deltaX; //new X value
		int newY = getCenter().y + deltaY; //new Y value
		Point newCenter = new Point(newX, newY);
		setCenter(newCenter); //set center to point with new X/Y values
	}

	/**
	 * Gets the center of the ellipse.
	 * @return the center of the ellipse
	 */
	public Point getCenter() {
		//average x and y values of top left and bottom right corners to get center
		return new Point((getTopLeft().x + getBottomRight().x) / 2, (getBottomRight().y + getTopLeft().y) / 2 );
	}
}
