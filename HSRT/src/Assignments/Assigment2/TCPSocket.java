package Assignments.Assigment2;

import java.io.*;
import java.net.*;

public class TCPSocket implements AutoCloseable
{
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private BufferedReader bufRea;

	public TCPSocket(String serverAddress, int serverPort)
			throws UnknownHostException, IOException
	{
		socket = new Socket(serverAddress, serverPort);
		initializeStreams();
	}

	public TCPSocket(Socket socket) throws IOException
	{
		this.socket = socket;
		initializeStreams();
	}

	public void sendObject(Object o) throws IOException
	{
		objectOutputStream.writeObject(o);
	}
	public Object receiveObject() throws IOException, ClassNotFoundException {

		return objectInputStream.readObject();
	}
	public String readLine() throws IOException {
		bufRea = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String response = bufRea.readLine();
		return response;
	}



	public void close() throws IOException
	{
		socket.close();
	}

	private void initializeStreams() throws IOException
	{
		objectInputStream = new ObjectInputStream(in = socket.getInputStream());
		objectOutputStream = new ObjectOutputStream(out = socket.getOutputStream());
	}



	public Socket getSocket() {
		return socket;
	}
}
