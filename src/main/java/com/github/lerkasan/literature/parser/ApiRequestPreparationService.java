package com.github.lerkasan.literature.parser;

import com.github.lerkasan.literature.entity.Resource;

public interface ApiRequestPreparationService {
	// public static final String[] SPECIAL_CHARS = { "\"", "\'", ";", "`", "^",
	// "&", "|", "\\" };
	public static final String[] SPECIAL_CHARS = { ";", "`", "^", "&", "|", ":" };
	public static final String SPRINGER_SUBJECT = "subject:\"Computer Science\" AND (";
	public static final String KEYWORD = "keyword:";
	public static final String TITLE = "title:";
	public static final String CHARSET = "UTF-8";
	public static final String RESULT_SIZE = "50"; // TO DO - make changeable

	public static final String AMAZON = "Amazon";
	public static final String GOOGLE_API = "Google_API";
	public static final String GOOGLE_SITE = "Google";
	public static final String GOOGLE_BOOKS = "Google_Books";
	public static final String SPRINGER = "Springer";
	public static final String CROSSREF = "Crossref";

	public String passRequestToApi(Resource api, String query);

	public String prepareQuery(Resource api, String[] keywords);

	public String[] deleteSpecialsAndSplit(String keywordsString);
}
