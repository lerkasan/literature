package com.github.lerkasan.literature.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.github.lerkasan.literature.entity.Author;

public interface AuthorService {
	Author add(Author author);
	void delete(int id);
	Author getByFamilyName(String familyName);
	Author getByFullName(String givenName, String familyName);
	Author getById(int id);
	Author save(Author author);
	Page<Author> getAll(Pageable pageable);
}
