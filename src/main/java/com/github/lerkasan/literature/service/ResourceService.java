package com.github.lerkasan.literature.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.github.lerkasan.literature.entity.Resource;

public interface ResourceService {
	Resource add(Resource resource);
	void delete(int id);
	Resource findByName(String name);
	Resource getById(int id);
	Resource save(Resource  resource);
	Page<Resource> getAll(int pageNumber);
	List<Resource> getByResponseFormat(String format);
	List<Resource> getByResponseFormatNot(String format);

}
