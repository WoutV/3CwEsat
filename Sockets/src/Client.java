import java.net.*;
import java.io.*;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);

		try (Socket echoSocket = new Socket(hostName, portNumber);
				PrintWriter out = new PrintWriter(echoSocket.getOutputStream(),
						true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						echoSocket.getInputStream()));
				BufferedReader stdIn = new BufferedReader(
						new InputStreamReader(System.in))) {
			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				System.out.println("echo: " + in.readLine());
			}
		}
	}
}
