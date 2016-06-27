package com.github.lerkasan.literature.parser.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.parser.GoogleApiJson;
import com.github.lerkasan.literature.parser.ParsingService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service("GoogleParsingService")
public class GoogleParsingServiceImpl extends ParsingServiceImpl implements ParsingService {
	
	@Override
	public List<GoogleApiJson> parse(String input) {
		List<GoogleApiJson> articleList = new ArrayList<>();

		Gson json = new Gson();
		JsonParser parser = new JsonParser();
		JsonArray items = parser.parse(input).getAsJsonObject().getAsJsonArray("items");
		if ((items != null) && (items.size() > 0)) {
			for (JsonElement item : items) {
				GoogleApiJson articleJson = json.fromJson(item, GoogleApiJson.class);
				JsonObject pagemap = item.getAsJsonObject().get("pagemap").getAsJsonObject();

				if (pagemap.has("person")) { // TO DO !!!
					JsonArray persons = pagemap.getAsJsonArray("person");
					if (persons != null) {
						articleJson.setAuthors(new ArrayList<String>());
						for (JsonElement person : persons) {
							JsonElement personElement = person.getAsJsonObject().get("name");
							if ((personElement != null)  && (! articleJson.getAuthors().contains(personElement.getAsString()))) {
								articleJson.getAuthors().add(personElement.getAsString());
							}
						}
					}
				}
				if (pagemap.has("blogposting")) {
					JsonElement blogposting = pagemap.getAsJsonArray("blogposting").get(0);
					if (blogposting.getAsJsonObject().has("datepublished")) {
						JsonElement dateElement = blogposting.getAsJsonObject().get("datepublished");
						if (dateElement != null) {
							String date = dateElement.getAsString();
							articleJson.setPublishDate(date);
						}
						/*
						 * date = date.substring(0, 10); DateTimeFormatter
						 * formatter =
						 * DateTimeFormatter.ofPattern("yyyy-MM-dd"); LocalDate
						 * publishDate = LocalDate.parse(date, formatter);
						 * articleJson.setPublishDate(publishDate);
						 */

					}
					if (blogposting.getAsJsonObject().has("keywords")) {
						JsonElement keywordsElement = blogposting.getAsJsonObject().get("keywords");
						if (keywordsElement != null) {
							String keywords = keywordsElement.getAsString();
							articleJson.setKeywords(keywords);
						}
					}
				}
				articleList.add(articleJson);
			}
		}
		return articleList;
	}

}
