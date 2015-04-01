import java.awt.*;

/**
 * Subclass of Command that deletes a shape from the drawing when clicked.
 * @author Jasper Bingham
 */
public class DeleteCmd extends Command
{
	private Shape deleteTarget;
	public void executeClick(Point p, Drawing dwg) {
		if(dwg.getFrontmostContainer(p) != null) //is there a shape to delete?
		{
			deleteTarget = dwg.getFrontmostContainer(p);
			dwg.removeShape(deleteTarget); //removes clicked shape
		}
	}
}