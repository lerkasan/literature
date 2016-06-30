package com.github.lerkasan.literature.dao;

import java.util.List;

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

	@Query("select a from author a where a.familyName like :letter")
	List<Author> getByFirstLetter(@Param("letter") String letter);

}