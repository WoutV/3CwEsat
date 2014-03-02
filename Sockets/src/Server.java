import java.io.*;
import java.net.*;

public class Server {
	
	public static void main(String[] args) throws IOException {
		
		final int portNumber;
		int temp_portNumber;
		
		try{
			temp_portNumber = Integer.parseInt(args[0]);
		} catch (Exception E){
			temp_portNumber = 6789;
		}
		
		portNumber = temp_portNumber;
		
		
		// serversocket wordt zogezegd nooit gesloten maar hier nog kijken voor http/1.0 en http/1.1 hoe dit gedaan wordt
		// waar en waneer de verbinding sluiten
		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			System.out.println("httpServer running on port " + serverSocket.getLocalPort());
			//server on infinite loop
			while(true){
				Socket clientSocket = serverSocket.accept();
				System.out.println("New connection accepted "
			            + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
				// Construct handler to process the HTTP request message.
		        try {
		          httpHandler request = new httpHandler(clientSocket);
		          // Create a new thread to process the request.
		          Thread thread = new Thread(request);

		          // Start the thread.
		          thread.start();
		        } catch (Exception e) {
		          System.out.println(e);
		        }
		      }
		    } catch (IOException e) {
		      System.out.println(e);
		    }
		  }
//			Socket clientSocket = serverSocket.accept();
//			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // naar client
//			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  // van client
//			String inputLine, outputLine;
//			while ((inputLine = in.readLine()) != null) {
//				outputLine = "I received your message, filthy peasant";
//				System.out.println("client:" + inputLine);
//				out.println(outputLine);
//			}
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
		
	}