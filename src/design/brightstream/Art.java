package design.brightstream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class Art {

	String url = "";

	public Art(String s) {

		url = s;

		url = Format.checkInput(s);

		if (url.isEmpty()) {
			System.out.println("Bad input!\n");
		} else {

			getArt();

		}
	}

	public boolean getArt() {
		String urlS = url;

		try {
			URL url = new URL(urlS);
			InputStream in = new BufferedInputStream(url.openStream());
			OutputStream out = new BufferedOutputStream(new FileOutputStream("img.jpg"));

			for (int i; (i = in.read()) != -1;) {
				out.write(i);
			}

			in.close();
			out.close();

			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
