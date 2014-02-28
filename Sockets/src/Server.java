import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) throws IOException {

		final int portNumber = Integer.parseInt(args[0]);

		try { 
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // naar client
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  // van client
			String inputLine, outputLine;
			while ((inputLine = in.readLine()) != null) {
				outputLine = "I received your message, filthy peasant";
				System.out.println("client:" + inputLine);
				out.println(outputLine);
			}
			//DO
			//DO CRAP
			//DO CRAP IF
			//DO CRAP IF TRY CATCH
			
			// if(command.equals("get")) {
			// //DO CRAP
			// } else if(command.equals("head")) {
			// //DO OTHER CRAP
			// } else if(command.equals("put")) {
			// //GUESS WHAT DO MORE CRAP
			// } else if(command.equals("post")) {
			// //POST SOMETHING
			// } else {
			// System.out.println("Invalid command try again");
			// }
//			out.println(userInput);
			
			//closing the connections and streams
			serverSocket.close();
			clientSocket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}