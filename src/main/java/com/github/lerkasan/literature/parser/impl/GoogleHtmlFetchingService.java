package com.github.lerkasan.literature.parser.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleHtmlFetchingService

{
	public static void main(String args[]) {
		Document doc;
		System.out.println("test");
		try {
			doc = Jsoup
					.connect(
							"https://www.google.com/search?as_q=&as_epq=%22Yorkshire+Capital%22+&as_oq=fraud+OR+allegations+OR+scam&as_eq=&as_nlo=&as_nhi=&lr=lang_en&cr=countryCA&as_qdr=all&as_sitesearch=&as_occt=any&safe=images&tbs=&as_filetype=&as_rights=")
					.userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0")
					.ignoreHttpErrors(false).timeout(0).get();
			Elements links = doc.select("li[class=g]");
			for (Element link : links) {
				Elements titles = link.select("h3[class=r]");
				String title = titles.text();

				Elements bodies = link.select("span[class=st]");
				String body = bodies.text();

				System.out.println("Title: " + title);
				System.out.println("Body: " + body + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
