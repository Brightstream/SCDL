package design.brightstream;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

	public static void singleTrackMode(String url) {
		@SuppressWarnings("unused")
		Track t = new Track(url);
	}

	public static void arrayTracksMode(String urls) {

		ArrayList<String> urlList = new ArrayList<String>();
		String currentUrl;
		int lastIndex = 0;

		if (urls.contains("*")) {
			urls = "*" + urls;
			while (lastIndex >= 0) {
				lastIndex = urls.lastIndexOf('*');

				currentUrl = urls.substring(lastIndex + 1).trim();
				if (lastIndex >= 0) {
					urlList.add(currentUrl);
					urls = urls.substring(0, lastIndex).trim();
				}
			}

			for (int ii = urlList.size() - 1; ii >= 0; ii--) {
				urlList.add(ii, checkInput(urlList.get(ii)));
				
				if (urlList.get(ii).isEmpty()) {
					System.out.println("Bad input!\n");
				} else {
					@SuppressWarnings("unused")
					Track t = new Track(urlList.get(ii));
				}
			}
		} else {
			System.out.println("Redirect to Single Track Mode...");
			
			urls = checkInput(urls);
			
			if (urls.isEmpty()) {
				System.out.println("Bad input!\n");
			} else {
				singleTrackMode(urls);
			}
		}
	}

	public static void playlistTracksMode() {
		System.out.println("Mode not yet supported, try again.\n");
	}

	public static void defaultMode(String input) {
		System.out.println("Invalid input '" + input + "', try again.\n");
	}

	public static String checkInput(String input) {

		Pattern p = Pattern.compile("((https:\\/\\/soundcloud\\.com)((?:\\/[\\w\\.\\-]+)+)((?:\\/[\\w\\.\\-]+)+))",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(input);
		
		if (m.find()) {
			return m.group(1).toString();
		}

		return "";
	}
}