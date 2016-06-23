package com.github.lerkasan.literature.parser;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.Lob;

import com.github.lerkasan.literature.dao.AuthorRepository;
import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.github.lerkasan.literature.service.AuthorService;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndPerson;

public class RssEntry extends SyndEntryImpl implements ConvertableToItemToRead {
	
	@Inject
	private AuthorService authorService;
	
	@Inject
	private AuthorRepository authorRepository;
	
	private ItemAccessType itemAccessType;
	private ItemType itemType;

	public RssEntry() {
	}
	
	public RssEntry(SyndEntry syndEntry) {
		setAuthor(syndEntry.getAuthor());
		setAuthors(syndEntry.getAuthors());
		setLink(syndEntry.getLink());
		setTitle(syndEntry.getTitle());
		setPublishedDate(syndEntry.getPublishedDate());
		setItemAccessType(ItemAccessType.FREE);
		setItemType(ItemType.INTERNET_ARTICLE);
	}

	public ItemAccessType getItemAccessType() {
		return itemAccessType;
	}

	public void setItemAccessType(ItemAccessType itemAccessType) {
		this.itemAccessType = itemAccessType;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	@Override
	public ItemToRead convertToItem() {

		ItemToRead item = new ItemToRead();
		item.setAuthors(new ArrayList<Author>());
		item.setUrl(this.getLink());
		item.setTitle(this.getTitle());
		item.setAccessType(ItemAccessType.FREE);
		item.setItemType(ItemType.INTERNET_ARTICLE);
		Date publDate = this.getPublishedDate();
		LocalDate publishDate = publDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		item.setPublishDate(publishDate);
		if (!this.getAuthors().isEmpty()) {
			for (SyndPerson syndAuthor : getAuthors()) {
				String[] fullNameParts = authorService.divideFullName(syndAuthor.getName());
				//Author itemAuthor = authorService.getByFullName(fullNameParts[0], fullNameParts[1]);
				Author itemAuthor = authorRepository.findByFullName(fullNameParts[0], fullNameParts[1]);
				if (itemAuthor == null) {
					itemAuthor = new Author(fullNameParts[0], fullNameParts[1]);
				}
				item.addAuthor(itemAuthor);
			}
		} else {
			String authorStr = this.getAuthor();
			if ((authorStr != null) && (authorStr != "")) {
				String[] fullNameParts = authorService.divideFullName(authorStr);
				Author rssAuthor = authorService.getByFullName(fullNameParts[0], fullNameParts[1]);
				if (rssAuthor == null) {
					rssAuthor = new Author(fullNameParts[0], fullNameParts[1]);
					//authorService.save(rssAuthor);
				}
				item.addAuthor(rssAuthor);
			}
		}
		
		return item;
	}

}
