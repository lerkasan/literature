package com.github.lerkasan.literature.parser.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.parser.ConvertableToItemToRead;
import com.github.lerkasan.literature.parser.CrossrefApiJson;
import com.github.lerkasan.literature.parser.ParsingService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service("CrossrefParsingService")
public class CrossrefParsingServiceImpl extends ParsingServiceImpl implements ParsingService {

	@Override
	public List<CrossrefApiJson> parse(String input) {
		System.out.println(input);
		Gson json = new Gson();
		List<CrossrefApiJson> itemList = new ArrayList<>();
		JsonParser parser = new JsonParser();
		JsonArray items = parser.parse(input).getAsJsonObject().get("message").getAsJsonObject()
				.getAsJsonArray("items");
		if ((items != null) && (items.size() > 0)) {
			for (JsonElement item : items) {
				CrossrefApiJson itemJson = json.fromJson(item, CrossrefApiJson.class);
				if (!item.getAsJsonObject().get("type").getAsString().equals("component")
						&& item.getAsJsonObject().has("title")) {
					JsonArray titleArray = item.getAsJsonObject().get("title").getAsJsonArray();
					if ( (titleArray != null) && (titleArray.size() > 0 ) ) {
						String title = titleArray.get(0).getAsString();
						itemJson.setItemTitle(title);
					}
				}
				if (item.getAsJsonObject().has("created")) {
					JsonElement created = item.getAsJsonObject().get("date-time");
					if (created != null) {
						String createdStr = created.getAsJsonObject().getAsString();
						createdStr = createdStr.substring(0, 10);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate publishDate = LocalDate.parse(createdStr, formatter);
						itemJson.setPublishDate(publishDate);
					}
				}
				if (item.getAsJsonObject().has("ISBN")) {
					String isbn = item.getAsJsonObject().get("ISBN").getAsJsonArray().get(0).getAsString();
					isbn = isbn.substring(32);
					itemJson.setIsbn(isbn);
				}
				if ((itemJson.getItemTitle() != null) && (itemJson.getItemTitle() != "")) {
					itemList.add(itemJson);
				}
			}
		}
	
		return itemList;

	}

}
