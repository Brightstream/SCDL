package design.brightstream.thread;

import design.brightstream.net.Beacon;

public class RunnableThread extends Thread {

	private String sLocal;
	
	public RunnableThread(String s, int port) {
		sLocal = s;
	}
	
	public void run() {
		
		switch(sLocal) {
		case "server":
			System.out.println("New Thread is Server Thread");
			Beacon.signalServer();
			break;
		case "download-session":
			System.out.println("New Thread is Download Thread");
			
			break;
		default:
			System.out.println("Bad value");
			break;
		}
	}
}