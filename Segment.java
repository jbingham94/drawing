import java.awt.*;

/**
 * Segment.java
 * Class for a line segment.
 * 
 * Written by THC for CS 5 Lab Assignment 3.
 *
 * @author Thomas H. Cormen
 * @author Jasper Bingham
 */
public class Segment extends Shape {
  private static final int TOLERANCE = 3; //tolerance is three pixels, as suggested
  
  /**
	* Constructor
	* @param color the color of the new segment
	*/
  public Segment(Color color)
  {
  	super(color);
  }
  
  // Helper method that returns true if Point p is within a tolerance of a
  // given bounding box. Here, the bounding box is given by the coordinates of
  // its left, top, right, and bottom.
	private static boolean almostContainsPoint(Point p, int left, int top,
      int right, int bottom, double tolerance) {
    return p.x >= left - tolerance && p.y >= top - tolerance
        && p.x <= right + tolerance && p.y <= bottom + tolerance;
  }

  // Helper method that returns the distance from Point p to the line
  // containing a line segment whose endpoints are given.
  private static double distanceToPoint(Point p, int x1, int y1, int x2,
      int y2) {
    if (x1 == x2) // vertical segment?
      return (double) (Math.abs(p.x - x1)); // yes, use horizontal distance
    else if (y1 == y2) // horizontal segment?
      return (double) (Math.abs(p.y - y1)); // yes, use vertical distance
    else {
      // Here, we know that the segment is neither vertical nor
      // horizontal.
      // Compute m, the slope of the line containing the segment.
      double m = ((double) (y1 - y2)) / ((double) (x1 - x2));

      // Compute mperp, the slope of the line perpendicular to the
      // segment.
      double mperp = -1.0 / m;

      // Compute the (x, y) intersection of the line containing the
      // segment and the line that is perpendicular to the segment and that
      // contains Point p.
      double x = (((double) y1) - ((double) p.y) - (m * x1) + (mperp * p.x))
          / (mperp - m);
      double y = m * (x - x1) + y1;

      // Return the distance between Point p and (x, y).
      return Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
    }
  }

  /**
   * Draws the line on a given page.
   * @param page the given page
   */
	public void drawShape(Graphics page) {
		//draws line between origin and drag point
		page.drawLine(getOrigin().x, getOrigin().y, getDragPoint().x, getDragPoint().y);
	}

	/**
	 * Tests if the line contains the given point.
	 * @param p the given point
	 */
	public boolean containsPoint(Point p) {
		//test using both helper methods
		if(Segment.almostContainsPoint(p, getTopLeft().x, getTopLeft().y, getBottomRight().x, getBottomRight().y, TOLERANCE)
				&& Segment.distanceToPoint(p, getOrigin().x, getOrigin().y, getDragPoint().x, getDragPoint().y) <= TOLERANCE)
		{
			return true;
		}
		return false;
	}

	/**
	 * Moves the line by a certain horizontal and vertical amount.
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
	 * Gets the center of the line.
	 * @return the center of the line
	 */
	public Point getCenter() {
	  //average x and y values of top left and bottom right corners to get center
		return new Point((getTopLeft().x + getBottomRight().x) / 2, (getBottomRight().y + getTopLeft().y) / 2 );
	}
}
