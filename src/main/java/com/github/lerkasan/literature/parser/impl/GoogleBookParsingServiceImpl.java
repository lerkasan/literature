package com.github.lerkasan.literature.parser.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.parser.GoogleBookJson;
import com.github.lerkasan.literature.parser.ParsingService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service("GoogleBookParsingService")
public class GoogleBookParsingServiceImpl extends ParsingServiceImpl implements ParsingService {

	@Override
	public List<GoogleBookJson> parse(String input) {
		
		List<GoogleBookJson> googleBookRecords = new ArrayList<>();
		Gson json = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject rootObj = parser.parse(input).getAsJsonObject();
		JsonArray items = rootObj.getAsJsonArray("items");
		
		for (JsonElement item : items) {
			JsonElement volumeInfo = item.getAsJsonObject().get("volumeInfo");
			GoogleBookJson googleBookRecord = json.fromJson(volumeInfo.toString(), GoogleBookJson.class);
	//		System.out.println("\n" + googleBookRecord.getTitle());
			googleBookRecords.add(googleBookRecord);
		}
		return googleBookRecords;
	}

}
