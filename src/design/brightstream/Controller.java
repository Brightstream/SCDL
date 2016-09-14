package design.brightstream;

import java.util.ArrayList;

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
				@SuppressWarnings("unused")
				Track t = new Track(urlList.get(ii));
			}
		} else {
			System.out.println("Redirect to Single Track Mode...");
			singleTrackMode(urls);
		}
	}

	public static void playlistTracksMode() {
		System.out.println("Mode not yet supported, try again.\n");
	}

	public static void defaultMode(String input) {
		System.out.println("Invalid input '" + input + "', try again.\n");
	}
}
