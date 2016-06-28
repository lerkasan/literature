package com.github.lerkasan.literature.controller;

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
import com.github.lerkasan.literature.service.ItemToReadService;

@Controller
@Scope("session")
@RequestMapping("/item")
public class ItemToReadController {
	@Inject
	ItemToReadService itemToReadService;

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
		model.addAttribute("itemTypes", ItemType.values());
		model.addAttribute("accessTypes", ItemAccessType.values());
		return "itemList";
	}

	@RequestMapping(value = "/list/{pageNumber}", method = { RequestMethod.GET, RequestMethod.POST })
	public String getPage(@PathVariable Integer pageNumber, ModelMap model,
			@RequestParam(value = "typeSelection", required = false) String typeSelection,
			@RequestParam(value = "accessSelection", required = false) String accessSelection, HttpSession session) {
		Page<ItemToRead> page = null;
		String selectedType;
		String selectedAccess;
		

		if ((typeSelection == null) || (typeSelection.equals("select_option"))
				&& ((accessSelection == null) || (accessSelection.equals("select_option")))) {
			page = itemToReadService.getAll(pageNumber);
			session.removeAttribute("selectedType");
			session.removeAttribute("selectedAccess");
			model.addAttribute("items", page);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("itemTypes", ItemType.values());
			model.addAttribute("accessTypes", ItemAccessType.values());
			model.addAttribute("type", typeSelection);
			return "itemList";
		}

		if ((session.getAttribute("selectedType") != null) && (session.getAttribute("selectedAccess") != null)) {
			selectedType = session.getAttribute("selectedType").toString();
			selectedAccess = session.getAttribute("selectedAccess").toString();
			page = itemToReadService.getByItemTypeAndAccessType(ItemType.valueOf(selectedType),
					ItemAccessType.valueOf(selectedAccess), pageNumber);
			model.addAttribute("items", page);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("itemTypes", ItemType.values());
			model.addAttribute("accessTypes", ItemAccessType.values());
			model.addAttribute("type", typeSelection);
			return "itemList";
		}

		if ((session.getAttribute("selectedType") != null)
				&& ((accessSelection != null) || (!accessSelection.equals("select_option")))) {
			selectedType = session.getAttribute("selectedType").toString();
			page = itemToReadService.getByItemTypeAndAccessType(ItemType.valueOf(selectedType),
					ItemAccessType.valueOf(accessSelection), pageNumber);
			model.addAttribute("items", page);
			session.setAttribute("selectedAccess", accessSelection);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("itemTypes", ItemType.values());
			model.addAttribute("accessTypes", ItemAccessType.values());
			model.addAttribute("type", typeSelection);
			return "itemList";
		}

		if ((session.getAttribute("selectedAccess") != null) && !typeSelection.equals("select_option")) {
			selectedAccess = session.getAttribute("selectedAccess").toString();
			page = itemToReadService.getByItemTypeAndAccessType(ItemType.valueOf(typeSelection),
					ItemAccessType.valueOf(selectedAccess), pageNumber);
			model.addAttribute("items", page);
			session.setAttribute("selectedType", accessSelection);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("itemTypes", ItemType.values());
			model.addAttribute("accessTypes", ItemAccessType.values());
			model.addAttribute("type", typeSelection);
			return "itemList";
		}

		if (!typeSelection.equals("select_option") && !accessSelection.equals("select_option")) {
			page = itemToReadService.getByItemTypeAndAccessType(ItemType.valueOf(typeSelection),
					ItemAccessType.valueOf(accessSelection), pageNumber);
			session.setAttribute("selectedType", typeSelection);
			session.setAttribute("accessType", accessSelection);
			model.addAttribute("items", page);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("itemTypes", ItemType.values());
			model.addAttribute("accessTypes", ItemAccessType.values());
			model.addAttribute("type", typeSelection);
			return "itemList";
		}

		if (session.getAttribute("selectedAccess") != null) {
			selectedAccess = session.getAttribute("selectedAccess").toString();
			page = itemToReadService.getByAccessType(ItemAccessType.valueOf(selectedAccess), pageNumber);
		}

		if (session.getAttribute("selectedType") != null) {
			selectedType = session.getAttribute("selectedType").toString();
			page = itemToReadService.getByItemType(ItemType.valueOf(selectedType), pageNumber);
		}

		if ((typeSelection == null) || (typeSelection.equals("select_option"))) {
			page = itemToReadService.getAll(pageNumber);
			session.removeAttribute("selectedType");
		} else {
			page = itemToReadService.getByItemType(ItemType.valueOf(typeSelection), pageNumber);
			session.setAttribute("selectedType", typeSelection);
		}

		if ((accessSelection == null) || (accessSelection.equals("select_option"))) {
			page = itemToReadService.getAll(pageNumber);
			session.removeAttribute("selectedAccess");
		} else {
			page = itemToReadService.getByAccessType(ItemAccessType.valueOf(accessSelection), pageNumber);
			session.setAttribute("selectedAccess", accessSelection);
		}
		model.addAttribute("items", page);
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("itemTypes", ItemType.values());
		model.addAttribute("accessTypes", ItemAccessType.values());
		model.addAttribute("type", typeSelection);
		return "itemList";
	}

	/*
	 * @RequestMapping(value = "/BOOK/{pageNumber}", method =
	 * {RequestMethod.GET, RequestMethod.POST}) public String
	 * getBooks(@PathVariable Integer pageNumber, ModelMap model,
	 * HttpServletRequest request) { Page<ItemToRead> page =
	 * itemToReadService.getByItemType(ItemType.BOOK,pageNumber); int current =
	 * page.getNumber() + 1; int begin = Math.max(1, current - 5); int end =
	 * Math.min(begin + 10, page.getTotalPages());
	 * model.addAttribute("beginIndex", begin); model.addAttribute("endIndex",
	 * end); model.addAttribute("currentIndex", current);
	 * model.addAttribute("items", page); model.addAttribute("itemTypes",
	 * ItemType.values()); model.addAttribute("accessTypes",
	 * ItemAccessType.values()); return "itemList/"+ItemType.BOOK; }
	 */

	/*
	 * @RequestMapping(value = "/INTERNET_ARTICLE/{pageNumber}", method =
	 * {RequestMethod.GET, RequestMethod.POST}) public String
	 * getInternetArticles(@PathVariable Integer pageNumber, ModelMap model,
	 * HttpServletRequest request) { Page<ItemToRead> page =
	 * itemToReadService.getByItemType(ItemType.INTERNET_ARTICLE,pageNumber);
	 * int current = page.getNumber() + 1; int begin = Math.max(1, current - 5);
	 * int end = Math.min(begin + 10, page.getTotalPages());
	 * model.addAttribute("beginIndex", begin); model.addAttribute("endIndex",
	 * end); model.addAttribute("currentIndex", current);
	 * model.addAttribute("items", page); model.addAttribute("itemTypes",
	 * ItemType.values()); model.addAttribute("accessTypes",
	 * ItemAccessType.values()); return "itemList/"+ItemType.INTERNET_ARTICLE; }
	 */
}
