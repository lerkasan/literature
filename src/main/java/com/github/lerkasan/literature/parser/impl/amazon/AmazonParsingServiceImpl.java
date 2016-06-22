package com.github.lerkasan.literature.parser.impl.amazon;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.github.lerkasan.literature.parser.AmazonBook;
import com.github.lerkasan.literature.parser.AmazonBooks;
import com.github.lerkasan.literature.parser.ParsingService;

@Service("AmazonParsingService")
public class AmazonParsingServiceImpl implements ParsingService {

	public List<AmazonBook> parse(String url) {
		JAXBContext jc;
		ItemSearchResponse foundItems = new ItemSearchResponse();
		try {
			jc = JAXBContext.newInstance(ItemSearchResponse.class);
			// System.out.println("EXCEPTION URL " + url);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			URL xmlURL = new URL(url);
			InputStream xml = xmlURL.openStream();
			foundItems = (ItemSearchResponse) unmarshaller.unmarshal(xml);
			// System.out.println("MOXy
			// "+foundItems.getItems().getBooks().get(1));
			xml.close();

		} catch (JAXBException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// -------------

		String title = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(url);
			// NodeList nodes = doc.getElementsByTagName("Item");
			int nodesSize;
			/*
			 * for (int i = 0; i < nodesSize; i++) { title =
			 * nodes.item(i).getTextContent(); }
			 */
			// System.out.println(" NodeValue:" +
			// nodes.item(0).getFirstChild().getNextSibling().getNodeValue().toString());//.getNamedItem("Title").getTextContent());
			System.out.println();
			// System.out.println(" URL:" +
			// nodes.item(0).getFirstChild().getNextSibling().getTextContent().toString());
			// System.out.println(" IMG:" +
			// nodes.item(0).getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getTextContent().toString());

			NodeList nodes = doc.getElementsByTagName("Item");
			NodeList nodesTitle = doc.getElementsByTagName("Title");
			NodeList nodesImg = doc.getElementsByTagName("MediumImage");
			NodeList nodesIsbn = doc.getElementsByTagName("ISBN");
			NodeList nodesPublisher = doc.getElementsByTagName("Publisher");
			NodeList nodesPublicationDate = doc.getElementsByTagName("PublicationDate");
			NodeList nodesUrl = doc.getElementsByTagName("DetailPageURL");
			nodesSize = nodesTitle.getLength();
			System.out.println("AMAZON RESULTS: ");
			for (int i = 0; i < nodesSize; i++) {
					System.out.println(nodesTitle.item(i).getTextContent());
					System.out.println(" Author:" + nodes.item(i).getFirstChild().getNextSibling().getNextSibling()
							.getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling()
							.getNextSibling().getFirstChild().getTextContent());
					System.out.println(nodesImg.item(i).getFirstChild().getTextContent());
				//	System.out.println(nodesIsbn.item(i).getTextContent());
					System.out.println(nodesPublisher.item(i).getTextContent());
					System.out.println(nodesPublicationDate.item(i).getTextContent());
					System.out.println(nodesUrl.item(i).getTextContent());
				System.out.println();

			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		// --------------

		return foundItems.getItems().getBooks();

	}
}

/*
 * JAXBContext context = JAXBContext.newInstance(AmazonItem.class); Unmarshaller
 * unMarshaller = context.createUnmarshaller(); AmazonItem newItem =
 * (AmazonItem) unMarshaller.unmarshal(response); System.out.println(newItem);
 */
