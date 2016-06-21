package com.github.lerkasan.literature.parser;

import java.time.LocalDate;

public class CrossrefApiJson {
	private String publisher;
	private String DOI;
	private String itemTitle;
	private String URL;
	private LocalDate publishDate;
	private String isbn;
	
	public CrossrefApiJson() {
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String title) {
		this.itemTitle = title;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getDOI() {
		return DOI;
	}
	public void setDOI(String dOI) {
		DOI = dOI;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public LocalDate getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	
	

}
