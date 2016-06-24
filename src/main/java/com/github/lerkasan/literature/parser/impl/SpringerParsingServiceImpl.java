package com.github.lerkasan.literature.parser.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.controller.Messages;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.parser.ParsingService;
import com.github.lerkasan.literature.parser.RssEntry;
import com.github.lerkasan.literature.parser.SpringerApiJson;
import com.github.lerkasan.literature.service.AuthorService;
import com.github.lerkasan.literature.service.ItemToReadService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rometools.rome.feed.synd.SyndEntry;

@Service("SpringerParsingService")
public class SpringerParsingServiceImpl implements ParsingService {
	
	@Inject
	private ItemToReadService itemToReadService;

	@Inject
	private AuthorService authorService;
	

	@Override
	public List<SpringerApiJson> parse(String input) {
		Gson json = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject rootObj = parser.parse(input).getAsJsonObject();
		JsonArray recordsObj = rootObj.getAsJsonArray("records");
		SpringerApiJson[] springerRecords = json.fromJson(recordsObj.toString(), SpringerApiJson[].class);
	/*	for ( SpringerApiJson record : springerRecords) {
		System.out.println(record.getTitle());
		System.out.println(record.getUrl());
		System.out.println(record.getPublisher());
		System.out.println();
		}*/
		return Arrays.asList(springerRecords);
	}
	
public String save(int[] selectedItemsIds, List<SpringerApiJson> items) {
		
		boolean oldItemsFlag = false;
		if ((selectedItemsIds != null) && (items != null)) {
			for (int i : selectedItemsIds) {
				SpringerApiJson springerItem = items.get(i);
				String url = springerItem.getUrl();
				ItemToRead foundItem = itemToReadService.getByUrl(url); 
				if (foundItem == null) {
					ItemToRead item = springerItem.convertToItem(); 
					item.setAddedAt(LocalDate.now()); 
					itemToReadService.save(item);
				} else {
					oldItemsFlag = true;
				}
			}
		}
		return oldItemsFlag ? Messages.SOME_ITEMS_ALREADY_IN_DB : Messages.SUCCESSFUL_SAVE;
	}

}
