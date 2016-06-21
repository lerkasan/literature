package com.github.lerkasan.literature.controller;

import java.util.List;

import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.lerkasan.literature.dao.ResourceRepository;
import com.github.lerkasan.literature.entity.Resource;
import com.github.lerkasan.literature.parser.ApiRequestPreparationService;
import com.github.lerkasan.literature.parser.CrossrefApiJson;
import com.github.lerkasan.literature.parser.GoogleApiJson;
import com.github.lerkasan.literature.parser.ParsingService;
import com.github.lerkasan.literature.parser.SpringerApiJson;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Inject
	ApiRequestPreparationService apiRequestPreparation;

	@Inject
	ResourceRepository resourceRepository;

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
	@RequestMapping(method = RequestMethod.POST)
	// @RequestMapping(value = "/api", method = RequestMethod.POST)
	public String search(@RequestParam("searchQuery") String searchQuery, ModelMap model) {

		List<GoogleApiJson> googleResults;
		List<SpringerApiJson> springerResults;
		List<CrossrefApiJson> crossrefResults;

		if ((searchQuery != null) && (searchQuery != "")) {
			String[] searchedWords = apiRequestPreparation.deleteSpecialsAndSplit(searchQuery);
			// List<Resource> resources = new ArrayList<>();
			Iterable<Resource> foundApi = resourceRepository.findByResponseFormatNot("rss");
			// foundResources.forEach(resources::add);

			// Resource api = resourceRepository.findByName("Google");

			/*
			 * String preparedQuery = apiRequestPreparation.prepareQuery(api,
			 * searchedWords); String apiResponse =
			 * apiRequestPreparation.passRequestToApi(api, preparedQuery);
			 * googleResults = googleParsingService.parse(apiResponse);
			model.addAttribute("googleResults", googleResults); */
			
			for (Resource api : foundApi) {
				String preparedQuery = apiRequestPreparation.prepareQuery(api, searchedWords);
				String apiResponse = apiRequestPreparation.passRequestToApi(api, preparedQuery);
				switch (api.getName()) {
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
				}

			}
		}
		return "searchResult";
	}

}
