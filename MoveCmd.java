import java.awt.*;
/**
 * Subclass of Command that moves a shape when pressed and dragged.
 * @author Jasper Bingham
 */
public class MoveCmd extends Command
{
	private Shape selected = null;
	private Point clickPoint; 
	private Point center;
	private boolean move;
	public void executePress(Point p, Drawing dwg) {
		if(dwg.getFrontmostContainer(p) != null) //check if there is a shape under click point
		{
			selected = dwg.getFrontmostContainer(p);
			center = selected.getCenter(); //center of clicked shape
			clickPoint = p; //where the user presses
		}
  }
  public void executeDrag(Point p, Drawing dwg) {
  	if(dwg.getFrontmostContainer(p) != null)
  	{
  		int deltaX = p.x - clickPoint.x; //change in X from pressed point to current point
  		int deltaY = p.y - clickPoint.y; //change in Y from pressed point to current point
  		Point newCenter = new Point(center.x + deltaX, center.y + deltaY); //shift center by change in X/Y
  		selected.setCenter(newCenter);
  	}
  }
}