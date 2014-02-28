import java.net.*;
import java.io.*;

public class Client {

	public static void main(String[] args) {
		try {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); //reads the user input from the command line. 
			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
				String[] input = userInput.split(" ");
				String command = input[0] + " " + input[1];
				String host = input[2];
				String version = input[3];
				int port = Integer.parseInt(input[4]);
				Socket echoSocket = new Socket(host, port);
				
				//create streams to read from and write to the socket
				PrintWriter out = new PrintWriter(echoSocket.getOutputStream(),true);
				BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())); 
				
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
