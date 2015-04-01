import net.datastructures.*;
import java.util.*;

/**
 * Class which implements a breadth-first search for the Kevin Bacon game.
 * @author Jasper Bingham
 */
public class KevinBaconBFS
{
	private AdjacencyListGraphMap<String, String> myUndirGraph;
	private static String kevin = "Kevin Bacon"; //the root -- Kevin Bacon
	
	/**
	 * Constructor
	 * @param undirGraph the undirected graph of actors/movies
	 */
	public KevinBaconBFS(AdjacencyListGraphMap<String, String> undirGraph)
	{
		myUndirGraph = undirGraph;
	}
	
	/**
	 * Runs the breadth-first search.
	 * @return the shortest-path tree
	 */
	public DirectedAdjListMap<String, String> doBFS()
	{
		DirectedAdjListMap<String, String> shortestPathTree = new DirectedAdjListMap<String, String>();
		LinkedListQueue<Vertex<String>> queue = new LinkedListQueue<Vertex<String>>();
		
		//Insert root into queue and into directed graph
		queue.enqueue(myUndirGraph.getVertex(kevin));
		shortestPathTree.insertVertex(kevin);
		
		//Until queue is empty
		while(!queue.isEmpty())
		{
			//Get next vertex to process
			Vertex<String> nextVertex = queue.dequeue();
			
			Iterable<Edge<String>> edgesOfNext = myUndirGraph.incidentEdges(nextVertex);
			
			//for incident edges of the vertex
			for(Edge<String> e : edgesOfNext)
			{
				Vertex<String> otherEnd = myUndirGraph.opposite(nextVertex, e);
				
				//check if shortest path tree has the vertex
				if(!shortestPathTree.vertexInGraph(otherEnd.element()))
				{
					shortestPathTree.insertVertex(otherEnd.element());
					shortestPathTree.insertDirectedEdge(otherEnd.element(), nextVertex.element(), e.element());
					queue.enqueue(otherEnd);
				}
			}
		}
		return shortestPathTree;
	}
	
	
	/**
	 * BFS Tester.
	 */
	public static void main(String[] args)
	{
		AdjacencyListGraphMap<String, String> startGraph = new AdjacencyListGraphMap<String, String>();
		startGraph.insertVertex("Kevin Bacon");
		startGraph.insertVertex("actor1");
		startGraph.insertVertex("actor2");
		startGraph.insertVertex("actor3");
		startGraph.insertVertex("actor4");
		startGraph.insertVertex("actor5");
		startGraph.insertVertex("actor6");
		startGraph.insertEdge("Kevin Bacon", "actor1", "movie1");
		startGraph.insertEdge("Kevin Bacon", "actor2", "movie1");
		startGraph.insertEdge("actor1", "actor2", "movie1");
		startGraph.insertEdge("actor1", "actor3", "movie2");
		startGraph.insertEdge("actor3", "actor2", "movie3");
		startGraph.insertEdge("actor3", "actor4", "movie4");
		startGraph.insertEdge("actor5", "actor6", "movie5");
		System.out.println(startGraph);
		KevinBaconBFS bfs = new KevinBaconBFS(startGraph);
		System.out.println(bfs.doBFS());
	}
}