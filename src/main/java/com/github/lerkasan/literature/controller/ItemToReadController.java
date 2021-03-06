package com.github.lerkasan.literature.controller;

import java.time.LocalDate;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.github.lerkasan.literature.entity.User;
import com.github.lerkasan.literature.service.ItemToReadService;
import com.github.lerkasan.literature.service.UserService;

@Controller
@Scope("session")
@RequestMapping("/item")
public class ItemToReadController {
	@Inject
	ItemToReadService itemToReadService;

	@Inject
	UserService userService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String getFirstPage(ModelMap model, HttpSession session) {
		Page<ItemToRead> page = null;
		page = itemToReadService.getAll(1);
		session.removeAttribute("selectedType");
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("items", page);
		session.setAttribute("items", page);
		model.addAttribute("itemTypes", ItemType.values());
		model.addAttribute("accessTypes", ItemAccessType.values());
		return "itemList";
	}

	@RequestMapping(value = "/addToLibrary", method = { RequestMethod.POST })
	public String addToLibraryPage(ModelMap model,
			@RequestParam(value = "selectedItems", required = false) int[] selectedItemsIds, HttpSession session) {
		@SuppressWarnings("unchecked")
		Page<ItemToRead> page = (Page<ItemToRead>) session.getAttribute("items");
		String message = "";
		User currentUser = userService.getById(userService.USER_ID);
		if (selectedItemsIds != null) {
			message = currentUser.addSelectedToLibrary(page.getContent(), selectedItemsIds);
			userService.save(currentUser);
		}
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("items", page);
		model.addAttribute("message", message);
		model.addAttribute("itemTypes", ItemType.values());
		model.addAttribute("accessTypes", ItemAccessType.values());
		return "itemList";
	}

	@RequestMapping(value = "/list/{pageNumber}", method = { RequestMethod.GET, RequestMethod.POST })
	public String getPage(@PathVariable Integer pageNumber, ModelMap model, HttpSession session,
			@RequestParam(value = "typeSelection", required = false) String typeSelection,
			@RequestParam(value = "accessSelection", required = false) String accessSelection,
			@RequestParam(value = "keywordSelection", required = false) String keywordSelection,
			@RequestParam(value = "periodSelection", required = false) String periodSelection) {
		Page<ItemToRead> page = null;
		String searchKeyword = "";
		LocalDate locDate;
		int selectedPeriod = 36500;
		if ((periodSelection != null) && (periodSelection != "")) {
			selectedPeriod = Integer.valueOf(periodSelection);
			locDate = LocalDate.now().minusDays(selectedPeriod);
		} else {
			locDate = LocalDate.now().minusDays(36500);
		}
		model.addAttribute("selectedPeriod", selectedPeriod);
		if (keywordSelection != null) {
			searchKeyword = keywordSelection.replaceAll("%20", " ");
		}
		model.addAttribute("selectedKeyword", searchKeyword);
		if ((typeSelection == null) && (accessSelection == null)) {
			// page = itemToReadService.getAll(pageNumber);
			page = itemToReadService.getAllByKeyword(keywordSelection, locDate, pageNumber);
		} else {

			if ((typeSelection != null) && !typeSelection.equals("select_option")) {
				// page =
				// itemToReadService.getByItemType(ItemType.valueOf(typeSelection),
				// pageNumber);
				// page =
				// itemToReadService.getByKeywordAndItemType(keywordSelection,
				// ItemType.valueOf(typeSelection), pageNumber);
				page = itemToReadService.getByKeywordAndItemType(keywordSelection, ItemType.valueOf(typeSelection),
						locDate, pageNumber);
				model.addAttribute("selectedType", typeSelection);
			}

			if ((accessSelection != null) && !accessSelection.equals("select_option")) {
				// page =
				// itemToReadService.getByAccessType(ItemAccessType.valueOf(accessSelection),
				// pageNumber);
				page = itemToReadService.getByKeywordAndAccessType(keywordSelection,
						ItemAccessType.valueOf(accessSelection), locDate, pageNumber);
				model.addAttribute("selectedAccess", accessSelection);
			}

			if ((typeSelection != null) && !typeSelection.equals("select_option") && (accessSelection != null)
					&& !accessSelection.equals("select_option")) {
				// page =
				// itemToReadService.getByItemTypeAndAccessType(ItemType.valueOf(typeSelection),
				// ItemAccessType.valueOf(accessSelection), pageNumber);
				page = itemToReadService.getByKeywordAndItemTypeAndAccessType(keywordSelection,
						ItemType.valueOf(typeSelection), ItemAccessType.valueOf(accessSelection), locDate, pageNumber);
				model.addAttribute("selectedType", typeSelection);
				model.addAttribute("selectedAccess", accessSelection);
			}

			if ((typeSelection != null) && typeSelection.equals("select_option") && (accessSelection != null)
					&& accessSelection.equals("select_option")) {
				// page = itemToReadService.getAll(pageNumber);
				page = itemToReadService.getAllByKeyword(keywordSelection, locDate, pageNumber);
			}

		}
		model.addAttribute("items", page);
		session.setAttribute("items", page);
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("itemTypes", ItemType.values());
		model.addAttribute("accessTypes", ItemAccessType.values());
		return "itemList";
	}
}
