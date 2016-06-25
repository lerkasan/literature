package com.github.lerkasan.literature.parser.impl;

import java.io.IOException;
import java.util.Scanner;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class GoogleHtmlParsingService {
 
    public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
    public static void main(String[] args) throws IOException {
        //Taking search term input from console
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the search term.");
        String searchTerm = scanner.nextLine();
      //  System.out.println("Please enter the number of results. Example: 5 10 20");
      //  int num = scanner.nextInt();
        scanner.close();
        int num = 50; 
        String searchURL = GOOGLE_SEARCH_URL + "?q="+searchTerm+"&num="+num+"&tbm=bks";  //books
        //without proper User-Agent, we will get 403 error
        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
         
        //below will print HTML data, save it to a file and open in browser to compare
        //System.out.println(doc.html());
         
        //If google search results HTML change the &lt;h3 class=&quot;r&quot; to &lt;h3 class=&quot;r1&quot;
        //we need to change below accordingly
        Elements results = doc.select("h3.r > a");
 
        for (Element result : results) {
            String linkHref = result.attr("href");
            String linkText = result.text();
            System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
        }
        
        String searchURL2 = GOOGLE_SEARCH_URL + "?q="+searchTerm+"&num="+num;  //books
        //without proper User-Agent, we will get 403 error
        Document doc2 = Jsoup.connect(searchURL2).userAgent("Mozilla/5.0").get();
         
        //below will print HTML data, save it to a file and open in browser to compare
        //System.out.println(doc.html());
         
        //If google search results HTML change the &lt;h3 class=&quot;r&quot; to &lt;h3 class=&quot;r1&quot;
        //we need to change below accordingly
        Elements results2 = doc2.select("h3.r > a");
 
        for (Element result : results2) {
            String linkHref = result.attr("href");
            String linkText = result.text();
            System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
        }
    }
 
}
