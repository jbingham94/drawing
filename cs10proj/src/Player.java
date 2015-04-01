/**
 * Defines a player for the computer vs human version of Chips
 * 
 * @author Scot Drysdale
 */
public abstract class Player {
	
	private String myName;		// Player name
	private int myChips;		// Number of chips that player has taken

	/**
	 * Constructs a player object with the given name
	 * @param name the player's name
	 */
	public Player(String name)
	{
		myName = name;
		myChips = 0;
	}
	
  /**
   * Constructs a player object with the given name and number of chips
   * @param name the player's name
   * @param chips the number of chips this player has taken
   */
	public Player(String name, int chips)
	{
		myName = name;
		myChips = chips;
	}
	
	// returns the player's name
	public String getName()
	{
		return myName;
	}
	
	/**
	 * How many chips has this player taken?
	 * @return the number of chips this player has taken
	 */
	public int getChips()
	{
		return myChips;
	}
	
	/**
	 * add newChips to current pile
	 * @param newChips number of chips taken in a move
	 */
	public void addChips(int newChips)
	{
		myChips += newChips;
	}
	
	
  /** 
   * Makes a copy of the current player
   * Could have used clone(), had we studied it.
   * 
   * @return a copy of the current player
   */
	public abstract Player makeCopy(); 
	
	/**
	 * Gets the number of chips the player wants to take
	 * @param game the state of the game
	 * @return the number of chips the player choses to take
	 */
	public abstract int getMove(Game game);
	
}
