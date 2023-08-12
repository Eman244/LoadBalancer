package me.manny.ntu;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
public class LoadBalanceClient {
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	final int countTill;

	public LoadBalanceClient(final int countFill) {
		this.countTill = countFill;
	}

	public int getNext() throws Exception {
		try {
			socket = new Socket(PropertyKeys.SERVER_HOSTNAME,	PropertyKeys.SERVER_PORT);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: " + PropertyKeys.SERVER_HOSTNAME);
		} catch (IOException e) {
			System.out.println("No I/O");
			throw e;
		}
		out.println("next");
		final int next = Integer.parseInt(in.readLine());
		in.close();
		out.close();
		socket.close();
		if (next < countTill) {
			return next;
		} else {
			System.out.println("Max job Capacity Reached");
			return -1;
		}
	}

	public static void main(String[] args) throws Exception {
		LoadBalanceClient client = new LoadBalanceClient(10);
		System.out.println(client.getNext());
		
	}

}