
//import java.net.*;
import java.io.*;
import java.net.UnknownHostException;


public class ClientInterface {



	public ClientInterface(){

	}

	public static void main(String[] args) throws IOException, UnknownHostException {
		String command = "";
		String host = "";
		int port = 0;
		String version = "";
		try {
			command = args[0];
			host = args[1];
			port = Integer.parseInt(args[2]);
			version = args[2];
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("<command> <URI> <port> <version>");
		}
		boolean woutisnefag = true;
		
		while(woutisnefag){
			ClientHandler clientHandler= null;
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); //reads the user input from the command line.
			//System.out.println("Create a new socket connection: <host> <port>");
			//String[] connectionInput = stdIn.readLine().split(" ");
			//host = connectionInput[0];
			//port = 80;
			//try{
				//port = Integer.parseInt(connectionInput[1]);
			//} catch (Exception E){
				//port = 80
			//}
			try {
				String [] splittedhost = host.split("/");
				clientHandler = new ClientHandler(splittedhost[0], port);
				System.out.println("Connection created: "+host+" on port "+port);
			} catch (IOException e) {
				e.printStackTrace();
				//stacktrace printen is ni ideaal om in uwe commandline te krijgen e :D
			}
			while(clientHandler.getConnectionStatus() == true){
				System.out.println("<command> <file> <version>");
				//command = stdIn.readLine();
				String commandToProcess = command +" "+host+" "+version;
				clientHandler.processCommand_better(commandToProcess);
			}
		}
	}
}
