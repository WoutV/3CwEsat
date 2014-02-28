import java.net.*;
import java.io.*;

public class Client {

	public static void main(String[] args) {
//		String hostName = "www.google.com";
//		int portNumber = 80;
		
		

		try {
			BufferedReader stdIn = new BufferedReader( // wa ge typt
					new InputStreamReader(System.in));
			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
				String[] input = userInput.split(" ");
				String command = input[0] + " " + input[1];
				String host = input[2];
				String version = input[3];
				int port = Integer.parseInt(input[4]);
				Socket echoSocket = new Socket(host, port);
				
				PrintWriter out = new PrintWriter(echoSocket.getOutputStream(),
						true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						echoSocket.getInputStream())); // wa ge krijgt van de server
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
//				out.println(userInput);
				out.println(command + " " + version + "\r" + "\n");
				String output;
				while ((output = in.readLine()) != null) {
					System.out.println(output);
				}
				echoSocket.close();
			}
		} catch (UnknownHostException e) {
			System.out.println("Host unknown. Cannot create connection.");
		} catch (IOException e) {
			System.out.println("Unable to connect.");
		}

	}
}
