package me.manny.ntu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalanceServer {
	ServerSocket server = null;
	public static final AtomicInteger next = new AtomicInteger(0);
	long last;
	long n = 0;

	public void listenSocket() {
		try {
			server = new ServerSocket(PropertyKeys.SERVER_PORT);
		} catch (IOException e) {
			System.out.println("Could not listen on port " + PropertyKeys.SERVER_PORT);
			System.exit(-1);
		}
		while (true) {
			ClientWorker w;
			try {
				w = new ClientWorker(server.accept());
				final long now = System.currentTimeMillis();
				if (now - last > TimeUnit.MINUTES.toMillis(1l)) {
					last = now;
					System.out.println("Processed: " + next.get());
				}
				if(next.get()<10)
				System.out.println("Processed: " + n);
				
				n++;
				Thread t = new Thread(w);
				t.start();
			} catch (IOException e) {
				System.out
						.println("Accept failed: " + PropertyKeys.SERVER_PORT);
				System.exit(-1);
			}
		}
	}

	protected void finalize() {
		try {
			server.close();
		} catch (IOException e) {
			System.out.println("Could not close socket");
			System.exit(-1);
		}
	}

	class ClientWorker implements Runnable {
		private Socket client;

		ClientWorker(Socket client) {
			this.client = client;
		}

		public void run() {
			String line;
			BufferedReader in = null;
			PrintWriter out = null;

			try {
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				out = new PrintWriter(client.getOutputStream(), true);
			} catch (IOException e) {
				System.out.println("in or out failed");
				System.exit(-1);
			}
			try {
				line = in.readLine();
				if (line.compareTo("next") == 0) {
					final int next = LoadBalanceServer.next.getAndIncrement();
					if(next<10)
					System.out.println(client.getInetAddress() + " working on " + next);
					else  System.out.println("Max job Capacity Reached");
					out.println(Integer.toString(next));
				}
				in.close();
				out.close();
			} catch (IOException e) {
				System.out.println("Read failed");
				System.exit(-1);
			}
		}
	}

	public static void main(String[] args) {
		LoadBalanceServer server = new LoadBalanceServer();
		server.listenSocket();
	}

}