package com.github.lerkasan.literature.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Lob;

import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.github.lerkasan.literature.service.AuthorService;

public class GoogleApiJson implements ConvertableToItemToRead {
	private String title;
	private String link;
	private String snippet;
	private List<String> authors;
	private String publishDate;
	private String keywords;

	@Inject
	private AuthorService authorService;

	public GoogleApiJson() {
		authors = new ArrayList<>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Override
	public ItemToRead convertToItem() {
		ItemToRead item = new ItemToRead();
		item.setTitle(title);
		item.setUrl(link);
		if (snippet != null) {
			item.setContents(snippet);
		}
		if (keywords != null) {
			item.setKeywords(keywords);
		}
		item.setAccessType(ItemAccessType.FREE);
		item.setItemType(ItemType.INTERNET_ARTICLE);

		for (String author : authors) {
			if ((author != null) && (author != "")) {
				String[] fullNameParts = authorService.divideFullName(author);
				Author itemAuthor = authorService.getByFullName(fullNameParts[0], fullNameParts[1]);
				if (itemAuthor == null) {
					itemAuthor = new Author(fullNameParts[0], fullNameParts[1]);
					//This saving is used instead of Cascade.PERSIST to avoid duplication of existing authors:
					authorService.save(itemAuthor);
					itemAuthor = authorService.getByFullName(fullNameParts[0], fullNameParts[1]);
				}
				item.addAuthor(itemAuthor);
				itemAuthor.addItemToRead(item); 
			}
		}
		return item;
	}

	@Override
	public String getUrl() {
		return getLink();
	}

}
