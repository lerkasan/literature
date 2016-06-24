package com.github.lerkasan.literature.parser.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.controller.Messages;
import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.github.lerkasan.literature.entity.Resource;
import com.github.lerkasan.literature.parser.ConvertableToItemToRead;
import com.github.lerkasan.literature.parser.RssEntry;
import com.github.lerkasan.literature.parser.RssService;
import com.github.lerkasan.literature.service.AuthorService;
import com.github.lerkasan.literature.service.ItemToReadService;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Service("RssService")
public class RssServiceImpl implements RssService {

	@Inject
	private ItemToReadService itemToReadService;

	@Inject
	private AuthorService authorService;
	
	@Inject
	private AutowireCapableBeanFactory beanFactory;

	public RssServiceImpl() {
	}

	public List<SyndEntry> read(Resource resource) {

		URL url;
		if (resource.getResponseFormat().equals("rss")) {
			try {
				url = new URL(resource.getUrl());
				HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
				SyndFeedInput input = new SyndFeedInput();
				SyndFeed feed = input.build(new XmlReader(httpcon));
				httpcon.disconnect();
				return feed.getEntries();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FeedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// TO DO - log exception "Rss reader can't read non RSS feed"
		}

		return null;
	}

	public String save(int[] selectedRssNewsIds, List<SyndEntry> rssNews) {
		
		boolean oldItemsFlag = false;
		if ((selectedRssNewsIds != null) && (rssNews != null)) {
			for (int i : selectedRssNewsIds) {
				SyndEntry pieceOfNewsSynd = rssNews.get(i);
				
				RssEntry pieceOfNews = new RssEntry(pieceOfNewsSynd);
				beanFactory.autowireBean(pieceOfNews);
				
				String url = pieceOfNews.getLink();
				ItemToRead foundItem = itemToReadService.getByUrl(url); 
				if (foundItem == null) {
					ItemToRead item = pieceOfNews.convertToItem(); 
					item.setAddedAt(LocalDate.now()); 
					itemToReadService.save(item);
				} else {
					oldItemsFlag = true;
				}
			}
		}
		return oldItemsFlag ? Messages.SOME_ITEMS_ALREADY_IN_DB : Messages.SUCCESSFUL_SAVE;
	}

	@Override
	public ItemToRead convertToItem(RssEntry rssItem) {

		ItemToRead item = new ItemToRead();
		item.setUrl(rssItem.getLink());
		item.setTitle(rssItem.getTitle());
		item.setAccessType(ItemAccessType.FREE);
		item.setItemType(ItemType.INTERNET_ARTICLE);
		Date publDate = rssItem.getPublishedDate();
		LocalDate publishDate = publDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		item.setPublishDate(publishDate);
		if (!rssItem.getAuthors().isEmpty()) {
			for (SyndPerson syndAuthor : rssItem.getAuthors()) {
				String[] fullNameParts = authorService.divideFullName(syndAuthor.getName());
				Author itemAuthor = authorService.getByFullName(fullNameParts[0], fullNameParts[1]);
			//	Author itemAuthor = authorRepository.findByFullName(fullNameParts[0], fullNameParts[1]);
				if (itemAuthor == null) {
					itemAuthor = new Author(fullNameParts[0], fullNameParts[1]);
				}
				item.addAuthor(itemAuthor);
			}
		} else {
			String authorStr = rssItem.getAuthor();
			if ((authorStr != null) && (authorStr != "")) {
				String[] fullNameParts = authorService.divideFullName(authorStr);
				Author rssAuthor = authorService.getByFullName(fullNameParts[0], fullNameParts[1]);
				if (rssAuthor == null) {
					rssAuthor = new Author(fullNameParts[0], fullNameParts[1]);
				}
				item.addAuthor(rssAuthor);
			}
		}
		
		return item;
	}

}
