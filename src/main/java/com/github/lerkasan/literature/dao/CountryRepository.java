package com.github.lerkasan.literature.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.github.lerkasan.literature.entity.Country;

public interface CountryRepository extends PagingAndSortingRepository<Country, Short> {
	@Query("select c from country c where c.name = :name")
    Country findByName(@Param("name") String name);
	
	@Query("select c from country c where c.id = :id")
    Country findById(@Param("id") short id);
}
