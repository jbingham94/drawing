import net.datastructures.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
/**
 * Class which reads necessary files for the Kevin Bacon game.
 * @author Jasper Bingham
 */
public class KevinBaconReader
{
	
	private HashMap<String, String> actorMap; 
	private HashMap<String, String> movieMap;
	private AdjacencyListGraphMap<String, String> finalGraph;
	
	/**
	 * Constructor which prompts user for necessary files, 
	 * maps actor and movie IDs to actor and movie names,
	 * and creates the undirected graph.
	 * @throws IOException
	 */
	public KevinBaconReader() throws IOException
	{
	  //map for IDs/names
		actorMap = new HashMap<String, String>(); 
		movieMap = new HashMap<String, String>();
		
		//undirected graph
		finalGraph = new AdjacencyListGraphMap<String, String>();
		
		//file paths
		String actorsPath;
		String moviesPath;
		String moviesActorsPath;
		
		//get user to choose appropriate files
		System.out.println("Please choose your actors file.");
		actorsPath = KevinBaconReader.getFilePath();
		
		System.out.println("Please choose your movies file.");
		moviesPath = KevinBaconReader.getFilePath();
		
		System.out.println("Please choose your movie-actor pairs file.");
		moviesActorsPath = KevinBaconReader.getFilePath();
		
		//set up readers
		BufferedReader actorReader = new BufferedReader(new FileReader(actorsPath));
		BufferedReader moviesReader = new BufferedReader(new FileReader(moviesPath));
		BufferedReader moviesActorsReader = new BufferedReader(new FileReader(moviesActorsPath));
		
		try
		{
			while(actorReader.ready())
			{
				String line = actorReader.readLine();
				String actorID = ""; 
				String actorName = "";
				
				//find '|' char, then split
				for(int i = 0; i < line.length(); i++)
				{
					if(line.charAt(i) == '|')
					{
						actorID = line.substring(0, i);
						actorName = line.substring(i + 1, line.length());
					}
				}
				
			  //map name to ID
				mapActor(actorID, actorName);
			}
			
			while(moviesReader.ready())
			{
				String line = moviesReader.readLine();
				String movieID = "";
				String movieName = "";
				
			  //find '|' char, then split
				for(int i = 0; i < line.length(); i++)
				{
					if(line.charAt(i) == '|')
					{
						movieID = line.substring(0, i);
						movieName = line.substring(i + 1, line.length());
					}
				}
				
				//map name to ID
				mapMovie(movieID, movieName);
			}
			
			String currMovie = "";
			String startActor = "";
			boolean sameMovie = false; //used to track which edge to make
			
			while(moviesActorsReader.ready())
			{
				String line = moviesActorsReader.readLine();
				String movieID = ""; 
				String actorID = "";
				
			  //find '|' char, then split
				for(int i = 0; i < line.length(); i++)
				{
					if(line.charAt(i) == '|')
					{
						movieID = line.substring(0, i);
						actorID = line.substring(i + 1, line.length());
					}
				}
				
				//go down through all actors in a movie, make edges with first actor in the movie
				if(movieID.equals(currMovie))
				{
					sameMovie = true;
					finalGraph.insertEdge(actorMap.get(startActor), actorMap.get(actorID), movieMap.get(currMovie));
				}
				
				//start new movie with first actor
				else 
				{
					sameMovie = false;
					currMovie = movieID;
					startActor = actorID;
				}
			}
		}
		
		
		
		//handle exception
		catch(IOException i)
		{
			System.out.println(i + " happened!");
		}
		
		//close readers
		finally
		{
			actorReader.close();
			moviesReader.close();
			moviesActorsReader.close();
		}
	}
	
	/**
	 * Maps an actor name to an actor ID
	 * @param actorID the actor ID
	 * @param actorName the actor name
	 */
	public void mapActor(String actorID, String actorName)
	{
		actorMap.put(actorID, actorName);
		finalGraph.insertVertex(actorName);
	}
	
	/**
	 * Maps a movie name to a movie ID
	 * @param movieID the movie ID
	 * @param movieName the movie name
	 */
	public void mapMovie(String movieID, String movieName)
	{
		movieMap.put(movieID, movieName);
	}

	
	/**
	 * Helper method
	 * @return the undirected graph produced in constructor
	 */
	public AdjacencyListGraphMap<String, String> getGraph()
	{
		return finalGraph;
	}
	
	/**
   * Puts up a fileChooser and gets path name for file to be opened.
   * Returns an empty string if the user clicks "cancel".
   * @return path name of the file chosen	
   */
	public static String getFilePath() {
	   //Create a file chooser
	   JFileChooser fc = new JFileChooser();
	    
	   int returnVal = fc.showOpenDialog(null);
	   if(returnVal == JFileChooser.APPROVE_OPTION)  {
	     File file = fc.getSelectedFile();
	     String pathName = file.getAbsolutePath();
	     return pathName;
	   }
	   else
	     return "";
	  }
}