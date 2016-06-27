package com.github.lerkasan.literature.parser.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.parser.ApiRequestPreparationService;
import com.github.lerkasan.literature.parser.GoogleApiJson;
import com.github.lerkasan.literature.parser.ParsingService;
 
@Service("GoogleHtmlParsingService")
public class GoogleHtmlParsingService extends ParsingServiceImpl implements ParsingService {
 
    public static final String GOOGLE_RESULT_TAG = "h3.r > a";
    public static final String GOOGLE_BOOKS_PARAMETER = "&tbm=bks";
    
    @Override
	public List<GoogleApiJson> parse(String input) {
    	System.out.println("My input is: "+input);
        String searchBooksUrl = input + GOOGLE_BOOKS_PARAMETER;  //book search
		List<GoogleApiJson> articleList = new ArrayList<>();
        Document doc;
     
	/*	try {
			doc = Jsoup.connect(searchBooksUrl).userAgent("Mozilla/5.0").get();
			   Elements results = doc.select(GOOGLE_RESULT_TAG);
			  // Elements imagesBase64 = results.select("g-img._ygd > img");
		        for (Element result : results) {
		            String linkHref = result.attr("href");
		            String linkText = result.text();
		            linkHref = linkHref.substring(6, linkHref.indexOf("&"));
		            GoogleApiJson article = new GoogleApiJson();
		            article.setTitle(linkText);
		            article.setLink(linkHref);
		            articleList.add(article);
		        }
		        Elements img = doc.select("img");      		
		        String imageBase64 = img.get(4).attr("src");
		        System.out.println(imageBase64);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
			doc = Jsoup.connect(input).userAgent("Mozilla/5.0").get();
			   Elements results = doc.select(GOOGLE_RESULT_TAG);
		        for (Element result : results) {
		            String linkHref = result.attr("href");
		            String linkText = result.text();
		            linkHref = linkHref.substring(6, linkHref.indexOf("&"));
		            GoogleApiJson article = new GoogleApiJson();
		            article.setTitle(linkText);
		            article.setLink(linkHref);
		            articleList.add(article);
		        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return articleList;
    }
 
}
