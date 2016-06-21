package com.github.lerkasan.literature.controller;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.github.lerkasan.literature.dao.ResourceRepository;
import com.github.lerkasan.literature.entity.Resource;
import com.github.lerkasan.literature.parser.RssService;
import com.rometools.rome.feed.synd.SyndEntry;

@Controller
@Scope("session")
@RequestMapping("/")
public class IndexController {
	@Inject
	RssService rssService;
	@Inject
	ResourceRepository rssFeed;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request) {
		List<Resource> rssList = new ArrayList<>();
		Iterable<Resource> rssIterable = rssFeed.findByResponseFormat("rss");
		if (rssIterable != null) {
			rssIterable.forEach(rssList::add);
		}
		if (!rssList.isEmpty()) {
			Resource currentRss = rssList.get(rssList.size()-1);
			List<SyndEntry> rssNews = rssService.read(currentRss);
			model.addAttribute("rssNews", rssNews);
			model.addAttribute("rssList", rssList);
			request.getSession().setAttribute("rssNewsParam",rssNews);
			request.getSession().setAttribute("rssList",rssList);
			request.getSession().setAttribute("currentRssName",currentRss.getName());
			
		}
	/*	model.addAttribute("searchQuery", searchQuery);
		model.addAttribute("selectedRssNews", selectedRssNews);*/
		return "index";
	}

}
