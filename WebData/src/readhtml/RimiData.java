package readhtml;

import java.net.URL;

public class RimiData {

	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			RimiData data = new RimiData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public RimiData() throws Exception {
		// Test URL (tomatoes)
		URL url = new URL("https://app.rimi.lv/products/1179");
	}
}
