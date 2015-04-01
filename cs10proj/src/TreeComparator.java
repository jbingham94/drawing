import java.util.*;
/**
 * Class which compares two tree nodes with letters and frequencies.
 * @author Jasper Bingham
 */
public class TreeComparator implements Comparator<BinaryTree<CharAndFreq>>
{
	/**
	 * Compares the two letters' frequencies.
	 * Returns 1 if first frequency is greater than second,
	 * 0 if the frequencies are equal, or -1 if the first 
	 * frequency is less than the second.
	 * @return the number indicating whether the first letter's 
	 * frequency is greater than, equal to, or less than the 
	 * second letter's frequency
	 */
	public int compare(BinaryTree<CharAndFreq> lf1, BinaryTree<CharAndFreq> lf2) {
		if(lf1.getValue().getFreq() > lf2.getValue().getFreq())
		{
			return 1;
		}
		else if(lf1.getValue().getFreq() == lf2.getValue().getFreq())
		{
			return 0;
		}
		else return -1;
	}
}