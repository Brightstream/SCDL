package design.brightstream;

import java.util.Scanner;

public class Main {

	public static final String OAuth2 = "e28d3961fd0da0fd54aec1b7b5f520f4";

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		boolean runningMenu = true;
		String mode = "";
		
		while(runningMenu) {
			
			System.out.print("Choose a mode ([s]ingle track, [a]rray of tracks, [p]laylist of tracks, [e]xit): ");
			mode = sc.nextLine();
			
			String atrUrl = "";
			
			switch(mode) {
			case "s":
				System.out.print("Single Track Mode - Enter track url: ");
				
				atrUrl = Controller.checkInput(sc.nextLine());
				
				if (atrUrl.isEmpty()) {
					System.out.println("Bad input!\n");
				} else {
					Controller.singleTrackMode(atrUrl);
				}
				
				break;
			case "a":
				System.out.print("Array of Tracks Mode - Enter track urls seperated by asterisks: ");
				
				Controller.arrayTracksMode(sc.nextLine());
				break;
			case "p":
				System.out.print("Warning: ");
				Controller.playlistTracksMode();
				break;
			case "e":
				System.out.println("Exit.");
				runningMenu = false;
				break;
			default:
				Controller.defaultMode(mode);
				break;
			
			}
		}
		
		sc.close();
	}
}