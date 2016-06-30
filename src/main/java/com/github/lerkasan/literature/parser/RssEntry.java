package com.github.lerkasan.literature.parser;

import javax.inject.Inject;

import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;

public class RssEntry extends SyndEntryImpl implements ConvertableToItemToRead {

	@Inject
	RssService rssService;

	private static final long serialVersionUID = 1L;
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
		ItemToRead item = rssService.convertToItem(this);
		return item;
	}

	@Override
	public String getUrl() {
		return getLink();
	}

}
