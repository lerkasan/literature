package com.github.lerkasan.literature.service.impl;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.lerkasan.literature.dao.CountryRepository;
import com.github.lerkasan.literature.entity.Country;
import com.github.lerkasan.literature.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {
	@Inject
	private CountryRepository countryRepository;

	public CountryServiceImpl() {
	}

	@Override
	public Country add(Country country) {
		return countryRepository.save(country);
	}

	@Override
	public void delete(short id) {
		countryRepository.delete(id);

	}

	@Override
	public Country getById(short id) {
		return countryRepository.findById(id);
	}

	@Override
	@Transactional
	public Country save(Country country) {
		return countryRepository.save(country);
	}

	@Override
	public Page<Country> getAll(Pageable pageable) {
		return countryRepository.findAll(pageable);
	}

}
