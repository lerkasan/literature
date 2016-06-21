package com.github.lerkasan.literature.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.lerkasan.literature.dao.ResourceRepository;
import com.github.lerkasan.literature.entity.Resource;
import com.github.lerkasan.literature.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {
	private static final int PAGE_SIZE = 50;

	@Inject
	private ResourceRepository resourceRepository;

	public ResourceServiceImpl() {
	}

	@Override
	public Resource add(Resource resource) {
		return resourceRepository.save(resource);
	}

	@Override
	public void delete(int id) {
		resourceRepository.delete(id);
	}

	@Override
	public Resource findByName(String name) {
		return resourceRepository.findByName(name);
	}

	@Override
	public Resource getById(int id) {
		return resourceRepository.findById(id);
	}

	@Override
	public Page<Resource> getAll(int pageNumber) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "name");
		return resourceRepository.findAll(pageRequest);
	}

	@Override
	@Transactional
	public Resource save(Resource resource) {
		return resourceRepository.save(resource);
	}

	@Override
	public List<Resource> getByResponseFormat(String format) {
		return resourceRepository.findByResponseFormat(format);
	}

	@Override
	public List<Resource> getByResponseFormatNot(String format) {
		return resourceRepository.findByResponseFormatNot(format);
	}

}