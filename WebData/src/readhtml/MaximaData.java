package readhtml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaximaData {
	// TODO: (?) boolean field to identify discounts
	private BufferedReader in;

	private List<String> urlCategorys;
	private List<String> urlList;
	private List<String> products;
	private List<Double> prices;

	// temporary main method for testing
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MaximaData data = new MaximaData();
	}

	public MaximaData() {
		urlCategorys = new ArrayList<>();
		urlList = new ArrayList<>();
		products = new ArrayList<>();
		prices = new ArrayList<>();

		try {
			collectURLs();
			// collectData();
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

	// TODO: sporta_uzturs has no subcategorys
	// Add special support for it
	private void readPage(URL url) throws Exception {
		String inputLine;
		Matcher matcher;
		Pattern namePattern = Pattern.compile("(?<=>)(.+?)(?=</a>)");
		Pattern pricePattern = Pattern
				.compile("(?<=<strong>)(\\d{1,3})(,)(\\d{2})(?= €</strong>)");
		
		in = new BufferedReader(new InputStreamReader(url.openStream()));
		while ((inputLine = in.readLine()) != null) {
			boolean isTable = false;
			boolean isContent = false;
			if ((inputLine.replaceAll("\\s", ""))
					.equals("<divid=\"main-content\">")) {
				while ((inputLine = in.readLine()) != null) {
					if (isContent) {
						if ((inputLine.replaceAll("\\s", ""))
								.equals("</tbody></table>")) {
							break;
						}
						if ((inputLine.replaceAll("\\s", "")).equals("<h3>")) {
							inputLine = in.readLine();
							matcher = namePattern.matcher(inputLine);
							matcher.find();
							products.add(matcher.group());
							// temporary output for testing
							System.out.println(matcher.group());
						}
						if ((inputLine.replaceAll("\\s", ""))
								.equals("<pclass=\"guide\">")) {
							inputLine = in.readLine();
							matcher = pricePattern.matcher(inputLine);
							matcher.find();
							prices.add(Double.parseDouble((matcher.group())
									.replaceAll(",", ".")));
							// temporary output for testing
							System.out.println(matcher.group());
						}
					}
					if (isTable) {
						if ((inputLine.replaceAll("\\s", "")).equals("<tr>")) {
							isContent = true;
						}
					}
					if ((inputLine.replaceAll("\\s", ""))
							.equals("<tableclass='datatype1'>")) {
						isTable = true;
					}
				}
				break;
			}
		}
		in.close();
	}

	private void collectURLs() throws Exception {
		String homeURL = "https://www.e-maxima.lv";
		// Page to get all the categories and products
		String indexPage = homeURL + "/Produkti/partika_dzerieni.aspx";
		URL url = new URL(indexPage);
		String inputLine;
		Matcher matcher;
		Pattern urlPattern = Pattern
				.compile("(?<=(<a href=\"))(/Produkti/partika_dzerieni/[^/]+?)(?=(\\.aspx\">.+?</a>))");
		
		// gets category URLs
		in = new BufferedReader(new InputStreamReader(url.openStream()));
		while ((inputLine = in.readLine()) != null) {
			if ((inputLine.replaceAll("\\s", ""))
					.equals("<spanid=\"ctl00_phSiteMapMenu_menuCategory_menuSitemap\">")) {
				while ((inputLine = in.readLine()) != null) {
					// TODO: (?) get product category names
					// into a separate list/class field for database
					if ((inputLine.replaceAll("\\s", "")).equals("</ul></span>")) {
						break;
					}

					matcher = urlPattern.matcher(inputLine);
					if (matcher.find()) {
						urlCategorys.add(matcher.group());
						// temporary output for testing
						System.out.println(matcher.group());
					}
				}
				break;
			}
		}
		in.close();
		
		// TODO: maybe reopen BufferedReader
		// gets product URLs (subcategories) for each category
		for (String category : urlCategorys) {
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			String pattern = "(?<=(<a href=\"))(" + category + "/.+?\\.aspx)(?=(\">.+?</a>))";
			urlPattern = Pattern.compile(pattern);
			while ((inputLine = in.readLine()) != null) {
				if ((inputLine.replaceAll("\\s", ""))
						.equals("<spanid=\"ctl00_phSiteMapMenu_menuCategory_menuSitemap\">")) {
					while ((inputLine = in.readLine()) != null) {
						// TODO: (?) get product subcategory names
						// into a separate list/class field for database
						if ((inputLine.replaceAll("\\s", "")).equals("</ul></span>")) {
							break;
						}
						
						// TODO: remove favorites from urlList
						matcher = urlPattern.matcher(inputLine);
						if (matcher.find()) {
							urlList.add(matcher.group());
							// temporary output for testing
							System.out.println(matcher.group());
						}
					}
					break;
				}
			}
			in.close();
		}
		in.close();
	}
	

	// TODO: Method that returns collected data
	// Return type to be determined (special class, list ...)
	// private (returnType) getData () { }
}
