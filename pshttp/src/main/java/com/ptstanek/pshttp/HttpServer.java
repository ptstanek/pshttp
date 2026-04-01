package com.ptstanek.pshttp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer implements Runnable {
	private ServerSocket serverSocket;
	private int serverPort;
	private boolean running;

	private HtmlFile indexFile;

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public int getServerPort() {
		return serverPort;
	}

	public HttpServer(int serverPort) {
		this.serverPort = serverPort;
		this.indexFile = new HtmlFile("WWW/index.html");
	}

	public HttpServer() {
		this(8080);
	} // default constructor

	@Override
	public void run() {
		running = true;
		scanWWWDirectory();
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Server listening on PORT: " + serverSocket.getLocalPort());
		while (running) { // TODO: Logging into an sqlite database
			try (Socket clientSocket = serverSocket.accept(); OutputStream writer = clientSocket.getOutputStream()) {
				System.out.println("accepted connection from client: " + clientSocket.getInetAddress());
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				// PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true); // the true enables autoflushing

				String data = reader.readLine();

				if (data == null) {
					// if the line is empty it means the client has closed the connection.
					return;
				}
				
				while(!(data = reader.readLine()).isEmpty()) {
					// reading headers until they are empty.
				}
				
				byte[] fileBytes = indexFile.getBytes();
				
				String headers = "HTTP/1.1 200 OK\r\nContent-Length: " + fileBytes.length + "\r\n" + "Content-Type: text/html; charset=utf-8\r\n" + "Connection: close\r\n" + "\r\n";
				writer.write(headers.getBytes(StandardCharsets.US_ASCII));
				writer.write(fileBytes);
				writer.flush();

				clientSocket.close();
				
			} catch (Exception e) {

			}

		}

	}

	public void stop() throws IOException {
		running = false;
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void scanWWWDirectory() { // TODO: sort this out
		File wwwPath = new File("www/");
		String[] filesInDirectory = null;

		if (wwwPath.exists()) {
			System.out.println("Found WWW Directory. Scanning...");
			filesInDirectory = wwwPath.list();
			for (int i = 0; i < filesInDirectory.length; i++) {
				// System.out.println(i);
				System.out.println("Found " + filesInDirectory[i]);
			}
		} else {
			System.out.println("WWW directory does not exist");
			wwwPath.mkdir();
		}

	}

}
