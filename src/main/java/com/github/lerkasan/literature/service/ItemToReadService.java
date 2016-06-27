package com.github.lerkasan.literature.service;

import org.springframework.data.domain.Page;

import com.github.lerkasan.literature.entity.ItemToRead;

public interface ItemToReadService {
	ItemToRead add(ItemToRead itemToRead);
	void delete(int id);
	ItemToRead getById(int id);
	ItemToRead save(ItemToRead itemToRead);
	//void save(ItemToRead itemToRead);
	//Page<ItemToRead> getAll(Pageable pageable);
	ItemToRead getByUrl(String url);
	Page<ItemToRead> getAll(int pageNumber);
}
