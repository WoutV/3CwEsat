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
	 * Initializes a new httpHandler with a given socket. Initializes its input
	 * and output streams.
	 * 
	 * @param socket
	 * @throws Exception
	 */
	public httpHandler(Socket socket) throws Exception {
		this.socket = socket;
		this.input = socket.getInputStream();
		this.output = socket.getOutputStream();
		this.br = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		this.outputPrinter = new PrintWriter(output, true);
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

	private void processRequest() throws Exception{
		String userInputLine = br.readLine();
		System.out.println(userInputLine);
		if (userInputLine.equals(CRLF) || userInputLine.equals(""))
			closeConnection();
		String[] request = userInputLine.split(" ");
		String commandWord = request[0].toUpperCase();
		String version = request[2];
		String fileName = request[1];
		System.out.println(commandWord);
		if (commandWord.equals("GET")) {
			System.out.println("GET ENTERED MOFOS");
			System.out.println(fileName);
			getCommand(fileName);
		} else if (commandWord.equals("PUT")) {
			putCommand();
		} else if (commandWord.equals("POST")) {
			System.out.println("enterd POST");
			postCommand(fileName);
		} else if (commandWord.equals("HEAD")) {
			headCommand(fileName);
		}
		if (version.equals("HTTP/1.0")) {
			closeConnection();
		} else {
			userInputLine = br.readLine();
			processRequest();
		}

		try {
			closeConnection();
		} catch (Exception e) {
		}
	}

	private void getCommand(String fileName) throws Exception {
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
		System.out.println(fileExists);
		if (fileExists) {
			statusLine = "HTTP/1.0 200 OK" + CRLF;
			contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
			contentLengthLine = "Content-Length: "
					+ (new Integer(fis.available())).toString() + CRLF;
		} else {
			statusLine = "HTTP/1.0 404 Not Found" + CRLF;
			contentTypeLine = "text/html";
			entityBody = "<HTML>" + "<HEAD><TITLE>404 Not Found</TITLE></HEAD>"
					+ "<BODY>404 Not Found"
					+ "<br>usage:http://yourHostName:port/"
					+ "fileName.html</BODY></HTML>";
		}

		// Send the status line.
		outputPrinter.write(statusLine);
		System.out.println(statusLine);

		// Send the content type line.
		outputPrinter.write(contentTypeLine);
		System.out.println(contentTypeLine);

		// Send the Content-Length
		outputPrinter.write(contentLengthLine);
		System.out.println(contentLengthLine);

		// Send a blank line to indicate the end of the header lines.
		output.write(CRLF.getBytes());
		System.out.println(CRLF);

		// Send the entity body.
		if (fileExists) {
			System.out.println("entered fileexists");
			sendBytes(fis, output);
			fis.close();
		} else {
			System.out.println(entityBody);
		}
	}

	private void putCommand() {
		
	}

	private void postCommand(String filename) {
		System.out.println("enterd post duuude");
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
		    String userInput;
		    while((userInput=br.readLine())!= null && br.ready()) {
		    	System.out.println(userInput);
		    	out.println(userInput);
		    }
			
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	}

	private void headCommand(String fileName) throws IOException {
		System.out.println("HEAD REACHED MOFOS");
		FileInputStream fis = null;
		boolean fileExists = true;
		try {
			fis = new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			fileExists = false;
		}
		String statusLine = null;
		String contentTypeLine = null;
		String contentLengthLine = "error";
		if (fileExists) {
			statusLine = "HTTP/1.0 200 OK" + CRLF;
			contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
			contentLengthLine = "Content-Length: "
					+ (new Integer(fis.available())).toString() + CRLF;
		} else {
			System.out.println("file not found");
			statusLine = "HTTP/1.0 404 Not Found" + CRLF;
			contentTypeLine = "text/html";
		}

		// Send the status line.
		outputPrinter.write(statusLine);
		System.out.println(statusLine);

		// Send the content type line.
		outputPrinter.write(contentTypeLine);
		System.out.println(contentTypeLine);

		// Send the Content-Length
		outputPrinter.write(contentLengthLine);
		System.out.println(contentLengthLine);

		// Send a blank line to indicate the end of the header lines.
		outputPrinter.write(CRLF);
		System.out.println(CRLF);

//		 // Send the entity body.
//		 if (fileExists) {
//			 sendBytes(fis, output);
//			 fis.close();
//		 } else {
//			 output.write(entityBody.getBytes());
//		 }
	}

	private static void sendBytes(FileInputStream fis, OutputStream os)
			throws Exception {

		byte[] buffer = new byte[1024];
		int bytes = 0;

		while ((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}

	private void closeConnection() throws Exception {
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
