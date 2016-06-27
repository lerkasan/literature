package com.github.lerkasan.literature.controller;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.service.ItemToReadService;

@Controller
@RequestMapping("/item")
public class ItemToReadController {
	@Inject
	ItemToReadService itemToReadService;
	
	@RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.GET)
	public String getPage(@PathVariable Integer pageNumber, ModelMap model) {
		Page<ItemToRead> page = itemToReadService.getAll(pageNumber);

		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("resources", page);
		return "resourceList";
	}
	

}
