package com.github.lerkasan.literature.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.lerkasan.literature.entity.Category;

public interface CategoryService {
	Category add(Category category);

	void delete(int id);

	Category getByName(String name);

	Category getById(int id);

	Category save(Category category);

	Page<Category> getAll(Pageable pageable);
}
