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
			command = sc.nextLine();

			switch (command) {
			case "getTrack":
			case "gt":
				System.out.print("URL: ");
				new Track(sc.nextLine());
				break;
			case "getTrack -audio":
			case "gt -audio":
				System.out.print("URL: ");
				new Track(sc.nextLine(), "audioOnly");
				break;
			case "getTrack -art":
			case "gt -art":
				System.out.print("URL: ");
				new Track(sc.nextLine(), "artOnly");
				break;
			case "getTracks":
			case "gts":
				System.out.print("URLs: ");
				new TrackArray(sc.nextLine());
				break;
			case "getTracks -audio":
			case "gts -audio":
				System.out.print("URLs: ");
				new TrackArray(sc.nextLine(), "audioOnly");
				break;
			case "getTracks -art":
			case "gts -art":
				System.out.print("URLs: ");
				new TrackArray(sc.nextLine(), "artOnly");
				break;
			case "getPlaylist":
			case "gp":
				System.out.print("URL: ");
				new TrackPlaylist(sc.nextLine(), false);
				break;
			case "getAlbum":
			case "ga":
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