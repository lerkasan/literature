package com.github.lerkasan.literature.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.lerkasan.literature.dao.ResourceRepository;
import com.github.lerkasan.literature.entity.Resource;
import com.github.lerkasan.literature.parser.AmazonBook;
import com.github.lerkasan.literature.parser.AmazonItem;
import com.github.lerkasan.literature.parser.ApiRequestPreparationService;
import com.github.lerkasan.literature.parser.CrossrefApiJson;
import com.github.lerkasan.literature.parser.GoogleApiJson;
import com.github.lerkasan.literature.parser.ParsingService;
import com.github.lerkasan.literature.parser.SpringerApiJson;
import com.github.lerkasan.literature.parser.impl.amazon.AmazonBookSearchService;
import com.rometools.rome.feed.synd.SyndEntry;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Inject
	ApiRequestPreparationService apiRequestPreparation;

	@Inject
	ResourceRepository resourceRepository;

	@Inject
	AmazonBookSearchService amazonBookSearchService;

	@Inject
	@Qualifier("AmazonParsingService")
	ParsingService amazonParsingService;

	@Inject
	@Qualifier("GoogleParsingService")
	ParsingService googleParsingService;

	@Inject
	@Qualifier("CrossrefParsingService")
	ParsingService crossrefParsingService;

	@Inject
	@Qualifier("SpringerParsingService")
	ParsingService springerParsingService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{engineName}", method = { RequestMethod.GET, RequestMethod.POST })
	// @RequestMapping(value = "/api", method = RequestMethod.POST)
	public String search(@PathVariable String engineName,
			@RequestParam(value = "searchQuery", required = false) String searchQuery, ModelMap model,
			HttpServletRequest request) {

		List<GoogleApiJson> googleResults;
		List<SpringerApiJson> springerResults;
		List<CrossrefApiJson> crossrefResults;
		List<AmazonItem> amazonResults;
		String apiResponse = "";

		List<Resource> searchEngineList = new ArrayList<>();
		Iterable<Resource> engineIterable = resourceRepository.findByResponseFormatNot("rss");
		if (engineIterable != null) {
			engineIterable.forEach(searchEngineList::add);
		}
		if (!searchEngineList.isEmpty()) {
			model.addAttribute("searchEngineList", searchEngineList);
			request.getSession().setAttribute("searchEngineList", searchEngineList);
		}

		if ((searchQuery == null) || (searchQuery == "")) {
			searchQuery = (String) request.getSession().getAttribute("searchQuery");
		}
		
		if ((searchQuery != null) && (searchQuery != "")) {
			request.getSession().setAttribute("searchQuery", searchQuery);
			String[] searchedWords = apiRequestPreparation.deleteSpecialsAndSplit(searchQuery);
			Iterable<Resource> foundApi = resourceRepository.findByResponseFormatNot("rss");

			Resource searchApi = resourceRepository.findByName(engineName);
			String preparedQuery = apiRequestPreparation.prepareQuery(searchApi, searchedWords);
			apiResponse = apiRequestPreparation.passRequestToApi(searchApi, preparedQuery);

			switch (engineName) {
			case "Google": {
				googleResults = googleParsingService.parse(apiResponse);
				model.addAttribute("googleResults", googleResults);
				break;
			}
			case "Springer": {
				springerResults = springerParsingService.parse(apiResponse);
				model.addAttribute("springerResults", springerResults);
				break;
			}
			case "Crossref": {
				crossrefResults = crossrefParsingService.parse(apiResponse);
				model.addAttribute("crossrefResults", crossrefResults);
				break;
			}
			case "Amazon": {
				String amazonRequestUrl = amazonBookSearchService.prepareRequestUrl(preparedQuery);
				amazonResults = amazonParsingService.parse(amazonRequestUrl);
				model.addAttribute("amazonResults", amazonResults);
				break;
			}
			}

			/*
			 * request.getSession().setAttribute("rssNewsParam", rssNews);
			 * request.getSession().setAttribute("currentRssName", rssName);
			 */

			/*
			 * for (Resource api : foundApi) { String preparedQuery =
			 * apiRequestPreparation.prepareQuery(api, searchedWords);
			 * apiResponse = apiRequestPreparation.passRequestToApi(api,
			 * preparedQuery); switch (api.getName()) { case "Google": {
			 * googleResults = googleParsingService.parse(apiResponse);
			 * model.addAttribute("googleResults", googleResults); break; } case
			 * "Springer": { springerResults =
			 * springerParsingService.parse(apiResponse);
			 * model.addAttribute("springerResults", springerResults); break; }
			 * case "Crossref": { crossrefResults =
			 * crossrefParsingService.parse(apiResponse);
			 * model.addAttribute("crossrefResults", crossrefResults); break; }
			 * case "Amazon": { String amazonRequestUrl =
			 * amazonBookSearchService.prepareRequestUrl(preparedQuery);
			 * amazonResults = amazonParsingService.parse(amazonRequestUrl);
			 * model.addAttribute("amazonResults", amazonResults); break; } }
			 * 
			 * }
			 */
		}
		/*
		 * String amazonRequestUrl =
		 * amazonBookSearchService.prepareRequestUrl(preparedQuery);
		 * List<AmazonBook> books =
		 * amazonParsingService.parse(amazonRequestUrl);
		 */

		return "searchResult";
	}

}
