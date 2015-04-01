import java.util.*;
import java.io.*;
import javax.swing.*;

/**
 * Class which represents a Huffman encoding system, which can compress and decompress a text file.
 * @author Jasper Bingham
 */
public class HuffmanEncode
{
	private BinaryTree<CharAndFreq> codeTree;
	private TreeMap<Character, String> codeTable = new TreeMap<Character, String>();
	private String pathName;
	private String compressedPathName;
	private String decompressedPathName;
	private BufferedReader regInput;
	private BufferedWriter regOutput;
	private BufferedBitReader bitInput;
	private BufferedBitWriter bitOutput;
	private static final int TXT_LENGTH = 4;
	private static final int COMPRESSED_AND_TXT_LENGTH = 15;
	private static final int LARGE_NUMBER = 100000000;
	
	/**
	 * Constructor.
	 * Prompts user for file to compress / decompress, then generates a character tree, 
	 * as well as target file names, readers, and writers. 
	 * @throws IOException
	 */
	public HuffmanEncode() throws IOException
	{
	    //get original file from user
			pathName = HuffmanEncode.getFilePath();
			
			//if the user presses cancel, exit program
			if(pathName.equals(""))
			{
				System.out.println("You have cancelled the program.");
				System.exit(0);
			}
			
		  //make compressed/decompressed file names
			compressedPathName = pathName.substring(0, pathName.length() - TXT_LENGTH) + "_compressed" + ".txt";
			decompressedPathName = compressedPathName.substring(0, compressedPathName.length() - COMPRESSED_AND_TXT_LENGTH) + "_decompressed" + ".txt";
			
			//set up compression readers and writers
			regInput = new BufferedReader(new FileReader(pathName));
			regInput.mark(LARGE_NUMBER); //mark the reader for later reset in compress method
			bitOutput = new BufferedBitWriter(compressedPathName);
			
			//generate frequency table, tree, and code table
			Map<Character, Integer> freqTable = generateFreqTable(pathName);
			codeTree = makeHuffmanTree(freqTable);
			codeTable = generateCodeTable("", makeHuffmanTree(freqTable));
	}
	
	/**
	 * Generates a frequency table for an input text file.
	 * @param pathName the path name for the input text file
	 * @return the frequency table
	 */
	public Map<Character, Integer> generateFreqTable(String pathName) throws IOException
	{
		Map<Character, Integer> myFreqTable = new TreeMap<Character, Integer>();
		try
		{
			int c = regInput.read();
			if(c == -1) //if file is empty, quit program
			{
				System.out.println("File is empty -- compression will not be useful.");
				System.exit(0);
			}
			while(c != -1) //check that reader has more characters to read
			{			
				char thisChar = (char) c; //cast to character
				if(myFreqTable.containsKey(thisChar))
				{
					myFreqTable.put(thisChar, myFreqTable.get(thisChar) + 1); //increment frequency
				}
				else
				{
					myFreqTable.put(thisChar, 1); //frequency starts at 1
				}
				c = regInput.read(); //move to next character
			}
		}
		catch(IOException i)
		{
			System.out.println(i + " exception");
		}
		return myFreqTable;
	}
		
	/**
	 * Generates a Huffman tree from a frequency table.
	 * @param freqTable the frequency table
	 * @return the Huffman tree
	 */
	public BinaryTree<CharAndFreq> makeHuffmanTree(Map<Character, Integer> freqTable)
	{

		PriorityQueue<BinaryTree<CharAndFreq>> myQueue = new PriorityQueue<BinaryTree<CharAndFreq>>(freqTable.size(), new TreeComparator());
		Set<Character> keys = freqTable.keySet();
		
	  //BOUNDARY CASE: if there is only one type of character in the file,
		//we have to make a tree where there is a dummy root and then two identical children,
		//both of which represent the one character and that character's frequency
		if(keys.size() == 1) 
		{
			for(Character c : keys)
			{
				int freq = freqTable.get(c);
			  CharAndFreq loneRoot = new CharAndFreq('\0', freq + freq);
			  CharAndFreq loneChar = new CharAndFreq(c, freqTable.get(c));
			  BinaryTree<CharAndFreq> loneTree = new BinaryTree<CharAndFreq>(loneChar);
			  BinaryTree<CharAndFreq> loneCombo = new BinaryTree<CharAndFreq>(loneRoot, loneTree, loneTree);
				myQueue.add(loneCombo);
				return myQueue.poll();
			}	
		}
		
		//otherwise go through the regular process -- create singleton trees
		for(Character c : keys)
		{
			CharAndFreq cf = new CharAndFreq(c, freqTable.get(c));
			BinaryTree<CharAndFreq> singleton = new BinaryTree<CharAndFreq>(cf);
			myQueue.add(singleton);
		}
		//tree creation
		while(myQueue.size() > 1)
		{
			//get two characters with lowest frequencies, then make a tree with those two and a dummy header
			//and add that tree to the priority queue
			int freq1 = myQueue.peek().getValue().getFreq();
			BinaryTree<CharAndFreq> tree1 = myQueue.poll();
			int freq2 = myQueue.peek().getValue().getFreq();
			BinaryTree<CharAndFreq> tree2 = myQueue.poll();
			CharAndFreq newParent = new CharAndFreq('\0', freq1 + freq2);
			BinaryTree<CharAndFreq> combo = new BinaryTree<CharAndFreq>(newParent, tree1, tree2);
			myQueue.add(combo);
		}
		//once the process is over, there is one element in the priority queue: the complete tree
		return myQueue.poll();
	}
	
