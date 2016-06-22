package com.github.lerkasan.literature.parser.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.parser.GoogleApiJson;
import com.github.lerkasan.literature.parser.ParsingService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service("GoogleParsingService")
public class GoogleParsingServiceImpl implements ParsingService {

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

			/*	if (pagemap.has("person")) {   //TO DO !!!
					JsonArray persons = pagemap.getAsJsonArray("person");
					if (persons != null) {
						for (JsonElement person : persons) {
							String name = person.getAsJsonObject().get("name").getAsString();
							articleJson.getAuthors().add(name);
						}
					}
				} */
				if (pagemap.has("blogposting")) {
					JsonElement blogposting = pagemap.getAsJsonArray("blogposting").get(0);
					if (blogposting.getAsJsonObject().has("datepublished")) {
						String date = blogposting.getAsJsonObject().get("datepublished").getAsString();
						/*
						 * date = date.substring(0, 10); DateTimeFormatter
						 * formatter =
						 * DateTimeFormatter.ofPattern("yyyy-MM-dd"); LocalDate
						 * publishDate = LocalDate.parse(date, formatter);
						 * articleJson.setPublishDate(publishDate);
						 */
						articleJson.setPublishDate(date);
					}
					if (blogposting.getAsJsonObject().has("keywords")) {
						String keywords = blogposting.getAsJsonObject().get("keywords").getAsString();
						articleJson.setKeywords(keywords);
					}
				}
				articleList.add(articleJson);
			}
		}
		// System.out.println("my result #3" + articleList.get(3).getTitle()+"
		// "+articleList.get(3).getAuthors().get(0)+"
		// "+articleList.get(3).getPublishDate()+"
		// "+articleList.get(3).getKeywords());
		return articleList;
	}

}
