import java.util.Scanner;

/**
 * Class to hold player information on a human player in Chips game.
 * 
 * Based on earlier versions written by Scot Drysdale and THC.
 * 
 * @author Scot Drysdale
 */ 
public class HumanPlayer extends Player
{
	/**
	 * Constructs a human player object with the given name
	 * @param name the name of the player
	 */
	public HumanPlayer(String name)
	{
		super(name);
	}
	
	/**
	 * Constructs a human player with the given name and number of chips
	 * @param name the name of the player
	 * @param chips the current number of chips in the pile
	 */
	public HumanPlayer(String name, int chips)
	{
		super(name, chips);
	}
	
	
	/**
	 * Gets the number of chips the player wants to take
	 * @param game the state of the game
	 * @return the number of chips chosen
	 */
	public int getMove(Game game)
	{
		int maxMove = game.getMaxMove();  // Maximum number of chips can take
		
		System.out.print("You may take any number of chips from 1 to "
            + maxMove + ".  How many will you take? ");
	 
		return inputNumInRange(1, maxMove);  // Get legal move
	}
	
	/**
	 * Input a number in the interval low to high, re-prompting if bad.
	 * @param low minimum acceptable number
	 * @param high maximum acceptable number
	 * @return the number input
	 */
	// Assumes that prompt has been given already   
	public static int inputNumInRange(int low, int high)
	{
	  	int choice;                  // The player's guess
	  	Scanner input = new Scanner(System.in);
	  
	    choice = input.nextInt();
	  
	    // Did we get a legal choice?
	    while (choice < low || choice > high)
	    {  
	      // And give player another shot.
	      System.out.println("I SAID to enter an integer between " 
	      					 + low + " and " + high + ".");
	      System.out.print("Enter your choice: ");
	      choice = input.nextInt();
	    }
	      
	    // At this point, we have a legal choice
	    return choice;
	}

  /**
   * Makes a copy of the current player
   * Could have used clone(), had we studied it.
   */
	public Player makeCopy() {
		return new HumanPlayer(getName(), getChips());
	} 

}
