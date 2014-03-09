
//import java.net.*;
import java.io.*;


public class ClientInterface {



	public ClientInterface(){

	}

	public static void main(String[] args) throws IOException {
		boolean woutisnefag = true;
		while(woutisnefag){
			ClientHandler clientHandler= null;
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); //reads the user input from the command line.
			System.out.println("Create a connection: <host> <port>");
			String[] connectionInput = stdIn.readLine().split(" ");
			String host = connectionInput[0];
			int port = 80;
			try{
				port = Integer.parseInt(connectionInput[1]);
			} catch (Exception E){
				//do something maybe? :D
			}
			try {
				clientHandler = new ClientHandler(host, port);
				System.out.println("Connection created: "+host+" on port "+port);
			} catch (IOException e) {
				e.printStackTrace();
				//stacktrace printen is ni ideaal om in uwe commandline te krijgen e :D
			}
			while(clientHandler.getConnectionStatus() == true){
				System.out.println("<command> <file> <version>");
				String command = stdIn.readLine();
				clientHandler.processCommand_better(command);
			}
		}
	}
}
