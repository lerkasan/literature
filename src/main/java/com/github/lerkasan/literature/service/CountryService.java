package com.github.lerkasan.literature.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.github.lerkasan.literature.entity.Country;

public interface CountryService {
	Country add(Country country);
	void delete(short id);
	Country getById(short id);
	Country save(Country country);
	Page<Country> getAll(Pageable pageable);
}
