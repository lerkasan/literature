package com.github.lerkasan.literature.parser.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.inject.Named;

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.entity.Resource;
import com.github.lerkasan.literature.parser.ApiRequestPreparationService;

@Service("APiRequestPreparationService")
public class ApiRequestPreparationServiceImpl implements ApiRequestPreparationService {

	@Override
	public String prepareQuery(Resource api, String[] keywords) {
		String res;
		StringBuilder result = new StringBuilder();
		
		if (api.getName().equals("Springer")) {
			result.append(SPRINGER_SUBJECT);

			for (String word : keywords) {
				result = result.append(KEYWORD + "\"" + word + "\" OR " + TITLE + "\"" + word + "\" AND " );
			}
			res = result.substring(0, result.length()-4)+ ")";
		//	System.out.println("search:"+res);
		} else {
			for (String word : keywords) {
				result = result.append(word + "+");
			}
			res = result.substring(0, result.length() - 1);
		}
		System.out.println("search:"+res);
		return res;
	}

	@Override
	public String[] deleteSpecialsAndSplit(String keywords) {
		for (String special : SPECIAL_CHARS) {
			keywords.replaceAll(special, "");
		}
		//keywords.replaceAll(" ", "%20");
		if (keywords == null || keywords.equals("")) {
			throw new IllegalArgumentException("Search keywords are null");
		} else {
			//String[] words = keywords.split(",%20");
			String[] words = keywords.split(", ");
			if (words.length < 2) {
				words = keywords.split(",");
			}
			return words;
		}
	}

	@Override
	public String passRequestToApi(Resource api, String query) {
		HttpURLConnection connection;
		InputStream response;
		// String paramQ = "subject:\"Computer Science\" AND (title:\"data\" OR
		// keyword:\"machine learning\" OR keyword:\"cloud computing\")";
		String request = "";
		try {
			// parameterFormat "q=%s&p=%s&api_key=%s"
			if (api.getName().equals("Springer")) {
				request = String.format(api.getParameterFormat(), URLEncoder.encode(query, CHARSET),
						URLEncoder.encode(RESULT_SIZE, CHARSET), URLEncoder.encode(api.getApiKey(), CHARSET));
			} else if (api.getName().equals("Google")) {
				request = String.format(api.getParameterFormat(), URLEncoder.encode(query, CHARSET),
						URLEncoder.encode(api.getSearchEngineKey(), CHARSET),
						URLEncoder.encode(api.getApiKey(), CHARSET));
			} else {
				request = String.format(api.getParameterFormat(), URLEncoder.encode(query, CHARSET)); 
			}

			connection = (HttpURLConnection) new URL(api.getUrl() + "?" + request).openConnection();
			connection.setRequestProperty("Accept-Charset", CHARSET);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Host", api.getDomain());
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.setRequestProperty("Accept", "application/" + api.getResponseFormat());
			connection.setRequestProperty("Accept-Language", "en-us,en;q=0.5");

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				connection.disconnect();
				return sb.toString();
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
				StringBuilder sb = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				connection.disconnect();
				System.out.println(sb.toString());
				return sb.toString();
			}
			// System.out.println(response.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// connection.getErrorStream();
		}
		return null;
	}

}
