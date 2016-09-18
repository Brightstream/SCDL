package design.brightstream;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TrackPlaylist {

	private int playlistID = 0;

	private String artUrl = "", albumTitle = "", albumArtist = "";

	private List trackIDs = new List(), trackURLs = new List();

	public TrackPlaylist(String inputUrl, boolean albumMode) {
		try {
			getId(inputUrl);
			getTrackInfo();

			String[] tracks = trackIDs.getItems();
			int[] tracksIDs = new int[tracks.length];

			String[] tracksURLs = trackURLs.getItems();

			for (int i = 0; i < tracks.length; i++) {
				tracksIDs[i] = Integer.parseInt(tracks[i]);
			}

			new TrackArray(tracksIDs, albumMode, artUrl, albumTitle, albumArtist, tracksURLs);
		} catch (IOException | ParseException e) {
		}
	}

	private void getTrackInfo() throws MalformedURLException {
		if (artUrl.equals("")) {

			URL url = new URL("http://api.soundcloud.com/playlists/" + playlistID + "?client_id=" + Main.OAuth2);

			String pageContent = "";

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
				for (String line; (line = reader.readLine()) != null;) {
					pageContent += line;
				}
			} catch (Exception e) {
			}

			try {
				JSONObject obj = null, objUser = null, objTrack = null;
				JSONArray objTracks = null;

				if (!pageContent.isEmpty()) {

					obj = (JSONObject) new JSONParser().parse(pageContent);
					objUser = (JSONObject) new JSONParser().parse(obj.get("user").toString());

					objTracks = (JSONArray) new JSONParser().parse(obj.get("tracks").toString());

					artUrl = (String) obj.get("artwork_url");
					albumTitle = (String) obj.get("title");
					albumArtist = (String) objUser.get("username");

					for (int i = 0; i < objTracks.size(); i++) {
						String trackID = "", trackURL = "";
						objTrack = (JSONObject) new JSONParser().parse(objTracks.get(i).toString());

						trackID = objTrack.get("id").toString();

						trackIDs.add(trackID);

						trackURL = objTrack.get("permalink_url").toString();

						trackURLs.add(trackURL);

					}
				} else {
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Matcher m = Pattern.compile("(large)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(artUrl);
				artUrl = m.replaceAll("t500x500");
			} catch (Exception e) {
			}
		}
	}

	private void getId(String inputUrl) throws UnsupportedEncodingException, IOException, ParseException {
		if (playlistID == 0) {

			URL url = new URL("http://api.soundcloud.com/resolve?url=" + inputUrl + "&client_id=" + Main.OAuth2);

			String pageContent = "";

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
				for (String line; (line = reader.readLine()) != null;) {
					pageContent += line;
				}
			}

			JSONObject obj = (JSONObject) new JSONParser().parse(pageContent);

			String location = (String) obj.get("location");

			Matcher m = Pattern.compile(".*?" + "(\\d+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(location);

			if (m.find())
				playlistID = Integer.parseInt(m.group(1).toString());
		}
	}

}
