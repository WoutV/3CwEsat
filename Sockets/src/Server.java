import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) throws IOException {

		final int portNumber = 1234;

		try (ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(
						clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));) {
			String inputLine, outputLine;
			while ((inputLine = in.readLine()) != null) {
				outputLine = "I received your message, filthy peasant";
				System.out.println("client:" + inputLine);
				out.println(outputLine);
			}
			clientSocket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			System.out
					.println("Exception caught when trying to listen on port "
							+ portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}