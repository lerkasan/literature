package com.github.lerkasan.literature.service.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.lerkasan.literature.controller.Messages;
import com.github.lerkasan.literature.dao.ItemToReadRepository;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.service.ItemToReadService;

@Service
public class ItemToReadServiceImpl implements ItemToReadService {

	@Inject
	private ItemToReadRepository itemToReadRepository;
	
	@PersistenceContext
    private EntityManager em;
	
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
	public Page<ItemToRead> getAll(int pageNumber) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, Messages.PAGE_SIZE, Sort.Direction.ASC, "name");
		return itemToReadRepository.findAll(pageRequest);
	}

	@Override
	public ItemToRead getByUrl(String url) {
		return itemToReadRepository.findByUrl(url);
	}

}
