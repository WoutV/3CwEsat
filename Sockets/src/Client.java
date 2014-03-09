import java.net.*;
import java.io.*;

public class Client {

	public static void main(String[] args) {
		try {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); //reads the user input from the command line. 
		
			System.out.println("Create a connection: <host> <port>");
			String[] connectionInput = stdIn.readLine().split(" ");
			String host = connectionInput[0];
			int port = 80;
			try{
				port = Integer.parseInt(connectionInput[1]);
			} catch (Exception E){
				
			}
			
			Socket echoSocket = new Socket(host, port);
			
			//create streams to read from and write to the socket
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(),true);
			 
			
			System.out.println("Connection created: "+host+" on port "+port);
			System.out.println("<command> <file> <version>");
			
			String version = "HTTP/1.1";
			String userInput;
			
			while (version.equals("HTTP/1.1")) {
				BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
				userInput = stdIn.readLine();
				String[] input = userInput.split(" ");
				
				String command = input[0] + " " + input[1];
				version = input[2];
	
				//out.println(command + " " + version + "\r" + "\n");
				out.println(command+ "\r" + "\n");
				//out.flush();
				
				String output;
								
				while ((output = in.readLine()) != null) {
					System.out.println(output);
				}
				System.out.println("command processed succesful");
			}
			System.out.println("Closing connection");
			echoSocket.close(); //moet de server de connection ni sluiten?
		} catch (UnknownHostException e) {
			System.out.println("Host unknown. Cannot create connection.");
		} catch (IOException e) {
			System.out.println("Unable to connect.");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("input template: <command> <file> <version>");
		}
	}
}
