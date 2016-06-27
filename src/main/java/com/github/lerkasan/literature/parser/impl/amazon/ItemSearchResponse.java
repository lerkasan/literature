package com.github.lerkasan.literature.parser.impl.amazon;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import com.github.lerkasan.literature.parser.AmazonBook;


@XmlRootElement(name = "ItemSearchResponse", namespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01")
@XmlType(name = "", namespace = "http://webservices.amazon.com/AWSECommerceService/2011-08-01", propOrder={"items"})
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemSearchResponse {

	@XmlElement(name = "Items", type = AmazonBook.class)
	@XmlPath("ItemSearchResponse/Items/text()")
	private List<AmazonBook> items;

	public ItemSearchResponse() {
		items = new ArrayList<>();
	}

	public List<AmazonBook> getItems() {
		return items;
	}

	public void setItems(List<AmazonBook> items) {
		this.items = items;
	}
	
	
	//private AmazonBooks items;

	/*public ItemSearchResponse() {
		items = new AmazonBooks();
	}

	public AmazonBooks getItems() {
		return items;
	}

	public void setItems(AmazonBooks items) {
		this.items = items;
	}
	*/
	
}