	/**
	 * Generates a code table from a Huffman tree.
	 * @param pathSoFar the path taken through the tree so far - 0s mark left turns, 1s mark right turns
	 * @param focusNode the current node in the traversal - changes during recursion
	 * @return
	 */
	public TreeMap<Character, String> generateCodeTable(String pathSoFar, BinaryTree<CharAndFreq> focusNode)
	{
		//go left & right down the root subtrees recursively until leaf reached
		if(focusNode.hasLeft())
		{
			generateCodeTable(pathSoFar + "0", focusNode.getLeft());
		}
		if(focusNode.hasRight())
		{
			generateCodeTable(pathSoFar + "1", focusNode.getRight());
		}
		//pair leaf character with the path so far in code table
		if(focusNode.isLeaf())
		{
			codeTable.put(focusNode.getValue().getChar(), pathSoFar);
		}
		return codeTable;
	}
	
	/**
	 * Compresses the file.
	 * @throws IOException
	 */
	public void compress() throws IOException
	{	
		regInput.reset(); //reset the file reader after making the frequency table
		try
		{
			int c = regInput.read(); //get first character
			while(c != -1) //run until reader is done
			{
				char thisChar = (char) c;
				String s = codeTable.get(thisChar); //find correct code for char
				for(int i = 0; i < s.length(); i++) //run through code, coding corresponding bits for each 0 or 1
				{
					if(s.charAt(i) == '0')
					{
						bitOutput.writeBit(0);
					}
					else bitOutput.writeBit(1);
				}
				c = regInput.read(); //advance reader
			}
		}
		catch(IOException i)
		{
			System.out.println(i + " exception");
		}	
		finally
		{
			regInput.close();
			bitOutput.close();
		}
	}
	
	/**
	 * Decompresses the file.
	 * @throws IOException
	 */
	public void decompress() throws IOException {
		
		//Make appropriate reader/writer (only possible here because compressed file has to be made first)
		bitInput = new BufferedBitReader(compressedPathName); 
		regOutput = new BufferedWriter(new FileWriter(decompressedPathName));	
		
		
		try{
			//read through bits
			int bit = bitInput.readBit();
			while (bit != -1) {
				decompressCharacter(bit, codeTree); //use helper method to recognize and write character
				bit = bitInput.readBit(); //advance bit after character is written
			}
		}	
		catch(IOException i)
		{
			 System.out.println(i + " exception");
		}	
		finally
		{
			 bitInput.close();
			 regOutput.close();
		}
	}
	
 /**
  * Recognizes and writes a character, given a starting bit in the compressed file and the Huffman tree.	
  * @param bit the starting bit in the compressed file
  * @param tree the Huffman tree
  * @throws IOException
  */
 public void decompressCharacter(int bit, BinaryTree<CharAndFreq> tree) throws IOException
 {
	 	 //base case: we get to a leaf
	 	 if(tree.isLeaf())
		 {
			 regOutput.write(tree.getValue().getChar()); //writes the character to the decompressed file
			 return;
		 }
		 try
		 {
			 //if bit is 0 go to left subtree
			 if(bit == 0)
			 {
				 //if next left node is not a leaf, advance bit
				 if(tree.getLeft().isInner())
				 {
					 decompressCharacter(bitInput.readBit(), tree.getLeft());
				 }
				 //otherwise we do not advance the bit
				 else decompressCharacter(bit, tree.getLeft());
			 }
			 
			 //if bit is 1 go to right subtree
			 if(bit == 1)
			 {
				 //if next right node is not a leaf, advance bit
				 if(tree.getRight().isInner())
				 {
					 decompressCharacter(bitInput.readBit(), tree.getRight());
				 }
				 //otherwise we do not advance the bit 
				 else decompressCharacter(bit, tree.getRight());
			 }
		 }
		 catch(IOException i)
		 {
				System.out.println(i + " exception");
		 }		 
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
 
	/**
	 * Tester. Compresses and decompresses an input file.
	 */
	public static void main (String [] args) throws IOException
	{	
		HuffmanEncode h = new HuffmanEncode();
		h.compress();
		h.decompress();
	}
}