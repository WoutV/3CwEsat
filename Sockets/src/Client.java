import java.net.*;
import java.io.*;

public class Client {

	public static void main(String[] args){
		String hostName = "www.google.com";
		int portNumber = 80;

		try {
			Socket echoSocket = new Socket(hostName, portNumber);
				PrintWriter out = new PrintWriter(echoSocket.getOutputStream(),
						true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						echoSocket.getInputStream()));	//wa ge krijgt van de server
				BufferedReader stdIn = new BufferedReader(	// wa ge typt
						new InputStreamReader(System.in));
			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
				String[] input = userInput.split(" ");
				String command = input[0].toLowerCase();
//				if(command.equals("get")) {
//					//DO CRAP
//				} else if(command.equals("head")) {
//					//DO OTHER CRAP
//				} else if(command.equals("put")) {
//					//GUESS WHAT DO MORE CRAP
//				} else if(command.equals("post")) {
//					//POST SOMETHING
//				} else {
//					System.out.println("Invalid command try again");
//				}
				out.println(userInput);
				System.out.println("echo: " + in.readLine());
			}
		} catch (UnknownHostException e) {
			System.out.println("Host unknown. Cannot create connection.");
		} catch (IOException e) {
			System.out.println("Unable to connect.");
		}
		
	}
}
