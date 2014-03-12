import java.io.*;
import java.net.*;

public class ClientHandler {
	boolean connectionOpen;
	Socket clientSocket;
	String host;
	int port;
	
	/**
	 * Creates a new client handler with given host and port.
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	public ClientHandler(String host, int port) throws IOException{
		connectionOpen = true;
		this.host = host;
		this.port = port;
		establishConnection(host, port);
	}
	
	/**
	 * Creates a socket with given host on given port.
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	private void establishConnection(String host, int port) throws IOException{
		clientSocket = new Socket(host,port);
	}
	
	private void changeConnectionStatus(String version, String commandWord) throws IOException{
		if(version.equals("HTTP/1.0") || commandWord.toUpperCase().equals("QUIT")){
			connectionOpen = false;
		}
		if(version.equals("HTTP/1.1")){
			establishConnection(this.host, this.port);
		}
	}
	
	/**
	 * Returns whether the connection to the host is open or closed. 
	 */
	public boolean getConnectionStatus(){
		return connectionOpen;
	}
	
	public void processCommand(String command_input) throws IOException {
		String[] commandline = command_input.split(" ");
		String command ="";
		String version ="";
		try {
			command = commandline[0] + " " + commandline[1];
			version = commandline[2];
		} catch (ArrayIndexOutOfBoundsException e) {
			if (commandline[0].toUpperCase().equals("QUIT")) {
				closeConnection();
			} 
		}
		changeConnectionStatus(version, command);	
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		if(version.equals("HTTP/1.0")){
			out.println(command + "\r\n");
			closeConnection();
		}
		
		else if(version.equals("HTTP/1.1")){
			String new_command = command + " " + version;
			out.println(new_command + "\r\n");
		}
		else{
			System.out.println("This HTTP version is not supported. Please choose HTTP/1.1 or HTTP/1.0");
			String new_request = (new BufferedReader(new InputStreamReader(System.in))).readLine();
			processCommand(new_request);
		}
		String output;
		while((output = in.readLine()) != null && in.ready()){
			if(output.contains("<img")){
				String[] splittedOutput = output.split("src=\"");
				String imgPath = splittedOutput[1].split("\"")[0];
				//writeToDisk(imgPath);
				processCommand("GET "+ imgPath + "HTTP/1.1");
			}
			System.out.println(output);
		}		
	}
	
//	private void writeToDisk(String imgPath) throws IOException {
//		System.out.println(imgPath);
//		DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
//		dos.writeBytes("GET " + host + imgPath + "HTTP/1.1\r\n");
//		dos.writeBytes("Host: "+ this.host + ":80\r\n\r\n");
//		dos.flush();
//		DataInputStream in = new DataInputStream(clientSocket.getInputStream());
//		OutputStream os = new FileOutputStream("C:\\Users\\Wout\\Desktop\\test.jpg");
//		int count;
//		byte[] buffer = new byte[2048];
//		while((count = in.read(buffer)) != -1){
//			os.write(buffer, 0, count);
//			os.flush();
//		}
//		os.close();
//		System.out.println("image transfer done");		
//	}

	private void closeConnection(){
		System.out.println("Closing connection... \r\n");
	}
}