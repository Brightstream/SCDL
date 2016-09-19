package design.brightstream;

import java.util.Scanner;

public class Controller {

	public static void menu() {

		Scanner sc = new Scanner(System.in);

		boolean menuRunning = true;
		String command = "";

		System.out.println("Command line prompt started.");

		while (menuRunning) {
			
			System.out.print("\nCommand: ");
			command = sc.nextLine().toLowerCase();

			switch (command) {
			case "gettrack":
			case "gt":
				System.out.print("URL: ");
				new Track(sc.nextLine());
				break;
			case "gettrack -audio":
			case "gt -audio":
				System.out.print("URL: ");
				new Track(sc.nextLine(), "audioOnly");
				break;
			case "gettrack -art":
			case "gt -art":
				System.out.print("URL: ");
				new Track(sc.nextLine(), "artOnly");
				break;
			case "gettracks":
			case "gts":
				System.out.print("URLs: ");
				new TrackArray(sc.nextLine());
				break;
			case "gettracks -audio":
			case "gts -audio":
				System.out.print("URLs: ");
				new TrackArray(sc.nextLine(), "audioOnly");
				break;
			case "gettracks -art":
			case "gts -art":
				System.out.print("URLs: ");
				new TrackArray(sc.nextLine(), "artOnly");
				break;
			case "getset":
			case "gs":
				System.out.print("URL: ");
				new TrackPlaylist(sc.nextLine(), false);
				break;
			case "getset -album":
			case "gs -album":
				System.out.print("URL: ");
				new TrackPlaylist(sc.nextLine(), true);
				break;
			case "help":
			case "?":
				System.out.print(Format.help(10));
				break;
			case "exit":
			case "e":
				System.out.println("Exit.");
				menuRunning = false;
				break;
			default:
				System.out.println("Bad command!");
				break;
			}
		}

		sc.close();
	}
}