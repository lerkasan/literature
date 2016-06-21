package com.github.lerkasan.literature.parser.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.parser.ParsingService;
import com.github.lerkasan.literature.parser.SpringerApiJson;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service("SpringerParsingService")
public class SpringerParsingServiceImpl implements ParsingService {

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

}
