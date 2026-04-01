package com.ptstanek.pshttp;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import com.github.lalyos.jfiglet.*;

// TODO: Sort out exceptions
//		 and write some unit tests.

public class App {
	static final int SERVER_PORT = 8080;
	static Thread serverThread;
	static HttpServer srv;
	static Scanner scn = new Scanner(System.in);

	public static void main(String[] args) throws IOException, InterruptedException {
		String splashText = FigletFont.convertOneLine("pshttp");
		System.out.println(splashText);

		srv = new HttpServer(SERVER_PORT);
		Thread serverThread = new Thread(srv);
		serverThread.start();

		// CLI interface stuff
		System.out.println("COMMANDS: exit");

		String command;

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}

		do { // command loop. this kinda works
			System.out.print("> ");
			command = scn.next().trim().toLowerCase();

			if (command.equals("help")) {
				System.out.println("[LIST OF COMMANDS]");
			} else if (command.equals("exit")) {
				System.out.println("Exiting...");
			} else {
				System.out.println("invalid command");
			}
		} while (!command.equals("exit"));

		srv.stop();
		scn.close();
		serverThread.join();
	}
}
