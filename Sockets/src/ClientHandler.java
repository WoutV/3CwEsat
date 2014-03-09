import java.net.*;
import java.io.*;

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
	
	private void changeConnectionStatus(String version, String command) throws IOException{
		if(version.equals("HTTP/1.0") || command.toUpperCase().equals("QUIT")){
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
	
	/**
	 * Processes a textual command given by the client.
	 * @param command_input
	 * @throws IOException
	 */
	public void processCommand(String command_input) throws IOException{
		String[] commandline = command_input.split(" ");
		String command = commandline[0] + " " + commandline[1];
		String version = commandline[2];
		changeConnectionStatus(version, command);	
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out.println(command + "\r" + "\n");
		String output;
		while((output = in.readLine()) != null){
			System.out.println(output);
		}		
	}
	
	

}
