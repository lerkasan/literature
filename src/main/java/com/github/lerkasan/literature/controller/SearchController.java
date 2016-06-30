package com.github.lerkasan.literature.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.lerkasan.literature.dao.ResourceRepository;
import com.github.lerkasan.literature.entity.Resource;
import com.github.lerkasan.literature.parser.AmazonItem;
import com.github.lerkasan.literature.parser.ApiRequestPreparationService;
import com.github.lerkasan.literature.parser.ConvertableToItemToRead;
import com.github.lerkasan.literature.parser.CrossrefApiJson;
import com.github.lerkasan.literature.parser.GoogleApiJson;
import com.github.lerkasan.literature.parser.GoogleBookJson;
import com.github.lerkasan.literature.parser.ParsingService;
import com.github.lerkasan.literature.parser.SpringerApiJson;
import com.github.lerkasan.literature.parser.impl.ApiRequestPreparationServiceImpl;
import com.github.lerkasan.literature.parser.impl.amazon.AmazonApiRequestPreparationService;
import com.github.lerkasan.literature.service.ItemToReadService;

@Controller
@Scope("session")
@RequestMapping("/search")
public class SearchController {

	@Inject
	ItemToReadService itemToReadService;

	@Inject
	ApiRequestPreparationService apiRequestPreparation;

	@Inject
	ResourceRepository resourceRepository;

	@Inject
	AmazonApiRequestPreparationService amazonBookSearchService;

	@Inject
	@Qualifier("AmazonParsingService")
	ParsingService amazonParsingService;

	@Inject
	@Qualifier("GoogleParsingService")
	ParsingService googleParsingService;

	@Inject
	@Qualifier("GoogleHtmlParsingService")
	ParsingService googleHtmlParsingService;

	@Inject
	@Qualifier("GoogleBookParsingService")
	ParsingService googleBookParsingService;

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
		List<GoogleBookJson> googleBookResults;
		List<SpringerApiJson> springerResults;
		List<CrossrefApiJson> crossrefResults;
		List<AmazonItem> amazonResults;
		String apiResponse = "";
		model.addAttribute("message", "");

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
			// Iterable<Resource> foundApi =
			// resourceRepository.findByResponseFormatNot("rss");

			Resource searchApi = resourceRepository.findByName(engineName);
			String preparedQuery = apiRequestPreparation.prepareQuery(searchApi, searchedWords);
			apiResponse = apiRequestPreparation.passRequestToApi(searchApi, preparedQuery);
			model.addAttribute("searchQuery", searchQuery);
			model.addAttribute("currentEngineName", engineName);
			request.getSession().setAttribute("currentEngineName", engineName);

			switch (engineName) {
			case ApiRequestPreparationServiceImpl.GOOGLE_API: {
				googleResults = (List<GoogleApiJson>) googleParsingService.parse(apiResponse);
				model.addAttribute("googleResults", googleResults);
				request.getSession().setAttribute("googleResults", googleResults);
				break;
			}
			case ApiRequestPreparationServiceImpl.GOOGLE_BOOKS: {
				googleBookResults = (List<GoogleBookJson>) googleBookParsingService.parse(apiResponse);
				model.addAttribute("googleBookResults", googleBookResults);
				request.getSession().setAttribute("googleBookResults", googleBookResults);
				break;
			}
			case ApiRequestPreparationServiceImpl.GOOGLE_SITE: {
				List<GoogleApiJson> googleHtmlResults = (List<GoogleApiJson>) googleHtmlParsingService
						.parse(apiResponse);
				model.addAttribute("googleHtmlResults", googleHtmlResults);
				request.getSession().setAttribute("googleHtmlResults", googleHtmlResults);
				break;
			}
			case ApiRequestPreparationServiceImpl.SPRINGER: {
				springerResults = (List<SpringerApiJson>) springerParsingService.parse(apiResponse);
				model.addAttribute("springerResults", springerResults);
				request.getSession().setAttribute("springerResults", springerResults);
				break;
			}
			case ApiRequestPreparationServiceImpl.CROSSREF: {
				crossrefResults = (List<CrossrefApiJson>) crossrefParsingService.parse(apiResponse);
				model.addAttribute("crossrefResults", crossrefResults);
				request.getSession().setAttribute("crossrefResults", crossrefResults);
				break;
			}
			case ApiRequestPreparationServiceImpl.AMAZON: {
				String amazonRequestUrl = amazonBookSearchService.prepareRequestUrl(preparedQuery);
				amazonResults = (List<AmazonItem>) amazonParsingService.parse(amazonRequestUrl);
				model.addAttribute("amazonResults", amazonResults);
				request.getSession().setAttribute("amazonResults", amazonResults);
				break;
			}
			}
		}
		return "searchResult";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save/{engineName}", method = RequestMethod.POST)
	public String saveItem(@PathVariable String engineName,
			@RequestParam(value = "selectedItems", required = false) int[] selectedItemsIds, ModelMap model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {

		String message = "";
		List<ConvertableToItemToRead> itemsToRead = null;
		List<Resource> searchEngineList = (List<Resource>) request.getSession().getAttribute("searchEngineList");
		model.addAttribute("searchEngineList", searchEngineList);

		switch (engineName) {
		case ApiRequestPreparationServiceImpl.GOOGLE_API: {
			itemsToRead = (List<ConvertableToItemToRead>) request.getSession().getAttribute("googleResults");
			message = googleParsingService.save(selectedItemsIds, itemsToRead);
			break;
		}
		case ApiRequestPreparationServiceImpl.GOOGLE_SITE: {
			itemsToRead = (List<ConvertableToItemToRead>) request.getSession().getAttribute("googleHtmlResults");
			message = googleHtmlParsingService.save(selectedItemsIds, itemsToRead);
			break;
		}
		case ApiRequestPreparationServiceImpl.GOOGLE_BOOKS: {
			itemsToRead = (List<ConvertableToItemToRead>) request.getSession().getAttribute("googleBookResults");
			message = googleBookParsingService.save(selectedItemsIds, itemsToRead);
			break;
		}
		case ApiRequestPreparationServiceImpl.SPRINGER: {
			itemsToRead = (List<ConvertableToItemToRead>) request.getSession().getAttribute("springerResults");
			message = springerParsingService.save(selectedItemsIds, itemsToRead);
			break;
		}
		case ApiRequestPreparationServiceImpl.CROSSREF: {
			itemsToRead = (List<ConvertableToItemToRead>) request.getSession().getAttribute("crossrefResults");
			message = crossrefParsingService.save(selectedItemsIds, itemsToRead);
			break;
		}
		case ApiRequestPreparationServiceImpl.AMAZON: {
			itemsToRead = (List<ConvertableToItemToRead>) request.getSession().getAttribute("amazonResults");
			message = amazonParsingService.save(selectedItemsIds, itemsToRead);
			break;
		}
		}
		model.addAttribute("itemsToRead", itemsToRead);
		request.getSession().setAttribute("message", message);
		model.addAttribute("message", message);
		return "searchResult/" + engineName;
	}
}
