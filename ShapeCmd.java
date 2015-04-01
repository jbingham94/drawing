import java.awt.*;

/**
 * Subclass of Command that creates a shape when the user clicks and drags.
 * @author Jasper Bingham
 */
public class ShapeCmd extends Command
{
  public void executePress(Point p, Drawing dwg) {
  	dwg.addCurrent();
  	dwg.resetCurrent();
  	dwg.getFront().setOrigin(p);
  	dwg.getFront().setDragPoint(p);
  }
  public void executeDrag(Point p, Drawing dwg) {
  	dwg.getFront().setDragPoint(p);
  }
}