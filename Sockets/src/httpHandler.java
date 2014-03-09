import java.io.*;
import java.net.Socket;

public class httpHandler implements Runnable {
	final static String CRLF = "\r\n";
	Socket socket;

	InputStream input;
	OutputStream output;
	BufferedReader br;
	PrintWriter outputPrinter;

	/**
	 * Initializes a new httpHandler with a given socket. Initializes its input and output streams.
	 * @param socket
	 * @throws Exception
	 */
	public httpHandler(Socket socket) throws Exception {
		this.socket = socket;
		this.input = socket.getInputStream();
		this.output = socket.getOutputStream();
		this.br = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		this.outputPrinter = new PrintWriter(output,true);
	}

	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			System.out.println("Something went wrong.");
			try {
				closeConnection();
			} catch (Exception e1) {
			}
		}
	}

	private void processRequest() throws Exception {
			String userInputLine = br.readLine();
			System.out.println(userInputLine);
			if (userInputLine.equals(CRLF) || userInputLine.equals(""))
				closeConnection();
			String[] request = userInputLine.split(" ");
			String temp = request[0].toUpperCase();
			String version = request[3];
			String fileName = request[1];

			if (temp.equals("GET")) {
				getCommand(fileName);
			}		
			else if(temp.equals("PUT")){
				putCommand();
			}
			else if(temp.equals("POST")){
				postCommand();
			}
			else if(temp.equals("HEAD")){
				headCommand();
			}
			System.out.println("kijken naar versie: " + version);
			if(version.equals("HTTP/1.0") || temp.equals("QUIT")) {
				closeConnection();
			} else {
				processRequest();
			}

		try {
			closeConnection();
		} catch (Exception e) {
		}
	}
	
	
	private void getCommand(String fileName) throws Exception{
		FileInputStream fis = null;
		boolean fileExists = true;
		try {
			fis = new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			fileExists = false;
		}
		String statusLine = null;
		String contentTypeLine = null;
		String entityBody = null;
		String contentLengthLine = "error";
		if (fileExists) {
			statusLine = "HTTP/1.0 200 OK" + CRLF;
			contentTypeLine = "Content-type: " + contentType(fileName)
					+ CRLF;
			contentLengthLine = "Content-Length: "
					+ (new Integer(fis.available())).toString() + CRLF;
		} else {
			statusLine = "HTTP/1.0 404 Not Found" + CRLF;
			contentTypeLine = "text/html";
			entityBody = "<HTML>"
					+ "<HEAD><TITLE>404 Not Found</TITLE></HEAD>"
					+ "<BODY>404 Not Found"
					+ "<br>usage:http://yourHostName:port/"
					+ "fileName.html</BODY></HTML>";
		}

		// Send the status line.
		output.write(statusLine.getBytes());
		System.out.println(statusLine);

		// Send the content type line.
		output.write(contentTypeLine.getBytes());
		System.out.println(contentTypeLine);

		// Send the Content-Length
		output.write(contentLengthLine.getBytes());
		System.out.println(contentLengthLine);

		// Send a blank line to indicate the end of the header lines.
		output.write(CRLF.getBytes());
		System.out.println(CRLF);

		// Send the entity body.
		if (fileExists) {
			sendBytes(fis, output);
			fis.close();
		} else {
			output.write(entityBody.getBytes());
		}
	}
	
	private void putCommand(){
			
	}
	
	private void postCommand(){
		
	}
	private void headCommand(){
		
	}

	private static void sendBytes(FileInputStream fis, OutputStream os)
			throws Exception {

		byte[] buffer = new byte[1024];
		int bytes = 0;

		while ((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}
	
	private void closeConnection() throws Exception{
		outputPrinter.write("Connection closing. Goodbye");
		output.close();
		br.close();
		socket.close();
	}

	private static String contentType(String fileName) {
		if (fileName.endsWith(".htm") || fileName.endsWith(".html")
				|| fileName.endsWith(".txt")) {
			return "text/html";
		} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (fileName.endsWith(".gif")) {
			return "image/gif";
		} else {
			return "application/octet-stream";
		}
	}
	
	
}
