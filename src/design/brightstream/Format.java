package design.brightstream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
			File f = new File("src/design/brightstream/Commands.json");

			String content = "";

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
				for (String line; (line = reader.readLine()) != null;) {
					content += line;
				}
			} catch (IOException e) {
			}

			JSONObject object = (JSONObject) new JSONParser().parse(content);
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
			return "Commands.json missing!";
		}
	}
}