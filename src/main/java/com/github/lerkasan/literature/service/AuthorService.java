package com.github.lerkasan.literature.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.entity.Resource;
import com.github.lerkasan.literature.service.impl.AuthorTotalItemsResult;

public interface AuthorService {
	Author add(Author author);
	void delete(int id);
	Author getByFamilyName(String familyName);
	Author getByFullName(String givenName, String familyName);
	Author getById(int id);
	Author save(Author author);
	Page<Author> getAll(Pageable pageable);
	public String[] divideFullName(String fullName);
	Page<Author> getAll(int i);
	//List<AuthorTotalItemsResult> getAll();
}
