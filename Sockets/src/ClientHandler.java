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
			System.out.println(commandline[1]);
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
	}

	private void postMethod(String command, String version) throws IOException{
		out.write(command + " " +version+ "\r\n");
		System.out.println("Enter the text you want to add to the file. End with a single . (dot).");
		String userInput;
		while(!(userInput=stdIn.readLine()).equals(".") && userInput!=null) {
			System.out.println("sending line");
			out.write(userInput + "\r\n");
		}
		out.flush();
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
		out.flush();
		String output;
		while (((output = in.readLine()) != null) && in.ready()) {
			System.out.println(output);
//			if(output.contains("<img")){
// 				String[] splittedOutput = output.split("src=\"");
//// 				String imgPath = splittedOutput[1].split("\"")[0];
// 				//writeToDisk(imgPath);
// 				processCommand("GET "+ imgPath + "HTTP/1.1");
//	 		}
		}
	}

//	private void writeToDisk(String imgPath) throws IOException {
//		System.out.println(imgPath);
//		URL url = new URL(imgPath);
//		URLConnection conn = url.openConnection();
//	
//		// open the stream and put it into BufferedReader
//		BufferedReader br = new BufferedReader(
//	                       new InputStreamReader(conn.getInputStream()));
//	
//		String inputLine;
//	
//		//save to this filename
//		String fileName = "C:/Users/Wout/Desktop/test";
//		File file = new File(fileName);
//	
//		if (!file.exists()) {
//			file.createNewFile();
//		}
//	
//		//use FileWriter to write file
//		FileWriter fw = new FileWriter(file.getAbsoluteFile());
//		BufferedWriter bw = new BufferedWriter(fw);
//	
//		while ((inputLine = br.readLine()) != null) {
//			bw.write(inputLine);
//		}
//	
//		bw.close();
//		br.close();
//	}
	
//	private void writeToDisk(String imgPath) throws IOException {
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src\\"+file), "utf-8"));
//		writer.write(file);
//	}

//	private ArrayList<String> getContents(File file) throws IOException {
//		ArrayList<String> contents = new ArrayList<String>();
//
//		BufferedReader input = new BufferedReader(new FileReader(file));
//		String line;
//		while ((line = input.readLine()) != null) {
//			contents.add(line);
//		}
//		input.close();
//
//		return contents;
//	}
	
	private void procesInvalidRequest() throws IOException{
		System.out.println("This request was not valid. Please check your HTTP version and your request format.");
		String new_request = stdIn.readLine();
		processCommand(new_request);
		
	}
}