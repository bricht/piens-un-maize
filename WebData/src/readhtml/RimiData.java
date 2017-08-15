package readhtml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class RimiData {

	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			RimiData data = new RimiData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RimiData() throws Exception {
		// Test URL (tomatoes)
		URL url = new URL("https://app.rimi.lv/products/1179");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		in.close();
	}
}
