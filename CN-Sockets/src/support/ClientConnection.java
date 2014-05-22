package support;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnection {
	
	private Socket socket;
	private BufferedReader in;
	private DataOutputStream out;
	
	public ClientConnection(String host, int port, String charset) throws IOException
	{
		socket = new Socket(InetAddress.getByName(host), port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream(), charset));
		out = new DataOutputStream (socket.getOutputStream());
	}
	
	public void write(String message) throws IOException
	{
		out.writeBytes(message);
	}
	
	public String readLine() throws IOException
	{
		return in.readLine();
	}
	
	public String readUntilEmpty() throws IOException
	{
		StringBuilder response = new StringBuilder();
		String line = in.readLine();
		while (line != null)
		{
			if (! (response.toString() == ""))
			{
				response.append("\n");
			}
			response.append(line);
			line = in.readLine();
		}
		return response.toString();
	}
	
	public void close() throws IOException
	{
		if (! socket.isClosed())
			socket.close();
		in.close();
		out.close();
		in = null;
		out = null;
	}

}
