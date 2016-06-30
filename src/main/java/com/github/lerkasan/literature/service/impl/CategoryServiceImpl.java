package com.github.lerkasan.literature.service.impl;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.lerkasan.literature.dao.CategoryRepository;
import com.github.lerkasan.literature.entity.Category;
import com.github.lerkasan.literature.service.CategoryService;

@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService {

	@Inject
	private CategoryRepository categoryRepository;

	public CategoryServiceImpl() {
	}

	@Override
	public Category add(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void delete(int id) {
		categoryRepository.delete(id);
	}

	@Override
	public Category getByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	@Transactional
	public Page<Category> getAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public Category getById(int id) {
		return categoryRepository.findById(id);
	}

}
