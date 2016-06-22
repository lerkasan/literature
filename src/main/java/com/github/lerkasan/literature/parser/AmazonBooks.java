package com.github.lerkasan.literature.parser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Item", namespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01")
@XmlAccessorType(XmlAccessType.FIELD)
public class AmazonBooks {

	@XmlElement(name = "Items", type = AmazonBook.class)
	private List<AmazonBook> books = new ArrayList<AmazonBook>();

	public AmazonBooks() {
	}

	public List<AmazonBook> getBooks() {
		return books;
	}

	public void setBooks(List<AmazonBook> books) {
		this.books = books;
	}

}