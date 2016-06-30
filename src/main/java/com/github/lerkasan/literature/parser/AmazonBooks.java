package com.github.lerkasan.literature.parser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlRootElement(name = "Items", namespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01")
@XmlType(name = "", namespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01", propOrder = { "books" })
@XmlAccessorType(XmlAccessType.FIELD)
public class AmazonBooks {

	@XmlElement(name = "Item", type = AmazonBook.class)
	@XmlPath("ItemSearchResponse/Items/Item/text()")
	private List<AmazonBook> books;

	public AmazonBooks() {
		books = new ArrayList<>();
	}

	public List<AmazonBook> getBooks() {
		return books;
	}

	public void setBooks(List<AmazonBook> books) {
		this.books = books;
	}

}