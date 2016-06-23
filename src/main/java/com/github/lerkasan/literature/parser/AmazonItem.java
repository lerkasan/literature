package com.github.lerkasan.literature.parser;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.AccessType;
import javax.persistence.Lob;

import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.github.lerkasan.literature.entity.Literature;
import com.github.lerkasan.literature.service.AuthorService;

public class AmazonItem implements  ConvertableToItemToRead {
	private String title;
	private String author;
	private String imageUrl;
	private String publisher;
	private String publicationDate;
	private String itemUrl;
	private String isbn;

	@Inject
	private AuthorService authorService;

	public AmazonItem() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String url) {
		this.itemUrl = url;
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
		literatureItem.setTitle(title);
		literatureItem.setImageUrl(imageUrl);
		literatureItem.setPublishing(publisher);
		literatureItem.setIsbn(isbn);
		literatureItem.setUrl(itemUrl);
		if ((publicationDate != null) && (publicationDate != "")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(publicationDate, formatter);
			literatureItem.setPublishDate(date);
			literatureItem.setYear(date.getYear());
		}
		literatureItem.setAccessType(ItemAccessType.PAID);
		literatureItem.setItemType(ItemType.BOOK);
		literatureItem.setAuthors(new ArrayList<Author>());
		String[] fullNameParts = authorService.divideFullName(author);
		Author itemAuthor = authorService.getByFullName(fullNameParts[0], fullNameParts[1]);
		if (itemAuthor == null) {
			itemAuthor = new Author(fullNameParts[0], fullNameParts[1]);
		}
		literatureItem.addAuthor(itemAuthor);
		return literatureItem;
	}

}
