import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
		String command_word = commandline[0];
		if(command_word.equals("QUIT")){
			closeConnection();
		}
		String command = commandline[0] + " " + commandline[1];
		String version = commandline[2];
		changeConnectionStatus(version, command);	
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out.println(command + "\r\n");
		String output;
		while((output = in.readLine()) != null){
			System.out.println(output);
		}		
	}
	
	public void processCommand_better(String command_input) throws IOException{
		//quit command heeft maar 1 woord split zal dus een array van 1 terug geven.
		String[] commandline = command_input.split(" ");
		String command = commandline[0] + " " + commandline[1];
		String version = commandline[2];
		changeConnectionStatus(version, command);	
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		if(version.equals("HTTP/1.0")){
			out.println(command + "\r\n");
		}
		
		else if(version.equals("HTTP/1.1")){
			System.out.println("we zitte aan verise 1.1");
			String new_command = command + " " + version;
			out.println(new_command + "\r\n");
		}
		else{
			System.out.println("This HTTP version is not supported. Please chose HTTP/1.1 or HTTP/1.0");
			String new_request = (new BufferedReader(new InputStreamReader(System.in))).readLine();
			processCommand(new_request);
		}
		String output;
		while((output = in.readLine()) != null && in.ready()){
			System.out.println(output);
		}		
	}
	
	private void closeConnection(){
		System.out.println("Closing connection... \r\n");
	}
	
	

}