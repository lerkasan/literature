package com.github.lerkasan.literature.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.persistence.Lob;

import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.github.lerkasan.literature.entity.Literature;

public class SpringerApiJson implements ConvertableToItemToRead {
	private String identifier;
	private String title;
	private String publicationName;
	private String issn;
	private String isbn;
	private String doi;
	private String publisher;
	private String publicationDate;
	private String volume;
	private String number;
	private String startingPage;
	private String url;
	private String copyright;

	public SpringerApiJson() {
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublicationName() {
		return publicationName;
	}

	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getStartingPage() {
		return startingPage;
	}

	public void setStartingPage(String startingPage) {
		this.startingPage = startingPage;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	@Override
	public Literature convertToItem() {
		Literature literatureItem = new Literature();
		literatureItem.setTitle(title);
		literatureItem.setPublishing(publisher);
		literatureItem.setIsbn(isbn);
		literatureItem.setIssn(issn);
		literatureItem.setUrl(url);
		if ((publicationDate != null) && (publicationDate != "")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(publicationDate, formatter);
			literatureItem.setPublishDate(date);
			literatureItem.setYear(date.getYear());
		}
		literatureItem.setDoi(doi);
		literatureItem.setVolume(Short.parseShort(volume));
		literatureItem.setIssueOrEditionNumber(number);
		literatureItem.setAccessType(ItemAccessType.FREE);
		literatureItem.setItemType(ItemType.JOURNAL_ARTICLE);
		literatureItem.setAuthors(new ArrayList<Author>());

		return literatureItem;
	}

}
