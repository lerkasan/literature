package com.github.lerkasan.literature.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name = "ItemAttributes", namespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", namespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01", propOrder = { "author",
		"edition", "isbn", "publishDate", "publisher", "title" })
public class AmazonBookAttributes {

	@XmlElement(name = "Author")
	@XmlPath("ItemSearchResponse/Items/Item/ItemAttributes/Author/text()")
	private String[] author;

	@XmlElement(name = "Edition")
	@XmlPath("ItemSearchResponse/Items/Item/ItemAttributes/Edition/text()")
	private String edition;

	@XmlElement(name = "ISBN")
	@XmlPath("ItemSearchResponse/Items/Item/ItemAttributes/ISBN/text()")
	private String isbn;

	@XmlElement(name = "PublicationDate")
	@XmlPath("ItemSearchResponse/Items/Item/ItemAttributes/PublicationDate/text()")
	private String publishDate;

	@XmlElement(name = "Publisher")
	@XmlPath("ItemSearchResponse/Items/Item/ItemAttributes/Publisher/text()")
	private String publisher;

	@XmlElement(name = "Title")
	@XmlPath("ItemSearchResponse/Items/Item/ItemAttributes/Title/text()")
	private String title;

}
