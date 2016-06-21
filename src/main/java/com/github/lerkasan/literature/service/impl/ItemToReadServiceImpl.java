package com.github.lerkasan.literature.service.impl;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.lerkasan.literature.dao.ItemToReadRepository;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.service.ItemToReadService;

@Service
public class ItemToReadServiceImpl implements ItemToReadService {

	@Inject
	private ItemToReadRepository itemToReadRepository;
	
	public ItemToReadServiceImpl() {
	}

	@Override
	public ItemToRead add(ItemToRead itemToRead) {
		return itemToReadRepository.save(itemToRead);
	}

	@Override
	public void delete(int id) {
		itemToReadRepository.delete(id);

	}

	@Override
	public ItemToRead getById(int id) {
		return itemToReadRepository.findById(id);
	}

	@Override
	@Transactional
	public ItemToRead save(ItemToRead itemToRead) {
		return itemToReadRepository.save(itemToRead);
	}

	@Override
	public Page<ItemToRead> getAll(Pageable pageable) {
		return itemToReadRepository.findAll(pageable);
	}

	@Override
	public ItemToRead getByUrl(String url) {
		return itemToReadRepository.findByUrl(url);
	}

}
