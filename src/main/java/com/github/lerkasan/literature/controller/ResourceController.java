package com.github.lerkasan.literature.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.lerkasan.literature.entity.Resource;
import com.github.lerkasan.literature.service.ResourceService;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	@Inject
	ResourceService resourceService;

	@RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.GET)
	public String getPage(@PathVariable Integer pageNumber, ModelMap model) {
		Page<Resource> page = resourceService.getAll(pageNumber);

		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("resources", page);
		return "resourceList";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newResource(ModelMap model) {
		model.addAttribute("resource", new Resource());
		return "newResource";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addResource(@Valid @ModelAttribute("resource") Resource resource, BindingResult bindingResult,
			ModelMap model) {
		if (bindingResult.hasErrors()) {
			return "newResource";
		}
		resourceService.save(resource);
		return getPage(1, model);
	}

}