package com.github.lerkasan.literature;

public class Main {
	/*
	 * @Inject static RssService rssService;
	 * 
	 * @Inject static ResourceRepository rssFeed;
	 */

	// public static void main(String[] args) {
	/*
	 * RssService rssService = new RssServiceImpl(); ResourceService resource =
	 * new ResourceServiceImpl(); Resource dzone = resource.getByName("DZone");
	 * if (dzone != null) { List<SyndEntry> rssNews = rssService.read(dzone);
	 * for (SyndEntry entry : rssNews) { System.out.println(entry.getLink()); }
	 * } else { System.out.println("null"); }
	 */
	// ApplicationContext context = new
	// ClassPathXmlApplicationContext("beans.xml");

	// AccessTypeService accessTypeService =
	// context.getBean("AccessTypeServiceImpl", AccessTypeService.class);
	// accessTypeService.findByName("nina");

	// SpringerApiRequestServiceImpl apiRequest = new
	// SpringerApiRequestServiceImpl();
	// String response =
	// apiRequest.request("http://api.springer.com/openaccess/json?q=subject:\"Computer
	// Science\" AND (title:\"data\" OR keyword:\"machine learning\" OR
	// keyword:\"cloud
	// computing\")&p=100&api_key=my_key");
	/*
	 * String response =
	 * apiRequest.request("http://api.springer.com/openaccess/json");
	 * SpringerParsingServiceImpl parser = new SpringerParsingServiceImpl();
	 * parser.parse(response);
	 * 
	 * ArxivParsingServiceImpl t = new ArxivParsingServiceImpl(); t.parse();
	 */

	/*
	 * Resource crossrefApi = new Resource(); crossrefApi.setId(1);
	 * crossrefApi.setName("Crossref");
	 * crossrefApi.setDomain("api.crossref.org");
	 * crossrefApi.setUrl("http://api.crossref.org/works");
	 * crossrefApi.setParameterFormat("query=%s");
	 * crossrefApi.setResponseFormat("json");
	 */
	/*
	 * RssReader rss = new RssReader(); rss.read(crossrefApi);
	 * System.out.println("________________________");
	 */
	/*
	 * crossrefApi.setName("Google");
	 * crossrefApi.setDomain("www.googleapis.com");
	 * crossrefApi.setUrl("https://www.googleapis.com/customsearch/v1");
	 * crossrefApi.setParameterFormat("q=%s&cx=%s&key=%s");
	 * crossrefApi.setResponseFormat("json"); crossrefApi.setApiKey("my_key");
	 * crossrefApi.setSearchEngineKey("my_key");
	 */
	// CrossrefParsingServiceImpl parser = new CrossrefParsingServiceImpl();
	// ApiRequestServiceImpl requester = new ApiRequestServiceImpl();
	// String result = "";
	// String result = requester.passRequestToApi(crossrefApi,
	// "Java+Spring");
	// parser.parse(result);
	/*
	 * try (PrintWriter outputStream = new PrintWriter(new
	 * FileWriter("result.txt"))) { outputStream.print(result); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }
	 */
	// System.out.println(result);
	// parser.parse(result);

	/*
	 * StringBuffer sb = new StringBuffer(); try (BufferedReader inputStream =
	 * new BufferedReader(new FileReader("result.txt"))) { String l; while ((l =
	 * inputStream.readLine()) != null) { sb.append(l); } } catch
	 * (FileNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * parser.parse(sb.toString());
	 */

	// }

}
