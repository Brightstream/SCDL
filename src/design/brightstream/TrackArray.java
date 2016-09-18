package design.brightstream;

import java.util.ArrayList;

public class TrackArray {

	private String urlsLocal, argsLocal;

	public TrackArray(String urls) {

		urlsLocal = urls;
		arrayTracksMode(urlsLocal, "");

	}

	public TrackArray(String urls, String args) {
		urlsLocal = urls;
		argsLocal = args;
		arrayTracksMode(urlsLocal, argsLocal);
	}

	public TrackArray(int[] ids, boolean albumMode, String artUrl, String albumTitle, String albumArtist, String[] urls) {
		if (albumMode) {
			for (int i = 0; i < ids.length; i++) {
				new Track(ids[i], artUrl, albumTitle, albumArtist, i+1, urls.length, urls[i]);
			}
		} else {
			for (int i = 0; i < ids.length; i++) {
				new Track(ids[i], urls[i]);
			}
		}
	}

	private static void arrayTracksMode(String urls, String args) {

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
				urlList.add(ii, Format.checkInput(urlList.get(ii)));

				if (urlList.get(ii).isEmpty()) {
					System.out.println("Bad input!\n");
				} else {
					switch (args) {
					case "audioOnly":
						new Track(urlList.get(ii), "audioOnly");
						break;
					case "artOnly":
						new Track(urlList.get(ii), "artOnly");
						break;
					default:
						new Track(urlList.get(ii));
						break;
					}
				}
			}
		} else {
			switch (args) {
			case "audioOnly":
				new Track(urls, "audioOnly");
				break;
			case "artOnly":
				new Track(urls, "artOnly");
				break;
			default:
				new Track(urls);
				break;
			}
		}
	}
}