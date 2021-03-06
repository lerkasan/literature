package com.github.lerkasan.literature.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.lerkasan.literature.controller.Messages;
import com.github.lerkasan.literature.dao.AuthorRepository;
import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.service.AuthorService;

@Service("AuthorService")
public class AuthorServiceImpl implements AuthorService {

	@PersistenceContext
	private EntityManager manager;

	@Inject
	private AuthorRepository authorRepository;

	public AuthorServiceImpl() {
	}

	@Override
	public Author add(Author author) {
		return authorRepository.save(author);
	}

	@Override
	public void delete(int id) {
		authorRepository.delete(id);

	}

	@Override
	public Author getByFamilyName(String familyName) {
		return authorRepository.findByFamilyName(familyName);
	}

	@Override
	public Author getByFullName(String givenName, String familyName) {
		return authorRepository.findByFullName(givenName, familyName);
	}

	@Override
	public Author getById(int id) {
		return authorRepository.findById(id);
	}

	@Override
	@Transactional
	public Author save(Author author) {
		if ((author.getGivenName() != null) && (author.getFamilyName() != null)) {
			return authorRepository.save(author);
		} else
			return null;
	}

	@Override
	public Page<Author> getAll(Pageable pageable) {
		return authorRepository.findAll(pageable);
	}

	@Override
	public String[] divideFullName(String fullName) {
		String[] result = new String[2];
		if ((fullName != null) && (fullName != "")) {
			String[] fullNameParts = fullName.split(" ", 2);
			if (fullName.indexOf(" ") != fullName.lastIndexOf(" ")) {
				fullName.replaceFirst(" ", "_");
				fullNameParts = fullName.split(" ", 2);
			}
			if (fullNameParts.length > 1) {
				result[0] = fullNameParts[0];
				result[1] = fullNameParts[1];
			} else {
				result[0] = "";
				result[1] = fullNameParts[0];
			}
		}
		return result;
	}

	@Override
	public Page<Author> getAll(int pageNumber) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, Messages.PAGE_SIZE, Sort.Direction.ASC, "familyName");
		return authorRepository.findAll(pageRequest);
		// Page<AuthorTotalItemsResult> findAllWithItemsTotal(Pageable
		// pageable);
		// List<AuthorTotalItemsResult> results =
		// manager.createNamedQuery("findAuthorsWithItemsTotal",
		// AuthorTotalItemsResult.class).getResultList();
	}

	@Override
	public List<Author> getByFirstLetter(String letter) {
		return authorRepository.getByFirstLetter(letter + "%");
	}
}
