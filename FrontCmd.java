import java.awt.Point;

/**
 * Subclass of Command that moves a shape to the front of the drawing when clicked.
 * @author Jasper Bingham
 */
public class FrontCmd extends Command
{
	Shape frontTarget;
	public void executeClick(Point p, Drawing dwg) {
		if(dwg.getFrontmostContainer(p) != null) //is there a shape to move?
		{
			frontTarget = dwg.getFrontmostContainer(p);
			dwg.moveToFront(frontTarget); //moves clicked shape to front
		}
	}
}