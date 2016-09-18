package design.brightstream.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Beacon {

	private static ServerSocket socket;

	private static Socket connection;
	private static String command = new String();
	private static String responseStr = new String();

	private static int port = 4309;

	private float sUptime = System.currentTimeMillis();

	public Beacon() {
		System.out.println("Start signalServer();");
	}

	public float getUptime() {
		return System.currentTimeMillis() - sUptime;
	}

	public static void signalServer() {

		System.out.println("Signal Server is running.");

		try {
			socket = new ServerSocket(port);

			connection = socket.accept();

			InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
			DataOutputStream response = new DataOutputStream(connection.getOutputStream());
			BufferedReader input = new BufferedReader(inputStream);

			command = input.readLine();
			// System.out.println("The input is" + command);
			response.writeBytes(responseStr);
			response.flush();
			// response.close();

			System.out.println("Running " + System.currentTimeMillis());
		} catch (IOException e) {
			System.out.println("Fail!: " + e.toString());
		}

		System.out.println("Closing...");
		
		System.out.println("Command: " + command);
	}

}
