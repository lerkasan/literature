package com.github.lerkasan.literature.parser.impl.amazon;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.github.lerkasan.literature.parser.AmazonBooks;

@XmlRootElement(name = "ItemSearchResponse", namespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemSearchResponse {

	@XmlElement(name = "ItemSearchResponse")
	private AmazonBooks items = new AmazonBooks();

	public ItemSearchResponse() {
	}

	public AmazonBooks getItems() {
		return items;
	}

	public void setItems(AmazonBooks items) {
		this.items = items;
	}
	
}
