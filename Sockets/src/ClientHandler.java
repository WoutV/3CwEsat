import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler {
	boolean connectionOpen;
	Socket clientSocket;
	String host;
	int port;
	PrintWriter out;
	BufferedReader in;
	BufferedReader stdIn;
	String[] valid_http_versions = {"HTTP/1.1", "HTTP/1.0"};

	/**
	 * Creates a new client handler with given host and port.
	 * 
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	public ClientHandler(String host, int port) throws IOException {
		stdIn = new BufferedReader(new InputStreamReader(System.in));
		connectionOpen = true;
		this.host = host;
		this.port = port;
		establishConnection(host, port);
	}

	/**
	 * Creates a socket with given host on given port.
	 * 
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	private void establishConnection(String host, int port) throws IOException {
		clientSocket = new Socket(host, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
	}

	/**
	 * 
	 * @param version The HTTP version that was used in the command
	 * @param commandWord
	 * @throws IOException
	 */
	private void changeConnectionStatus(String version)
			throws IOException {
		if (version.equals("HTTP/1.0")) {
			connectionOpen = false;
		}
		if (version.equals("HTTP/1.1")) {
			establishConnection(this.host, this.port);
		}
	}

	/**
	 * Returns whether the connection to the host is open or closed.
	 */
	public boolean getConnectionStatus() {
		return connectionOpen;
	}

	public void processCommand(String command_input) throws IOException {
		String[] commandline = command_input.split(" ");
		String command = "";
		String version = "";
		String commandword= "";
		try {
			command = commandline[0] + " " + commandline[1];
			version = commandline[2];
			commandword = commandline[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			procesInvalidRequest();
		}
		if(! (Arrays.asList(valid_http_versions).contains(version))){
			procesInvalidRequest();

		}
		changeConnectionStatus(version);
		if(commandword.toUpperCase().equals("POST")){
			postMethod(command, version);
		}
		else if(commandword.toUpperCase().equals("GET")){
			getMethod(command, version);
		}
		else if(commandword.toUpperCase().equals("PUT")){
			putMethod(command, version);
		}
		else if(commandword.toUpperCase().equals("HEAD")){
			headMethod(command, version);
		}
//		if(version.equals("HTTP/1.1")){
//			establishConnection(host, port);
//		} else{
//			closeConnection();
//		}
		//		if (version.equals("HTTP/1.0")) {
		//			if (commandline[0].equals("POST")) {
		//				postMethod(commandline[1]);
		//
		//			}
		//			else {
		//				out.println(command + "\r\n");
		//			}
		//			closeConnection();
		//		}
		//
		//		else if (version.equals("HTTP/1.1")) {
		//			String new_command = command + " " + version;
		//			out.println(new_command + "\r\n");
		//		} else {
		//			System.out
		//			.println("This HTTP version is not supported. Please choose HTTP/1.1 or HTTP/1.0");
		//			String new_request = (new BufferedReader(new InputStreamReader(
		//					System.in))).readLine();
		//			processCommand(new_request);
		//		}
	}

	private void postMethod(String filepath, String version) throws IOException{
		File file = new File(filepath);
		for (String line : getContents(file)) {
			System.out.println(line);
			out.write(line + "\r\n");
		}
	}

	private void putMethod(String filepath, String version){

	}

	private void headMethod(String command, String version) throws IOException{
		String newCommand = command + " " + version;
		out.println(newCommand + "\r\n");
		String output;
		while (((output = in.readLine()) != null) && in.ready()) {
			System.out.println(output);
		}
		
	}

	private void getMethod(String command, String version) throws IOException{
		String newCommand = command + " " + version;
		out.println(newCommand + "\r\n");
		String output;
		while (((output = in.readLine()) != null) && in.ready()) {
			System.out.println(output);
		}

	}

//	private void closeConnection() {
//		System.out.println("Closing connection... \r\n");
//	}

	private ArrayList<String> getContents(File file) throws IOException {
		ArrayList<String> contents = new ArrayList<String>();

		BufferedReader input = new BufferedReader(new FileReader(file));
		String line;
		while ((line = input.readLine()) != null) {
			contents.add(line);
		}
		input.close();

		return contents;
	}
	
	private void procesInvalidRequest() throws IOException{
		System.out.println("This request was not valid. Please check your HTTP version and your request format.");
		String new_request = stdIn.readLine();
		processCommand(new_request);
		
	}
}