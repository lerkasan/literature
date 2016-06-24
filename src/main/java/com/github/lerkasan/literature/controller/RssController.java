package com.github.lerkasan.literature.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.lerkasan.literature.dao.AuthorRepository;
import com.github.lerkasan.literature.dao.ItemToReadRepository;
import com.github.lerkasan.literature.dao.ResourceRepository;
import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.github.lerkasan.literature.entity.Resource;
import com.github.lerkasan.literature.parser.RssService;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndPerson;

@Controller
@Scope("session")
@RequestMapping("/rss")
public class RssController {
	@Inject
	RssService rssService;
	
	@Inject
	ResourceRepository resourceService;
	/*
	 * @Inject AuthorRepository authorService;
	 * 
	 * @Inject ItemToReadRepository itemToReadService;
	 */

	@RequestMapping(value = "/{rssName}", method = RequestMethod.GET)
	public String showRssNewsByRssName(@PathVariable String rssName, ModelMap model, HttpServletRequest request) {
		Resource rss = resourceService.findByName(rssName);
		List<SyndEntry> rssNews = rssService.read(rss);
		List<Resource> rssList = new ArrayList<>();
		Iterable<Resource> rssIterable = resourceService.findByResponseFormat("rss");
		if (rssIterable != null) {
			rssIterable.forEach(rssList::add);
		}
		model.addAttribute("rssNews", rssNews);
		model.addAttribute("rssName", rss.getName());
		if (!rssList.isEmpty()) {
			model.addAttribute("rssList", rssList);
		}
		request.getSession().setAttribute("rssNewsParam", rssNews);
		request.getSession().setAttribute("currentRssName", rssName);
		return "rss";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveRssNews(@RequestParam(value = "selectedRssNews", required = false) int[] selectedRssNewsIds,
			ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

		List<SyndEntry> rssNews = (List<SyndEntry>) request.getSession().getAttribute("rssNewsParam");
		model.addAttribute("rssNews", rssNews);
		List<Resource> rssList = (List<Resource>) request.getSession().getAttribute("rssList");
		model.addAttribute("rssList", rssList);
		String currentRssName = (String) request.getSession().getAttribute("currentRssName");
		String message = rssService.save(selectedRssNewsIds, rssNews);
		request.getSession().setAttribute("message", message);
		model.addAttribute("message", message);
		return "savedRss/" + currentRssName;
		// return "redirect:" + "/rss/" + currentRssName;

	}

}
