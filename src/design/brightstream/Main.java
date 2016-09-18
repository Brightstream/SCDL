package design.brightstream;

import java.util.Scanner;

import design.brightstream.thread.RunnableThread;

public class Main {

	public static final String OAuth2 = "e28d3961fd0da0fd54aec1b7b5f520f4";

	public static void main(String[] args) {

		Controller.menu();
	}

	public static void startWithThreads() {

		RunnableThread t = new RunnableThread("server", 4903);
		t.start();

		Scanner sc = new Scanner(System.in);
		String consoleInput = sc.nextLine();
		System.out.println("Parsing... " + consoleInput);

		sc.close();
	}
}