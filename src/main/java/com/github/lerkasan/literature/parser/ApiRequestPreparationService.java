package com.github.lerkasan.literature.parser;

import com.github.lerkasan.literature.entity.Resource;

public interface ApiRequestPreparationService {
	//public static final String[] SPECIAL_CHARS = { "\"", "\'", ";", "`", "^", "&", "|", "\\" };
	public static final String[] SPECIAL_CHARS = { ";", "`", "^", "&", "|"};
	public static final String SPRINGER_SUBJECT = "subject:\"Computer Science\" AND (";
	public static final String KEYWORD = "keyword:";
	public static final String TITLE = "title:";
	public static final String CHARSET = "UTF-8";
	public static final String RESULT_SIZE = "50"; // TO DO - make changeable

	public String passRequestToApi(Resource api, String query);

	public String prepareQuery(Resource api, String[] keywords);

	public String[] deleteSpecialsAndSplit(String keywordsString);
}
