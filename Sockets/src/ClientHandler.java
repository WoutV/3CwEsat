import java.net.*;
import java.io.*;

public class ClientHandler {
	boolean connectionOpen;
	Socket clientSocket;
	String host;
	int port;
	
	public ClientHandler(String host, int port) throws IOException{
		connectionOpen = true;
		this.host = host;
		this.port = port;
		establishConnection(host, port);
	}
	
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
	
	public boolean getConnectionStatus(){
		return connectionOpen;
	}
	
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
