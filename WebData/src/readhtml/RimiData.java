package readhtml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RimiData {

	private List<String> products;
	private List<Double> prices;
	private List<String> urlList;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		RimiData data = new RimiData();
	}

	public RimiData() {
		products = new ArrayList<>();
		prices = new ArrayList<>();
		urlList = new ArrayList<>();

		try {
			collectURLs();
			collectData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void collectData() throws Exception {
		for (String string : urlList) {
			URL url = new URL(string);
			// 1. check if product has multiple pages
			// 2. if it has, get the amount
			// 3. create a new URL with page number for each page in a for loop
			// 4. call readPage with the new URL in the loop
			// 5. if product doesnt have multiple pages, call readPage with URL
			// from urlList
			readPage(url);
		}
	}

	private void readPage(URL url) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String inputLine;
		Pattern namePattern = Pattern.compile("(?<=name\">)(.+?)(?=</div>)");
		Pattern wholePricePattern = Pattern
				.compile("(?<=whole-number \">)(\\d{1,3})(?=</span>)");
		Pattern decimalPricePattern = Pattern
				.compile("(?<=decimal\">)(\\d{2})(?=</span>)");
		Matcher matcher;

		while ((inputLine = in.readLine()) != null) {
			boolean isTable = false;
			boolean isContent = false;
			boolean priceBubble = false;
			String wholePrice;
			String decimalPrice;
			String allPrice;
			if ((inputLine.replaceAll("\\s", ""))
					.equals("<divclass=\"products-shelfjs-products-shelf\">")) {
				while ((inputLine = in.readLine()) != null) {
					if (isContent) {
						if ((inputLine.replaceAll("\\s", "")).equals("</ul>")) {
							break;
						}
						if ((inputLine.replaceAll("\\s", ""))
								.equals("<divclass=\"inforelativeclear\">")) {
							inputLine = in.readLine();
							matcher = namePattern.matcher(inputLine);
							matcher.find();
							products.add(matcher.group());
							// temporary output for testing
							System.out.println(matcher.group());
						}
						if ((inputLine.replaceAll("\\s", ""))
								.equals("<divclass=\"price-bubble\">")) {
							priceBubble = true;
						}
						if ((inputLine.replaceAll("\\s", ""))
								.equals("<divclass=\"price\">")) {
							if (priceBubble) {
								priceBubble = false;
								continue;
							}
							inputLine = in.readLine();
							matcher = wholePricePattern.matcher(inputLine);
							matcher.find();
							wholePrice = matcher.group();
							inputLine = in.readLine();
							matcher = decimalPricePattern.matcher(inputLine);
							matcher.find();
							decimalPrice = matcher.group();
							allPrice = wholePrice + "." + decimalPrice;
							prices.add(Double.parseDouble(allPrice));
							// temporary output for testing
							System.out.println(allPrice);
						}
					}
					if (isTable) {
						if ((inputLine.replaceAll("\\s", ""))
								.equals("<liclass=\"relativeitemeffectfade-shadowjs-shelf-itemshelf-item\"data-ads=\"true\"")
								|| (inputLine.replaceAll("\\s", ""))
										.equals("<liclass=\"relativeitemeffectfade-shadowjs-shelf-itemshelf-item\"")) {
							isContent = true;
						}
					}
					if ((inputLine.replaceAll("\\s", ""))
							.equals("<ulclass=\"shelfjs-shelfclearclearfix\">")) {
						isTable = true;
					}
				}
				break;
			}
		}

		in.close();
	}

	private void collectURLs() {
		// TODO: get all the necessary URLs from app.rimi.lv
		urlList.add("https://app.rimi.lv/products/793");
	}

	// TODO: Method that returns collected data
	// Return type to be determined (special class, list ...)
	// private (returnType) getData () { }
}
