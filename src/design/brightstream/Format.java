package design.brightstream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Format {

	public static String checkInput(String input) {

		Pattern p = Pattern.compile("((https:\\/\\/soundcloud\\.com)((?:\\/[\\w\\.\\-]+)+)((?:\\/[\\w\\.\\-]+)+))",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(input);

		if (m.find()) {
			return m.group(1).toString();
		}

		return "";
	}

	public static String safeForDir(String input) {

		input = input.replaceAll("[^a-zA-Z0-9\\s.-]", "_");

		return input;

	}

	public static String help(int numberOfCommands) {
		try {
			JSONObject object = (JSONObject) new JSONParser().parse(getHelpJson());
			JSONArray array = (JSONArray) new JSONParser().parse(object.get("commands").toString());

			String[][] commands = new String[numberOfCommands][4];
			String[] descriptors = { "fullName", "shortName", "description", "usage" };

			String output = "";

			output += "Help: \n";

			for (int objectCount = 0; objectCount < numberOfCommands; objectCount++) {
				output += "\t";
				for (int descriptorCount = 0; descriptorCount < 4; descriptorCount++) {
					JSONObject currentObject = (JSONObject) new JSONParser().parse(array.get(objectCount).toString());
					commands[objectCount][descriptorCount] = currentObject.get(descriptors[descriptorCount]).toString();
					output += descriptors[descriptorCount] + " >> \t" + commands[objectCount][descriptorCount];
					if (descriptorCount < 4)
						output += "\n\t";
				}
				if (objectCount < numberOfCommands - 1)
					output += "\n";
			}

			return output;
		} catch (ParseException e) {
			e.printStackTrace();
			return "Commands.json missing!";
		}
	}

	private static String getHelpJson() {
		return "{\"commands\":[{\"fullName\":\"getTrack\", \"shortName\":\"gt\", \"description\":\"downloads one track\", \"usage\":\"{url}\"},{\"fullName\":\"getTrack -audio\", \"shortName\":\"gt -audio\", \"description\":\"downloads one track, audio only\", \"usage\":\"{url}\"},{\"fullName\":\"getTrack -art\", \"shortName\":\"gt -art\", \"description\":\"downloads one track, art only\", \"usage\":\"{url}\"},{\"fullName\":\"getTracks\", \"shortName\":\"gts\", \"description\":\"downloads more than one track\", \"usage\":\"{url}*{url}*...*{url}\"},{\"fullName\":\"getTracks -audio\", \"shortName\":\"gts -audio\", \"description\":\"downloads more than one track, audio only\", \"usage\":\"{url}*{url}*...*{url}\"},{\"fullName\":\"getTracks -art\", \"shortName\":\"gts -art\", \"description\":\"downloads more than one track, art only\", \"usage\":\"{url}*{url}*...*{url}\"},{\"fullName\":\"getSet\", \"shortName\":\"gs\", \"description\":\"downloads a set of tracks\", \"usage\":\"{url}\"},{\"fullName\":\"getSet -album\", \"shortName\":\"gs -album\", \"description\":\"downloads a set of tracks with mutual album artist, title and artwork meta\", \"usage\":\"{url}\"},{\"fullName\":\"help\", \"shortName\":\"?\", \"description\":\"shows command information\", \"usage\":\"n/a\"},{\"fullName\":\"exit\", \"shortName\":\"e\", \"description\":\"terminates the program\", \"usage\":\"n/a\"},]}";
	}
}