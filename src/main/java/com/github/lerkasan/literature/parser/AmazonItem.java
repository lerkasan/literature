package com.github.lerkasan.literature.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.github.lerkasan.literature.entity.Literature;
import com.github.lerkasan.literature.service.AuthorService;

public class AmazonItem implements ConvertableToItemToRead {
	
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

			for (String formatString : DATE_FORMAT_STRINGS) {
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
					LocalDate date = LocalDate.parse(publicationDate, formatter);
					literatureItem.setPublishDate(date);
					literatureItem.setYear(date.getYear());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		}
		literatureItem.setAccessType(ItemAccessType.PAID);
		literatureItem.setItemType(ItemType.BOOK);
		if ((author != null) && (author != "")) {
			String[] fullNameParts = authorService.divideFullName(author);
			Author itemAuthor = authorService.getByFullName(fullNameParts[0], fullNameParts[1]);
			if (itemAuthor == null) {
				itemAuthor = new Author(fullNameParts[0], fullNameParts[1]);
				//This saving is used instead of Cascade.PERSIST to avoid duplication of existing authors:
				authorService.save(itemAuthor);
				itemAuthor = authorService.getByFullName(fullNameParts[0], fullNameParts[1]);
			}
			literatureItem.addAuthor(itemAuthor);
			itemAuthor.addItemToRead(literatureItem);
		}
		return literatureItem;
	}

	@Override
	public String getUrl() {
		return getItemUrl();
	}

}
