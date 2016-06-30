package com.github.lerkasan.literature.parser.impl.amazon;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.lerkasan.literature.parser.AmazonItem;
import com.github.lerkasan.literature.parser.ParsingService;
import com.github.lerkasan.literature.parser.impl.ParsingServiceImpl;

@Service("AmazonParsingService")
public class AmazonParsingServiceImpl extends ParsingServiceImpl implements ParsingService {

	public List<AmazonItem> parse(String url) {
		int nodesSize;
		List<AmazonItem> books = new ArrayList<>();

		/*
		 * JAXBContext jc; ItemSearchResponse itemSearchResponse; StringBuilder
		 * response = new StringBuilder(); try { jc =
		 * JAXBContext.newInstance(ItemSearchResponse.class); Unmarshaller
		 * unmarshaller = jc.createUnmarshaller(); URL xmlUrl = new URL(url);
		 * try (BufferedReader in = new BufferedReader(new
		 * InputStreamReader(xmlUrl.openStream())); PrintWriter out = new
		 * PrintWriter(new FileWriter("/home/lerkasan/bionic/test.xml"))) {
		 * String inputLine; while ((inputLine = in.readLine()) != null) {
		 * out.write(inputLine); //response.append(inputLine); }
		 * 
		 * } //itemSearchResponse = (ItemSearchResponse)
		 * unmarshaller.unmarshal(xmlUrl); itemSearchResponse =
		 * (ItemSearchResponse) unmarshaller.unmarshal(new
		 * File("/home/lerkasan/bionic/test.xml")); //Doesn't work !!!! returns
		 * empty object ((( System.out.println("Amazon xml results:"); //
		 * System.out.println(itemSearchResponse.getItems().get(0).getTitle());
		 * 
		 * } catch (JAXBException | MalformedURLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(url);
			NodeList nodes = doc.getElementsByTagName("Item");
			NodeList nodesTitle = doc.getElementsByTagName("Title");
			// NodeList nodesImg = doc.getElementsByTagName("MediumImage");
			NodeList nodesIsbn = doc.getElementsByTagName("ISBN");
			NodeList nodesPublisher = doc.getElementsByTagName("Publisher");
			NodeList nodesPublicationDate = doc.getElementsByTagName("PublicationDate");
			NodeList nodesUrl = doc.getElementsByTagName("DetailPageURL");
			nodesSize = nodesTitle.getLength();
			for (int i = 0; i < nodesSize; i++) {
				AmazonItem book = new AmazonItem();
				Node title = nodesTitle.item(i);
				if (title != null) {
					book.setTitle(title.getTextContent());
				}
				Node author = nodes.item(i);
				if (author != null) {
					String authorStr = author.getFirstChild().getNextSibling().getNextSibling().getNextSibling()
							.getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling()
							.getFirstChild().getTextContent();
					if (!authorStr.contains("Product Description")) {
						book.setAuthor(authorStr);
					}
					if (authorStr.contains("USD")) {
						authorStr = author.getFirstChild().getNextSibling().getNextSibling().getNextSibling()
								.getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild()
								.getTextContent();
						book.setAuthor(authorStr);
					}
				}
				Node image = nodes.item(i);
				if (image != null) {
					book.setImageUrl(image.getFirstChild().getNextSibling().getNextSibling().getNextSibling()
							.getNextSibling().getNextSibling().getFirstChild().getTextContent());
				}
				Node isbn = nodesIsbn.item(i);
				if (isbn != null) {
					book.setIsbn(isbn.getTextContent());
				}
				Node publisher = nodesPublisher.item(i);
				if (publisher != null) {
					book.setPublisher(publisher.getTextContent());
				}
				Node publicationDate = nodesPublicationDate.item(i);
				if (publicationDate != null) {
					book.setPublicationDate(publicationDate.getTextContent());
				}
				Node itemUrl = nodesUrl.item(i);
				if (itemUrl != null) {
					book.setItemUrl(itemUrl.getTextContent());
				}
				books.add(book);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return books;

	}
}
