package com.github.lerkasan.literature.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.github.lerkasan.literature.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
	@Query("select c from category c where c.name = :name")
	Category findByName(@Param("name") String name);
	
	@Query("select c from category c where c.id = :id")
	Category findById(@Param("id") Integer id);
}
