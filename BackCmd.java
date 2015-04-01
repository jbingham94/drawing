import java.awt.Point;

/**
 * Subclass of Command that moves a shape to the back of the drawing when clicked.
 * @author Jasper Bingham
 */

public class BackCmd extends Command
{
	Shape backTarget;
	public void executeClick(Point p, Drawing dwg) {
		if(dwg.getFrontmostContainer(p) != null) //is there a shape to move?
		{
			backTarget = dwg.getFrontmostContainer(p);
			dwg.moveToBack(backTarget); //moves clicked shape to back
		}
	}
}