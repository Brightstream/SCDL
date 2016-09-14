package design.brightstream;

import java.io.IOException;
import java.util.Scanner;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Main {

	static String url = "";
	public static final String OAuth2 = "fb6c65015f940bc6f475451fae16b1a9";

	public static void main(String[] args) {

		System.out.print("Enter a url: ");

		Scanner sc = new Scanner(System.in);
		url = sc.next();
		sc.close();

		Track t = new Track(url);
		t.getArt();
		t.getAudio();
		
		try {
			t.enactMetaDraw();
		} catch (UnsupportedTagException | InvalidDataException | NotSupportedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}