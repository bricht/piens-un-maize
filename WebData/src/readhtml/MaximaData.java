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

	private List<String> urlCategory;
	private List<String> urlList;
	private List<String> products;
	private List<Double> prices;

	// temporary main method for testing
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MaximaData data = new MaximaData();
	}

	public MaximaData() {
		urlCategory = new ArrayList<>();
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

	// TODO: sporta_uzturs has no subcategorys
	// Add special support for it
	private void collectData() throws Exception {
		for (String string : urlList) {
			// TODO: check if URL has multiple pages
			URL url = new URL(string);
			in = new BufferedReader(new InputStreamReader(url.openStream()));

			String inputLine;
			Matcher matcher;
			Pattern namePattern = Pattern.compile("(?<=>)(.+?)(?=</a>)");
			Pattern pricePattern = Pattern
					.compile("(?<=<strong>)(\\d{1,3})(,)(\\d{2})(?= €</strong>)");

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
							if ((inputLine.replaceAll("\\s", ""))
									.equals("<h3>")) {
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
							if ((inputLine.replaceAll("\\s", ""))
									.equals("<tr>")) {
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
	}

	private void collectURLs() throws Exception {
		String homeURL = "https://www.e-maxima.lv";
		// Page to get all the categories and products
		String indexPage = homeURL + "/Produkti/partika_dzerieni.aspx";
		URL url = new URL(indexPage);
		in = new BufferedReader(new InputStreamReader(url.openStream()));

		String inputLine;
		Matcher matcher;
		Pattern categoryPattern = Pattern
				.compile("(?<=(<a href=\"/Produkti/partika_dzerieni))(/[^/]+?)(?=(\\.aspx\">.+?)</a>)");
		// gets category URLs
		while ((inputLine = in.readLine()) != null) {
			// test
			if ((inputLine.replaceAll("\\s", ""))
					.equals("<spanid=\"ctl00_phSiteMapMenu_menuCategory_menuSitemap\">")) {
				while ((inputLine = in.readLine()) != null) {
					// TODO: (?) get product category names
					// into a separate list/class field for database
					if ((inputLine.replaceAll("\\s", "")).equals("</span>")) {
						break;
					}

					matcher = categoryPattern.matcher(inputLine);
					if (matcher.find()) {
						urlCategory.add(matcher.group());
						// temporary output for testing
						System.out.println(matcher.group());
					}
				}
				break;
			}
		}
		in.close();

		// TODO: for each category in urlCategory
		Pattern urlPattern = Pattern.compile("");
		in = new BufferedReader(new InputStreamReader(url.openStream()));
		// gets product URLs (subcategories)
		while ((inputLine = in.readLine()) != null) {
			if ((inputLine.replaceAll("\\s", ""))
					.equals("<spanid=\"ctl00_phSiteMapMenu_menuCategory_menuSitemap\">")) {
				// TODO: (?) get product subcategory names
				// into a separate list/class field for database
				if ((inputLine.replaceAll("\\s", "")).equals("</span>")) {
					break;
				}

				matcher = urlPattern.matcher(inputLine);
				if (matcher.find()) {

				}
			}
		}
		in.close();

		urlList.add("https://www.e-maxima.lv/Produkti/partika_dzerieni/augli_darzeni/augli.aspx");
	}

	// TODO: Method that returns collected data
	// Return type to be determined (special class, list ...)
	// private (returnType) getData () { }

	// https://stackoverflow.com/questions/14865283/proper-git-workflow-scheme-with-multiple-developers-working-on-same-task
	// https://www.atlassian.com/git/tutorials/comparing-workflows#feature-branch-workflow
	// https://stackoverflow.com/questions/4556467/git-pull-or-git-merge-between-master-and-development-branches
}
