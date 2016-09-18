package design.brightstream.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SOK_1_CLIENT {

	String message = "";

	public static void main(String[] args) throws Exception {

		SOK_1_CLIENT CLIENT = new SOK_1_CLIENT();
		CLIENT.run();

	}

	public void run() throws Exception {

		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.print("Send to server: ");
			message = sc.nextLine();

			Socket SOCK = new Socket("localhost", 4441);
			PrintStream PS = new PrintStream(SOCK.getOutputStream());
			PS.println(message);

			InputStreamReader IR = new InputStreamReader(SOCK.getInputStream());
			BufferedReader BR = new BufferedReader(IR);

			String MESSAGE = BR.readLine();
			System.out.println(MESSAGE);
			
			sc.close();
			SOCK.close();
		}
	}
}