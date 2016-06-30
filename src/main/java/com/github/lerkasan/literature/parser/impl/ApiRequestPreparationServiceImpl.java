package com.github.lerkasan.literature.parser.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.entity.Resource;
import com.github.lerkasan.literature.parser.ApiRequestPreparationService;

@Service("APiRequestPreparationService")
public class ApiRequestPreparationServiceImpl implements ApiRequestPreparationService {

	@Override
	public String prepareQuery(Resource api, String[] keywords) {
		String request = "";
		String searchURL = "";
		StringBuilder query = new StringBuilder();
		try {
			switch (api.getName()) {
			case SPRINGER: {
				query.append(SPRINGER_SUBJECT);
				for (String word : keywords) {
					query = query.append(KEYWORD + "\"" + word + "\" OR " + TITLE + "\"" + word + "\" AND ");
				}
				request = query.substring(0, query.length() - 4) + ")";
				request = String.format(api.getParameterFormat(), URLEncoder.encode(request, CHARSET),
						URLEncoder.encode(RESULT_SIZE, CHARSET), URLEncoder.encode(api.getApiKey(), CHARSET));
				break;
			}
			case AMAZON: {
				for (String word : keywords) {
					query = query.append(word + "%20");
				}
				request = query.substring(0, query.length() - 3);
				// ADD Request

				break;
			}
			case GOOGLE_API: {
				for (String word : keywords) {
					query = query.append(word + "+");
				}
				request = query.substring(0, query.length() - 1);
				request = String.format(api.getParameterFormat(), URLEncoder.encode(request, CHARSET),
						URLEncoder.encode(api.getSearchEngineKey(), CHARSET),
						URLEncoder.encode(api.getApiKey(), CHARSET));
				break;
			}
			case GOOGLE_SITE: {
				for (String word : keywords) {
					query = query.append(word + "+");
				}
				request = query.substring(0, query.length() - 1);
				searchURL = api.getUrl() + "?q=" + request + "&num=" + ApiRequestPreparationService.RESULT_SIZE;
				break;
			}
			case GOOGLE_BOOKS: {
				for (String word : keywords) {
					query = query.append(word + "+");
				}
				request = query.substring(0, query.length() - 1);
				request = String.format(api.getParameterFormat(), URLEncoder.encode(request, CHARSET));
				break;
			}
			default: {
				for (String word : keywords) {
					query = query.append(word + "+");
				}
				request = query.substring(0, query.length() - 1);
				request = String.format(api.getParameterFormat(), URLEncoder.encode(request, CHARSET));
				break;
			}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("search:" + request);
		if (api.getName().equals(GOOGLE_SITE)) {
			return searchURL;
		}
		return request;
	}

	@Override
	public String[] deleteSpecialsAndSplit(String keywords) {
		for (String special : SPECIAL_CHARS) {
			keywords = keywords.replaceAll(special, "");
		}
		// keywords.replaceAll(" ", "%20");
		if (keywords == null || keywords.equals("")) {
			throw new IllegalArgumentException("Search keywords are null");
		} else {
			// String[] words = keywords.split(",%20");
			String[] words = keywords.split(", ");
			if (words.length < 2) {
				words = keywords.split(",");
			}
			return words;
		}
	}

	@Override
	public String passRequestToApi(Resource api, String request) {
		HttpURLConnection connection;
		StringBuilder response = new StringBuilder();
		// String paramQ = "subject:\"Computer Science\" AND (title:\"data\" OR
		// keyword:\"machine learning\" OR keyword:\"cloud computing\")";

		if (api.getName().equals(GOOGLE_SITE)) {
			return request;
		}
		try {
			connection = (HttpURLConnection) new URL(api.getUrl() + "?" + request).openConnection();
			connection.setRequestProperty("Accept-Charset", CHARSET);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Host", api.getDomain());
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.setRequestProperty("Accept", "application/" + api.getResponseFormat());
			connection.setRequestProperty("Accept-Language", "en-us,en;q=0.5");

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
				String output;
				while ((output = br.readLine()) != null) {
					response.append(output);
				}
				connection.disconnect();

			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
				String output;
				while ((output = br.readLine()) != null) {
					response.append(output);
				}
				connection.disconnect();
				// System.out.println(sb.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.toString();
	}

}
