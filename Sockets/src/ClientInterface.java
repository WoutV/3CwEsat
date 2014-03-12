//import java.net.*;
import java.io.*;

public class ClientInterface {

	public ClientInterface() {

	}

	public static void main(String[] args) {
		ClientHandler clientHandler = null;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String command;
		String host;
		int port;
		String version;
		
		command = args[0];
		host = args[1];
		try {
			port = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			port = 80;
		}
		version = args[3];
		try {
			String[] splittedhost = host.split("/");
			clientHandler = new ClientHandler(splittedhost[0], port);
			System.out.println("Connection created: " + splittedhost[0] + " on port "+ port);
			String commandToProcess = command + " " + host + " " + version;
			clientHandler.processCommand(commandToProcess);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (clientHandler.getConnectionStatus() == true) {
			System.out.println("<command> <file> <version>");
			try {
				command = stdIn.readLine();
				String commandToProcess = command + " " + host + " " + version;
				clientHandler.processCommand(commandToProcess);
			} catch (IOException e) {
				
			}
		}
	}
}
