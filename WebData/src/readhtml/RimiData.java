package readhtml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class RimiData {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		RimiData data = new RimiData();
	}

	public RimiData() {
		collectURLs();
		try {
			collectData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void collectData() throws Exception {
		// Test URL (tomatoes)
		URL url = new URL("https://app.rimi.lv/products/1179");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		in.close();
	}

	private void collectURLs() {
		// TODO: get all the necessary URLs from app.rimi.lv
	}

	// TODO: Method that returns collected data
	// Return type to be determined (special class, list ...)
	// private (returnType) getData () { }
}
