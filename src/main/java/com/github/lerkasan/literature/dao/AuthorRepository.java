package com.github.lerkasan.literature.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.github.lerkasan.literature.entity.Author;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Integer> {
	@Query("select a from author a where a.familyName = :familyName")
	Author findByFamilyName(@Param("familyName") String familyName);
	
	@Query("select a from author a where a.familyName = :familyName and a.givenName = :givenName")
	Author findByFullName(@Param("givenName") String givenName, @Param("familyName") String familyName);
	
	@Query("select a from author a where a.id = :id")
	Author findById(@Param("id") Integer id);
}
