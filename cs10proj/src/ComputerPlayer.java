/**
 * Holds information about a player in the chips game.
 * Based on earlier versions written by Scot Drysdale and THC.
 * 
 * @author Scot Drysdale
 */
public class ComputerPlayer extends Player {

	public static String COMPUTER_NAME = "Computer";
	
	/**
	 *  Constructs a computer player
	 */
	public ComputerPlayer(String name) {
		super(name);
	}
	
	/**
	 * Constructs a computer player with the given number of chips
	 * @param chips number of chips that this player has
	 */
	public ComputerPlayer(String name, int chips) {
		super(name, chips);
	}
	

	/**
	 * Find the best move, using game tree search
	 */
	public int getMove(Game state) {
		int move = pickBestMove(state.makeCopy());  
		
		// If can't find a winning move make a random move.
		if (move == 0)
	 	 return 1 + (int) (Math.random()*state.getMaxMove());
		else
			return move;
	}

  /**
   * Returns a winning move for the current player if can find one, else 0 
   * if all moves loose
   * @param state the state of this Chips game
   * @return a winning move, or 0 if none exists
   */
  private static int pickBestMove(Game state)
  {
    // Try moves from maximum first looking for a winning move
    for (int move = state.getMaxMove(); move > 0; move--) {
      // Don't want to change current state, so make copy
      Game newState = state.makeCopy();
          
      newState.makeMove(move);  // Make the move on the copy made
    
      if (newState.isOver())  {
        if (newState.getWinner() == newState.getOtherPlayer())
          return move;    // Winner was player who just made move
      }
      else if (pickBestMove(newState) == 0)  // No winning move for other player?
        return move;            // If other player can't win, then I can
    }
    
    return 0;                   // No winning move found, so current player loses
  }
  
  /**
   * Makes a copy of the current player
   * Could have used clone(), had we studied it.
   */
	public Player makeCopy() {
		return new ComputerPlayer(getName(), getChips());
	}  
}
