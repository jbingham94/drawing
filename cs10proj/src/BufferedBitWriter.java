import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Writes bits to a file.  Accumulates bits until gets a byte, 
 * then writes it.  On closing writes an additional byte holding
 * the number of valid bits in the final byte written.
 * 
 * @author Scot Drysdale
 */
public class BufferedBitWriter {
  private byte currentByte;     // The byte that is being filled
  private byte numBitsWritten;  // Number of bits written to the current byte
  private BufferedOutputStream output; // The output byte stream
  
  /**
   * Constructor
   * @param pathName the path name of the file to be written
   * @throws FileNotFoundException
   */
  public BufferedBitWriter(String pathName) throws FileNotFoundException {
  	currentByte = 0;
  	numBitsWritten = 0;
  	output = new BufferedOutputStream(new FileOutputStream(pathName));
  }
  
  /**
   * writes a bit to the file (virtually)
   * @param bit the bit to be written, stored in an int (0 or 1)
	 * @throws IOException
   */
  public void writeBit(int bit) throws IOException {
  	if(bit < 0 || bit > 1)
  		throw new IllegalArgumentException("Argument to writeBit: bit = " + bit);
  	
  	numBitsWritten++;
  	currentByte |= bit << (8 - numBitsWritten);
  	if(numBitsWritten == 8) {  // Have we got a full byte?
  		output.write(currentByte);
  		numBitsWritten = 0;
  		currentByte = 0;
  	}
  }
  
  /**
   * Closes this bitstream.  Writes any partial byte, followed by 
   * the number of valid bits in the final byte.
   * The file will always have at least 2 bytes.  An file representing
   * no bits will have two zero bytes.
   * If this is not called the file will not be correctly read by 
   *   a BufferedBitReader
   *   
   * @throws IOException
   */
  public void close() throws IOException {
  	output.write(currentByte);
  	output.write(numBitsWritten);

  	output.close();
  }
}
