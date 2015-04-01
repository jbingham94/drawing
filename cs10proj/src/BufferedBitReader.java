import java.io.*;

/**
 * Reads bits from a file, one at a time.  
 * Assumes that the last byte of the file contains the number of
 * valid bits in the previous byte.
 * 
 * @author Scot Drysdale
 */
public class BufferedBitReader {
	// Note that we need to look ahead 3 bytes, because when the
	// third byte is -1 (EOF indicator) then the second byte is a count
	// of the number of valid bits in the first byte.
	
	int current;    // Current byte being returned, bit by bit
	int next;       // Next byte to be returned (could be a count)
	int afterNext;  // Byte two after the current byte
	int bitMask;    // Shows which bit to return
	
	BufferedInputStream input;
	
	/**
	 * Constructor
	 * @param pathName the path name of the file to open
	 * @throws IOException
	 */
	public BufferedBitReader(String pathName) throws IOException {
		input = new BufferedInputStream(new FileInputStream(pathName));
		
		current = input.read();
		if(current == -1)
			throw new EOFException("File did not have two bytes");
		
		next = input.read();
		if(next == -1) 
			throw new EOFException("File did not have two bytes");	
		
		afterNext = input.read();
		bitMask = 128;   // a 1 in leftmost bit position
	}
	
	
	/**
	 * Reads a bit and returns it as a 0 or a 1.
	 * Returns -1 when all bits have been returned
	 * 
	 * @return the bit read (0 or 1)
	 * @throws IOException
	 */
	public int readBit() throws IOException {
		int returnBit;   // Hold the bit to return
		
		if(afterNext == -1)  // Are we emptying the last byte?
			
			// When afterNext == -1, next is the count of bits remaining.
			
			if(next == 0)   // No more bits in the final byte to return
		  	return -1;    // No more bits to return
		  else {
		  	if((bitMask & current) == 0)
		  		returnBit = 0;
		  	else 
		  		returnBit = 1;
		  	
        next--;          // One fewer bit to return
        bitMask = bitMask >> 1;    // Shift to mask next bit
        return returnBit;
		  }
		else {
	  	if((bitMask & current) == 0)
	  		returnBit = 0;
	  	else 
	  		returnBit = 1;
	  	
      bitMask = bitMask >> 1;    // Shift to mask next bit
      
      if(bitMask == 0)  {        // Finished returning this byte?
      	bitMask = 128;           // Leftmost bit next
      	current = next;
      	next = afterNext;
      	afterNext = input.read();
      }
      return returnBit;
		}
	}
	
	/**
	 * Close this bitReader.
	 * @throws IOException
	 */
	public void close() throws IOException {
		input.close();
	}
	
	

}
