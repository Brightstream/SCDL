package design.brightstream.thread;

public class DownloadThread extends Thread {

	private String sLocal;
	
	public DownloadThread(String s) {
		sLocal = s;
	}
	
	public void run() {
		System.out.println(sLocal);
	}
}