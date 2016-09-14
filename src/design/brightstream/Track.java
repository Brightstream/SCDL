package design.brightstream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Track {

	private String trackURL = "", artURL = "", streamURL = "", status = "", location = "",
			trackName = "", trackArtist = "", trackGenre = "", trackYear = "";

	private int trackID = 0;

	public Track(String url) {
		trackURL = url;

		try {
			if (!initialize())
				System.out.println("Error initializing!");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	private boolean initialize() throws UnsupportedEncodingException, IOException, ParseException {

		if (trackID == 0) {

			URL url = new URL("http://api.soundcloud.com/resolve?url=" + trackURL + "&client_id=" + Main.OAuth2);

			String pageContent = "";

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
				for (String line; (line = reader.readLine()) != null;) {
					pageContent += line;
				}
			}

			JSONObject obj = (JSONObject) new JSONParser().parse(pageContent);

			status = (String) obj.get("status");
			location = (String) obj.get("location");

			Matcher m = Pattern.compile(".*?" + "(\\d+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(location);
			if (m.find())
				trackID = Integer.parseInt(m.group(1).toString());

		}

		if (artURL.equals("")) {

			URL url = new URL("http://api.soundcloud.com/tracks/" + trackID + "?client_id=" + Main.OAuth2);

			String pageContent = "";

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
				for (String line; (line = reader.readLine()) != null;) {
					pageContent += line;
				}
			}

			JSONObject obj = (JSONObject) new JSONParser().parse(pageContent);
			JSONObject objUser = (JSONObject) new JSONParser().parse((obj.get("user").toString()));

			System.out.println("Downloading meta...");
			
			artURL = (String) obj.get("artwork_url");
			streamURL = (String) obj.get("stream_url");
			trackGenre = (String) obj.get("genre");
			trackName = (String) obj.get("title");
			trackArtist = (String) objUser.get("username");
			try{
				trackYear = Long.toString((long) obj.get("release_year"));
			} catch(Exception e) {
				System.out.println("obj: Could not find trackYear!");
			}

			Matcher m = Pattern.compile("(large)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(artURL);
			artURL = m.replaceAll("t500x500");

		}

		if (status.equals("302 - Found")) {
			return true;
		} else {
			return false;
		}

	}

	public int getTrackID() {
		return trackID;
	}

	public String getAttribute(String attribute) {
		switch (attribute) {
		case "trackURL":
			return trackURL;
		case "artURL":
			return artURL;
		case "streamURL":
			return streamURL;
		case "status":
			return status;
		case "location":
			return location;
		case "trackName":
			return trackName;
		case "trackArtist":
			return trackArtist;
		case "trackGenre":
			return trackGenre;
		case "trackYear":
			return trackYear;
		default:
			return null;
		}

	}

	public boolean getArt() {

		System.out.println("Downloading graphic...");

		try {
			URL url = new URL(artURL);
			InputStream in = new BufferedInputStream(url.openStream());
			OutputStream out = new BufferedOutputStream(
					new FileOutputStream("~0.temp"));

			for (int i; (i = in.read()) != -1;) {
				out.write(i);
			}

			in.close();
			out.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean getAudio() {

		try {

			System.out.println("Downloading audio...");

			URL url;
			url = new URL("http://soundcloud.r3d-soft.de/download.php?soundcloud=" + trackURL);

			String pageContent = "";

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
				for (String line; (line = reader.readLine()) != null;) {
					pageContent += line;
				}
			}

			//

			Document doc = Jsoup.parse(pageContent);
			Elements content = doc.getElementsByAttributeValue("name", "dlUrl");
			String downloadURL = content.attr("value");

			//

			URLConnection conn = new URL(downloadURL).openConnection();
			InputStream is = conn.getInputStream();

			OutputStream outstream = new FileOutputStream(new File("~1.temp"));
			byte[] buffer = new byte[4096];
			int len;
			while ((len = is.read(buffer)) > 0) {
				outstream.write(buffer, 0, len);
			}
			outstream.close();

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void enactMetaDraw()
			throws UnsupportedTagException, InvalidDataException, IOException, NotSupportedException {

		System.out.println("Applying meta and graphic...");

		Mp3File mp3file = new Mp3File("~1.temp");
		ID3v1 id3v1Tag;
		if (mp3file.hasId3v1Tag()) {
			id3v1Tag = mp3file.getId3v1Tag();
		} else {
			// mp3 does not have an ID3v1 tag, let's create one..
			id3v1Tag = new ID3v1Tag();
			mp3file.setId3v1Tag(id3v1Tag);
		}
		id3v1Tag.setArtist(trackArtist);
		id3v1Tag.setTitle(trackName);
		id3v1Tag.setAlbum(trackName + " - Single");
		id3v1Tag.setGenre(52);
		id3v1Tag.setYear(trackYear);

		ID3v2 id3v2Tag;
		if (mp3file.hasId3v2Tag()) {
			id3v2Tag = mp3file.getId3v2Tag();
		} else {
			id3v2Tag = new ID3v24Tag();
			mp3file.setId3v2Tag(id3v2Tag);
		}
		id3v2Tag.setArtist(trackArtist);
		id3v2Tag.setTitle(trackName);
		id3v2Tag.setAlbum(trackName + " - Single");
		id3v2Tag.setGenre(52);
		id3v2Tag.setYear(trackYear);
		id3v2Tag.setOriginalArtist(trackArtist);
		id3v2Tag.setAlbumArtist(trackArtist);
		
		File f = new File("~0.temp");
		byte[] b = Files.readAllBytes(f.toPath());
		
		id3v2Tag.setAlbumImage(b, "image/ jpeg");

		mp3file.save("a.mp3");

	}
}