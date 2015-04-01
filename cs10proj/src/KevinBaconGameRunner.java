import java.util.*;
import java.io.*;
import net.datastructures.*;
import javax.swing.*;
/**
 * Class which runs the Kevin Bacon game, which traces a given actor to Kevin Bacon.
 * @author Jasper Bingham
 */
public class KevinBaconGameRunner
{
	public static void main(String [] args) throws IOException
	{
		
		KevinBaconReader reader = new KevinBaconReader();
		KevinBaconBFS bfs = new KevinBaconBFS(reader.getGraph());
		DirectedAdjListMap<String, String> graph = bfs.doBFS();
		
		//Continually prompts user for input
		while(true)
		{
			System.out.println("Please enter the name of the actor you wish to trace to Kevin Bacon, or type quit to exit.");
			
			//Scans what user types in
			Scanner scan = new Scanner(System.in);
			String actorName = scan.nextLine();
			
			//If user types quit, end program
			if(actorName.equalsIgnoreCase("quit"))
			{
				System.exit(0);
			}
			
			//Handle if user inputs Kevin Bacon
			if(actorName.equals("Kevin Bacon"))
			{
				System.out.println("That is Kevin Bacon! His Bacon Number is 0.");
			}
			
			//Handle if user puts in an actor that isn't connected to Kevin Bacon
			if(!graph.vertexInGraph(actorName))
			{
				System.out.println("That actor isn't connected to Kevin Bacon!");
			}
			
			
			else
			{
				int baconNumber = 0; //bacon number -- increments on each iteration
				String origActor = actorName;
				Vertex<String> vertex = graph.getVertex(actorName); //start vertex is actor inputted
				while(!vertex.equals(graph.getVertex("Kevin Bacon"))) //until traced to Kevin Bacon
				{
					Iterable<Edge<String>> edgeList = graph.incidentEdgesOut(vertex);
					for(Edge<String> e : edgeList) //start with edge going towards Kevin Bacon
					{
						//Print out first trace step
						System.out.println(vertex.toString() + " appeared in " + e.element() + " with " + graph.opposite(actorName, e).toString());
						
						//Increment Bacon number
						baconNumber++; 
						
						//Set new vertex and actor name
						vertex = graph.opposite(actorName, e);
						actorName = vertex.element();
					}
				}
				
				//Print actor's Bacon number
				System.out.println(origActor + "'s Bacon Number is " + baconNumber);
			}
		}
	}
}