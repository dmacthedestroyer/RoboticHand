package logicalComponents;
import java.io.IOException;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.comm.SerialPortEventListener;

/**
 *	Port class
 *		This class handles opening and closing the port, as well as reading and sending data.  Any program that wishes to use this program with event-driven
 *	capabilities must implement the SerialPortEventListener interface described in the javax.comm package.
 */
public class Port{
	/**
	 * The address of the serial port on this linux machine
	 */
	private final String defaultPortAddress = "/dev/ttyS0";	//the address of the serial port on this computer
	
	/**
	 * The baud rate we are using to communicate through the serial port
	 */
	private final int BAUDRATE = 9600;	//the default baud rate for this serial port
	
	/**
	 * The amount of data bits used
	 */
	private final int DATABITS = SerialPort.DATABITS_8;	//the number of data bits
	
	/**
	 * The amount of stop bits used
	 */
	private final int STOPBITS = SerialPort.STOPBITS_1;	// the number of stop bits
	
	/**
	 * The parity of this connection
	 */
	private final int PARITY = SerialPort.PARITY_NONE;	//the parity
	
	/**
	 * An instance of the javax.comm.SerialPort class, which is used to read and write to the serial port
	 */
	private SerialPort port;
	
	/**
	 * Creates a Port object that is ready for reading and writing via the serial port.  In order to receive events, the SerialPortEventListener interface must be implemented and registered to this class.
	 *
	 */
	public Port(){
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		while(portList.hasMoreElements()){
			CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
			if(portId.getPortType() == CommPortIdentifier.PORT_SERIAL && portId.getName().equals(defaultPortAddress))
				try{
					port = (SerialPort) portId.open("Hand of Doom!!", 2000);	//open the serial port
					
					/**		testing with the buffer size */
					//port.setInputBufferSize(1);
					//port.setOutputBufferSize(1);
					System.out.println("input buffer: " + port.getInputBufferSize() + "\toutput buffer: " + port.getOutputBufferSize());
					/***/
					
					try{//this method contains a bug which inteferes with the kernel, so run it once, catch the exception and run it again
						port.setSerialPortParams(BAUDRATE, DATABITS, STOPBITS, PARITY);
					}catch(Exception ioe){port.setSerialPortParams(BAUDRATE, DATABITS, STOPBITS, PARITY);}
					
					port.notifyOnDataAvailable(true);//tell the serial port to listen for events that indicate that there is data to be read from the buffer
				}catch(Exception e){
					e.printStackTrace();
//					JOptionPane.showMessageDialog(null, "failed to load serial port");
				}
		}
	}
	
	/**
	 * Writes the value to the serial port.  Is untested for values greater than 255.
	 * @param value the value to be written to the serial port
	 * @return	the value to be written to the serial port, or -1 if the send failed
	 */
	public int write(int value){
		try{
			port.getOutputStream().write((byte)value);
			return value;
		}catch(IOException e){
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Writes the array of bytes to the serial port.
	 * @param value	the values to be written to the serial port
	 * @return	the values, or null if the send failed
	 */
	public byte[] write(byte[] value){
		try{
			port.getOutputStream().write(value);
			return value;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Reads in all data in the input buffer of the serial port.
	 * @return an array with each item being a value in the buffer, or an empty array if the read fails
	 */
	public int[] read(){
		int[] data;
		try{
			byte[] inputBuffer = new byte[port.getInputBufferSize()];
			int inputSize = port.getInputStream().read(inputBuffer);
			
			data = new int[inputSize];
			for(int i=0; i<inputSize; i++)
				data[i] = inputBuffer[i] < 0? inputBuffer[i]+256: inputBuffer[i];
		}catch(IOException ioe){
			ioe.printStackTrace();
			data = new int[0];
		}
		return data;
	}
	
	/**
	 * Adds the given SerialPortEventListener to this Port.  Only one listener is allowed at a time, so if there is already a listener assigned, then nothing happens.
	 * @param listener the javax.comm.SerialPortEventListener which is to be added to this Port
	 */
	public void addEventListener(SerialPortEventListener listener){
		try{
			port.addEventListener(listener);
		}catch(TooManyListenersException blah){
			blah.printStackTrace();
		}
	}
	
	/**
	 * Removes the {@link SerialPortEventListener} from this Port.
	 *
	 */
	public void removeEventListener(){
		port.removeEventListener();
	}
	
	/**
	 * Closes the serial port.
	 *
	 */
	public void close(){
		port.removeEventListener();
		port.close();
	}
}
