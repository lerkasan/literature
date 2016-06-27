package com.github.lerkasan.literature.parser.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.parser.ConvertableToItemToRead;
import com.github.lerkasan.literature.parser.ParsingService;
import com.github.lerkasan.literature.parser.SpringerApiJson;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service("SpringerParsingService")
public class SpringerParsingServiceImpl extends ParsingServiceImpl implements ParsingService {
	
/*	@Inject
	private ItemToReadService itemToReadService;

	@Inject
	private AuthorService authorService; */
	

	@Override
	public List<ConvertableToItemToRead> parse(String input) {
		Gson json = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject rootObj = parser.parse(input).getAsJsonObject();
		JsonArray recordsObj = rootObj.getAsJsonArray("records");
		SpringerApiJson[] springerRecords = json.fromJson(recordsObj.toString(), SpringerApiJson[].class);
		return Arrays.asList(springerRecords);
	}
	
	/* public Literature convertToItem(SpringerApiJson item) {
		Literature literatureItem = new Literature();
		literatureItem.setTitle(item.getTitle());
		literatureItem.setPublishing(item.getPublisher());
		literatureItem.setIsbn(item.getIsbn());
		literatureItem.setIssn(item.getIssn());
		literatureItem.setUrl(item.getUrl());
		if ((item.getPublicationDate() != null) && (item.getPublicationDate() != "")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(item.getPublicationDate(), formatter);
			literatureItem.setPublishDate(date);
			literatureItem.setYear(date.getYear());
		}
		literatureItem.setDoi(item.getDoi());
		literatureItem.setVolume(Short.parseShort(item.getVolume()));
		literatureItem.setIssueOrEditionNumber(item.getNumber());
		literatureItem.setAccessType(ItemAccessType.FREE);
		literatureItem.setItemType(ItemType.JOURNAL_ARTICLE);
		literatureItem.setAuthors(new ArrayList<Author>());

		return literatureItem; 
	}*/
	
	/*
public String save(int[] selectedItemsIds, List<ConvertableToItemToRead> items) {
		
		boolean oldItemsFlag = false;
		if ((selectedItemsIds != null) && (items != null)) {
			for (int i : selectedItemsIds) {
				ConvertableToItemToRead convertableItem = items.get(i);
				String url = convertableItem.getUrl();
				ItemToRead foundItem = itemToReadService.getByUrl(url); 
				if (foundItem == null) {
					ItemToRead item = convertableItem.convertToItem(); 
					item.setAddedAt(LocalDate.now()); 
					itemToReadService.save(item);
				} else {
					oldItemsFlag = true;
				}
			}
		}
		return oldItemsFlag ? Messages.SOME_ITEMS_ALREADY_IN_DB : Messages.SUCCESSFUL_SAVE;
	}
*/

}
