package readhtml;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaximaData {

	private List<String> products;
	private List<Double> prices;
	private List<String> url;

	// temporary main method for testing
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MaximaData data = new MaximaData();
	}

	public MaximaData() {
		products = new ArrayList<>();
		prices = new ArrayList<>();
		url = new ArrayList<>();

		collectURLs();
		try {
			collectData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void collectData() throws Exception {
		// TODO: for each URL in List url ...
		// Test URL
		URL url = new URL(
				"https://www.e-maxima.lv/Produkti/partika_dzerieni/augli_darzeni/salati_garsaugi/svaigi_salati.aspx");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));

		String inputLine;
		Pattern namePattern = Pattern.compile("(?<=>)(.+?)(?=</a>)");
		Pattern pricePattern = Pattern
				.compile("(?<=<strong>)(\\d{1,3})(,)(\\d{2})(?= €</strong>)");
		Matcher matcher;

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

	private void collectURLs() {
		// TODO: get all the necessary URLs from e-Maxima
	}

	// TODO: Method that returns collected data
	// Return type to be determined (special class, list ...)
	// private (returnType) getData () { }
	
	// https://stackoverflow.com/questions/14865283/proper-git-workflow-scheme-with-multiple-developers-working-on-same-task
	// https://www.atlassian.com/git/tutorials/comparing-workflows#feature-branch-workflow
	// https://stackoverflow.com/questions/4556467/git-pull-or-git-merge-between-master-and-development-branches
}
