import java.io.*;

public class ClientInterface {

	public ClientInterface() {

	}

	public static void main(String[] args) {
		//command host port httpversion
		ClientHandler clientHandler = null;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		
		String command;
		String host;
		String version;
		int port;
		
		while(args.length!=4){
			try {
				args = stdIn.readLine().split(" ");
			} catch (IOException e) {
				//wrong input, restart loop and wait for new arguments
			}
		}
		command = args[0];
		host = args[1];
		try {
			port = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			port = 80;
		}
		version = args[3];
		
		try {
			String[] splittedHost = host.split("/",2);
			clientHandler = new ClientHandler(splittedHost[0], port);
			System.out.println("Connection created: " + splittedHost[0] + " on port "+ port);
//			String commandToProcess = command + " /" + splittedHost[1] + " " + version;
			String commandToProcess = command + " " + splittedHost[1] + " " + version;
			clientHandler.processCommand(commandToProcess);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (clientHandler.getConnectionStatus() == true) {
			System.out.println("<command> <file> <version>");
			try {
				String newCommand = stdIn.readLine();
				String commandToProcess = newCommand;
				clientHandler.processCommand(commandToProcess);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("The connection has been closed. Enjoy the real world now.");
	}
}
