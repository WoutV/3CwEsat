import java.net.*;
import java.io.*;

public class Client {

	public static void main(String[] args) {
		try {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); //reads the user input from the command line. 
			String userInput;
			System.out.println("Create a connection: <host> <port>");
			String[] connectionInput = stdIn.readLine().split(" ");
			String host = connectionInput[0];
			int port = Integer.parseInt(connectionInput[1]);
			Socket echoSocket = new Socket(host, port);
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())); 
			
			while ((userInput = stdIn.readLine()) != null) {
				String[] input = userInput.split(" ");
				String command = input[0] + " " + input[1];
				String version = input[2];
				//String host = input[2];
				//String host = "localhost";
				//int port = Integer.parseInt(input[4]);
				//int port = 6789;
				//Socket echoSocket = new Socket(host, port);
				
				//create streams to read from and write to the socket
				
				
				out.println(command + " " + version + "\r" + "\n");
				//out.println(userInput);
				String output;
				while ((output = in.readLine()) != null) {
					System.out.println(output);
				}
				if(version.equals("HTTP/1.0")) {
					System.out.println("Closing connection");
					echoSocket.close(); //moet de server de connection ni sluiten?
				}
			}
		} catch (UnknownHostException e) {
			System.out.println("Host unknown. Cannot create connection.");
		} catch (IOException e) {
			System.out.println("Unable to connect.");
		}
	}
}
