package com.github.lerkasan.literature.controller;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.lerkasan.literature.dao.ItemToReadRepository;
import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.github.lerkasan.literature.entity.Resource;
import com.github.lerkasan.literature.service.ItemToReadService;

@Controller
@RequestMapping("/item")
public class ItemToReadController {
	@Inject
	ItemToReadService itemToReadService;
	
	@RequestMapping(value = "/list/{pageNumber}", method = {RequestMethod.GET, RequestMethod.POST})
	public String getPage(@PathVariable Integer pageNumber, ModelMap model, @RequestParam(value = "typeSelection", required = false) ItemType itemType) {
		Page<ItemToRead> page = null; {
		};
		if (itemType == null) {
			page = itemToReadService.getAll(pageNumber);
		} else {
			page =	itemToReadService.getAllByItemType(itemType,pageNumber);
		}
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("items", page);
		model.addAttribute("itemTypes", ItemType.values());
		model.addAttribute("accessTypes", ItemAccessType.values());
		return "itemList";
	}
	

}
