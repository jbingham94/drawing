import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Class for a rectangle.
 * @author Jasper Bingham
 */
public class Rect extends Shape
{
	
	/**
	 * Constructor
	 * @param color the color of the new rectangle
	 */
	public Rect(Color color) {
		super(color);
	}

	/**
	 * Draws the rectangle on a given page.
	 * @param page the page where the rectangle is drawn
	 */
	public void drawShape(Graphics page) {
		//draws shape using top left coordinates as the starting point
		//and x and y distances between the origin and drag point as width 
		//and height, respectively
		page.fillRect(getTopLeft().x, getTopLeft().y, Math.abs(getOrigin().x - getDragPoint().x), Math.abs(getOrigin().y - getDragPoint().y));
	}

	/**
	 * Tests if the rectangle contains a given point.
	 * @param p the given point
	 */
	public boolean containsPoint(Point p) {
		//if the point's x and y values are between the x and y values
		//of the top left and bottom right corners, the point is in the shape
		if(p.x >= getTopLeft().x && p.x <= getBottomRight().x && p.y >= getTopLeft().y && p.y <= getBottomRight().y)
		{
			return true;
		}
		return false;
	}

	/**
   * Moves the rectangle by a certain horizontal and vertical amount.
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