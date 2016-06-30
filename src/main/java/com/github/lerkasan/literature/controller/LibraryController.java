package com.github.lerkasan.literature.controller;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.lerkasan.literature.entity.User;
import com.github.lerkasan.literature.service.UserService;

@Controller
@Scope("session")
@RequestMapping("/library")
public class LibraryController {

	@Inject
	UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getFirstPage(ModelMap model) {
		User currentUser = userService.getById(userService.USER_ID);
		model.addAttribute("items", currentUser.getLibrary());
		return "myLibrary";
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String removePage(ModelMap model,
			@RequestParam(value = "selectedItems", required = false) int[] selectedItemsIds) {
		User currentUser = userService.getById(userService.USER_ID);
		if (selectedItemsIds != null) {
			int shift = 0;
			for (int i : selectedItemsIds) {
				currentUser.removeFromLibrary(currentUser.getLibrary().get(i - shift));
				shift++;
			}
			userService.save(currentUser);
			model.addAttribute("items", currentUser.getLibrary());
		}
		return "myLibrary";
	}
}
