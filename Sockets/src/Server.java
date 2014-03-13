import java.io.*;
import java.net.*;

public class Server {

	public static void main(String[] args) throws IOException {

		final int portNumber;
		int temp_portNumber;

		try {
			temp_portNumber = Integer.parseInt(args[0]);
		} catch (Exception E) {
			temp_portNumber = 6789;
		}

		portNumber = temp_portNumber;

		try {
			ServerSocket serverSocket = new ServerSocket(portNumber);
			System.out.println("httpServer running on port "+ serverSocket.getLocalPort());
			// server on infinite loop
			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("New connection accepted "+ clientSocket.getInetAddress() + ":"+ clientSocket.getPort());
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
}