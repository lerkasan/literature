package com.github.lerkasan.literature.dao;

import javax.persistence.NamedNativeQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.service.impl.AuthorTotalItemsResult;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Integer> {
	@Query("select a from author a where a.familyName = :familyName")
	Author findByFamilyName(@Param("familyName") String familyName);
	
	@Query("select a from author a where a.familyName = :familyName and a.givenName = :givenName")
	Author findByFullName(@Param("givenName") String givenName, @Param("familyName") String familyName);
	
	@Query("select a from author a where a.id = :id")
	Author findById(@Param("id") Integer id);
	
}