import java.io.*;
import java.net.*;

public class ClientHandler {
	boolean connectionOpen;
	Socket clientSocket;
	String host;
	int port;
	
	/**
	 * Creates a new client handler with given host and port.
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	public ClientHandler(String host, int port) throws IOException{
		connectionOpen = true;
		this.host = host;
		this.port = port;
		establishConnection(host, port);
	}
	
	/**
	 * Creates a socket with given host on given port.
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	private void establishConnection(String host, int port) throws IOException{
		clientSocket = new Socket(host,port);
	}
	
	private void changeConnectionStatus(String version, String commandWord) throws IOException{
		if(version.equals("HTTP/1.0") || commandWord.toUpperCase().equals("QUIT")){
			connectionOpen = false;
		}
		if(version.equals("HTTP/1.1")){
			establishConnection(this.host, this.port);
		}
	}
	
	/**
	 * Returns whether the connection to the host is open or closed. 
	 */
	public boolean getConnectionStatus(){
		return connectionOpen;
	}
	
	public void processCommand(String command_input) throws IOException {
		String[] commandline = command_input.split(" ");
		String command ="";
		String version ="";
		try {
			command = commandline[0] + " " + commandline[1];
			version = commandline[2];
		} catch (ArrayIndexOutOfBoundsException e) {
			if (commandline[0].toUpperCase().equals("QUIT")) {
				closeConnection();
			} 
		}
		changeConnectionStatus(version, command);	
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		if(version.equals("HTTP/1.0")){
			out.println(command + "\r\n");
			closeConnection();
		}
		
		else if(version.equals("HTTP/1.1")){
			String new_command = command + " " + version;
			out.println(new_command + "\r\n");
		}
		else{
			System.out.println("This HTTP version is not supported. Please choose HTTP/1.1 or HTTP/1.0");
			String new_request = (new BufferedReader(new InputStreamReader(System.in))).readLine();
			processCommand(new_request);
		}
		String output;
		System.out.println("REACHED THE SPOT MOFO");
		System.out.println(in.readLine());
		while(((output = in.readLine()) != null) && in.ready()){
			System.out.println(output);
		}		
	}
	private void closeConnection(){
		System.out.println("Closing connection... \r\n");
	}
}