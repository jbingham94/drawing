/**
 * Class which stores a character and a frequency.
 * @author Jasper Bingham
 */
public class CharAndFreq
{
	private char myChar;
	private int myFreq;
	/**
	 * Constructor.
	 * @param thisChar the character
	 * @param freq the letter's frequency
	 */
	public CharAndFreq(char thisChar, int freq)
	{
		myChar = thisChar;
		myFreq = freq;
	}
	/**
	 * Returns the character.
	 * @return the character
	 */
	public char getChar()
	{
		return myChar;
	}
	
	/**
	 * Returns the frequency of the character.
	 * @return the frequency of the character
	 */
	public int getFreq()
	{
		return myFreq;
	}
	
	/**
	 * Prints out CharAndFreq's that have non-empty characters in the format character -> frequency
	 * @return the String representation of a CharAndFreq with a non-empty character
	 */
	public String toString()
	{
		//if character is empty, only print frequency
		if(myChar == '\0')
		{
			return "" + myFreq;
		}
		return myChar + "->" + myFreq;
	}
}