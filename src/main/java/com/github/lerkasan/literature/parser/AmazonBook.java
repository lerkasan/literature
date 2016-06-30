package com.github.lerkasan.literature.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name = "Item", namespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", namespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01", propOrder = { "url",
		"imageUrl", "attributes" })
public class AmazonBook {

	public AmazonBook() {
	}

	@XmlElement(name = "DetailPageURL")
	@XmlPath("ItemSearchResponse/Items/Item/DetailPageURL/text()")
	private String url;

	@XmlElement(name = "MediumImage")
	@XmlPath("ItemSearchResponse/Items/Item/MediumImage/text()")
	private String imageUrl;

	@XmlElement(name = "ItemAttributes", type = AmazonBookAttributes.class)
	@XmlPath("ItemSearchResponse/Items/Item/ItemAttributes/text()")
	AmazonBookAttributes attributes;

	/*
	 * @XmlElement(name = "Author")
	 * 
	 * @XmlPath("ItemSearchResponse/Items/Item/ItemAttributes/Author/text()")
	 * private String[] author;
	 * 
	 * @XmlElement(name = "Edition")
	 * 
	 * @XmlPath("ItemSearchResponse/Items/Item/ItemAttributes/Edition/text()")
	 * private String edition;
	 * 
	 * @XmlElement(name = "ISBN")
	 * 
	 * @XmlPath("ItemSearchResponse/Items/Item/ItemAttributes/ISBN/text()")
	 * private String isbn;
	 * 
	 * @XmlElement(name = "PublicationDate")
	 * 
	 * @XmlPath(
	 * "ItemSearchResponse/Items/Item/ItemAttributes/PublicationDate/text()")
	 * private String publishDate;
	 * 
	 * @XmlElement(name = "Publisher")
	 * 
	 * @XmlPath("ItemSearchResponse/Items/Item/ItemAttributes/Publisher/text()")
	 * private String publisher;
	 * 
	 * @XmlElement(name = "Title")
	 * 
	 * @XmlPath("ItemSearchResponse/Items/Item/ItemAttributes/Title/text()")
	 * private String title;
	 */

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public AmazonBookAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(AmazonBookAttributes attributes) {
		this.attributes = attributes;
	}

	/*
	 * public String[] getAuthor() { return author; }
	 * 
	 * public void setAuthor(String[] author) { this.author = author; }
	 * 
	 * public String getEdition() { return edition; }
	 * 
	 * public void setEdition(String edition) { this.edition = edition; }
	 * 
	 * public String getIsbn() { return isbn; }
	 * 
	 * public void setIsbn(String isbn) { this.isbn = isbn; }
	 * 
	 * public String getPublishDate() { return publishDate; }
	 * 
	 * public void setPublishDate(String publishDate) { this.publishDate =
	 * publishDate; }
	 * 
	 * public String getPublisher() { return publisher; }
	 * 
	 * public void setPublisher(String publisher) { this.publisher = publisher;
	 * }
	 * 
	 * public String getTitle() { return title; }
	 * 
	 * public void setTitle(String title) { this.title = title; }
	 * 
	 * @Override public String toString() { return "AmazonBook [url=" + url +
	 * ", imageUrl=" + imageUrl + ", author=" + Arrays.toString(author) +
	 * ", edition=" + edition + ", isbn=" + isbn + ", publishDate=" +
	 * publishDate + ", publisher=" + publisher + ", title=" + title + "]"; }
	 */

}
