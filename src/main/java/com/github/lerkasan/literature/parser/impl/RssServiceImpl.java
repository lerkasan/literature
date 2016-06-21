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

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.controller.Messages;
import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.github.lerkasan.literature.entity.Resource;
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
	ItemToReadService itemToReadService;
	
	@Inject
	AuthorService authorService;

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
				/*
				 * List entries = feed.getEntries(); Iterator itEntries =
				 * entries.iterator();
				 * 
				 * while (itEntries.hasNext()) { SyndEntry entry = (SyndEntry)
				 * itEntries.next(); System.out.println(
				 * "-------------------------------------------------");
				 * System.out.println("Title: " + entry.getTitle());
				 * System.out.println("Link: " + entry.getLink());
				 * System.out.println("Author: " + entry.getAuthor());
				 * System.out.println("Publish Date: " +
				 * entry.getPublishedDate()); //System.out.println(
				 * "Description: " + entry.getDescription().getValue());
				 * System.out.println(); }
				 */
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
	
	public String saveRssNewsToDb(int[] selectedRssNewsIds, List<SyndEntry> rssNews) {
		String givenName;
		String familyName;
		boolean oldItemsFlag = false;
		if (selectedRssNewsIds != null) {
			for (int i : selectedRssNewsIds) {
				SyndEntry pieceOfNews = rssNews.get(i);
				String url = pieceOfNews.getLink();
				ItemToRead item = itemToReadService.getByUrl(url); //  itemToReadRepository.findByUrl(url) worked fine
				if (item == null) {
					item = new ItemToRead();
					if (!pieceOfNews.getAuthors().isEmpty()) {
						for (SyndPerson syndAuthor : pieceOfNews.getAuthors()) {
							String[] fullNameParts = syndAuthor.getName().split(" ", 2);
							Author rssAuthor = authorService.getByFullName(fullNameParts[0], fullNameParts[1]); //  authorRepository.getByFullName(...)  worked fine
							if (rssAuthor == null) {
								rssAuthor = new Author(fullNameParts[0], fullNameParts[1]);
								authorService.save(rssAuthor);
							}
							item.addAuthor(rssAuthor);
						}
					} else {
						String authorStr = pieceOfNews.getAuthor();
						if ((authorStr != null) && (authorStr != "")) {
							String[] fullNameParts = pieceOfNews.getAuthor().split(" ", 2);
							if (fullNameParts.length > 1) {
								givenName = fullNameParts[0];
								familyName = fullNameParts[1];
							} else {
								givenName = "";
								familyName = fullNameParts[0];
							}
							Author rssAuthor = authorService.getByFullName(givenName, familyName);
							if (rssAuthor == null) {
								rssAuthor = new Author(fullNameParts[0], fullNameParts[1]);
								authorService.save(rssAuthor);
							}
							item.setAuthors(new ArrayList<Author>());
							item.addAuthor(rssAuthor);
						}
					}
					item.setUrl(url);
					item.setTitle(pieceOfNews.getTitle());
					item.setAccessType(ItemAccessType.FREE);
					item.setItemType(ItemType.INTERNET_ARTICLE);
					Date publDate = pieceOfNews.getPublishedDate();
					LocalDate publishDate = publDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					item.setPublishDate(publishDate);
					item.setAddedAt(LocalDate.now());
					itemToReadService.save(item);
				} else {
					oldItemsFlag = true;
				}
				
				
				// redirectAttributes.addFlashAttribute("message", message);
			}
		}
		return oldItemsFlag ? Messages.SOME_ITEMS_ALREADY_IN_DB : Messages.SUCCESSFUL_SAVE;
	}

}
