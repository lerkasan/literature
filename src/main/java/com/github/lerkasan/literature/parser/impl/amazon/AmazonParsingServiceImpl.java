package com.github.lerkasan.literature.parser.impl.amazon;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.lerkasan.literature.parser.AmazonBook;
import com.github.lerkasan.literature.parser.AmazonBooks;
import com.github.lerkasan.literature.parser.AmazonItem;
import com.github.lerkasan.literature.parser.ParsingService;
import com.github.lerkasan.literature.parser.impl.ParsingServiceImpl;

@Service("AmazonParsingService")
public class AmazonParsingServiceImpl extends ParsingServiceImpl implements ParsingService {

	public List<AmazonItem> parse(String url) {
		int nodesSize;
		JAXBContext jc;
		List<AmazonItem> books = new ArrayList<>();
		ItemSearchResponse foundItems = new ItemSearchResponse();
		try {
			jc = JAXBContext.newInstance(ItemSearchResponse.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			URL xmlURL = new URL(url);
			InputStream xml = xmlURL.openStream();
			xml.close();

		} catch (JAXBException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(url);
			NodeList nodes = doc.getElementsByTagName("Item");
			NodeList nodesTitle = doc.getElementsByTagName("Title");
			NodeList nodesImg = doc.getElementsByTagName("MediumImage");
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
				}
				Node image = nodes.item(i);
				if (image != null) {
					// book.setImageUrl(image.getFirstChild().getTextContent());
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
		// return foundItems.getItems().getBooks();
		return books;

	}
}

/*
 * JAXBContext context = JAXBContext.newInstance(AmazonItem.class); Unmarshaller
 * unMarshaller = context.createUnmarshaller(); AmazonItem newItem =
 * (AmazonItem) unMarshaller.unmarshal(response); System.out.println(newItem);
 */
