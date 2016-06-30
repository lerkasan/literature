package com.github.lerkasan.literature.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.service.AuthorService;

@Controller
@RequestMapping("/author")
public class AuthorController {
	@Inject
	AuthorService authorService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getFirstPage(ModelMap model, HttpSession session) {
		Page<Author> page = authorService.getAll(1);
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("authors", page);
		return "authorList";
	}

	@RequestMapping(value = "/list/{pageNumber}", method = RequestMethod.GET)
	public String getPage(@PathVariable Integer pageNumber, ModelMap model) {
		Page<Author> page = authorService.getAll(pageNumber);
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("authors", page);
		return "authorList";
	}

	@RequestMapping(value = "/letter/{letter}", method = RequestMethod.GET)
	public String getAuthorsByLetter(@PathVariable String letter, ModelMap model) {
		List<Author> page = authorService.getByFirstLetter(letter);
		model.addAttribute("authors", page);
		return "letterAuthorList";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getAuthorsItems(@PathVariable Integer id, ModelMap model) {
		Author author = authorService.getById(id);
		Page<Author> page = authorService.getAll(1);
		model.addAttribute("author", author);
		model.addAttribute("authors", page);
		model.addAttribute("items", author.getItemsToRead());
		int current = page.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, page.getTotalPages());
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		return "authorList";
	}
}
