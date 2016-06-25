package com.github.lerkasan.literature.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.persistence.Lob;

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.github.lerkasan.literature.entity.Literature;

public class CrossrefApiJson implements ConvertableToItemToRead {
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

	@Override
	public Literature convertToItem() {
		Literature literatureItem = new Literature();
		literatureItem.setTitle(itemTitle);
		literatureItem.setPublishing(publisher);
		literatureItem.setIsbn(isbn);
		literatureItem.setUrl(URL);
		if (publishDate != null) {
			literatureItem.setPublishDate(publishDate);
			literatureItem.setYear(publishDate.getYear());
		}
		literatureItem.setDoi(DOI);
		literatureItem.setAccessType(ItemAccessType.PAID);
		literatureItem.setItemType(ItemType.JOURNAL_ARTICLE);
		return literatureItem;
	}

	@Override
	public String getUrl() {
		return getURL();
	}

}
