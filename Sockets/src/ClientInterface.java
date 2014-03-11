//import java.net.*;
import java.io.*;
import java.net.UnknownHostException;

public class ClientInterface {

	public ClientInterface() {

	}

	public static void main(String[] args) {
		ClientHandler clientHandler = null;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String command = "";
		String host = "";
		int port = 80;
		String version = "";
		try {
			command = args[0];
			host = args[1];
			port = Integer.parseInt(args[2]);
			version = args[3];
		} catch (ArrayIndexOutOfBoundsException e) {
			String[] input = {};
			while(input.length != 4){
				System.out.println("<command> <URI> <port> <version>");
				try {
					input = stdIn.readLine().split(" ");
					command = input[0];
					host = input[1];
					version = input[3];
					port = Integer.parseInt(input[2]);
				} catch (NumberFormatException e1){
					port = 80;
				} catch (IOException e1) {
					System.out.println("<command> <URI> <port> <version>");
				}
			}
		}
		try {
			String[] splittedhost = host.split("/");
			clientHandler = new ClientHandler(splittedhost[0], port);
			System.out.println("Connection created: " + host + " on port "+ port);
			String commandToProcess = command + " " + host + " " + version;
			clientHandler.processCommand_better(commandToProcess);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (clientHandler.getConnectionStatus() == true) {
			System.out.println(version);
			System.out.println("<command> <file> <version>");
			try {
				command = stdIn.readLine();
				String commandToProcess = command + " " + host + " " + version;
				clientHandler.processCommand_better(commandToProcess);
			} catch (IOException e) {
				
			}
		}
	}
}
