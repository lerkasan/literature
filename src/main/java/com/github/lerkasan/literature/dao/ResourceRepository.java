package com.github.lerkasan.literature.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.github.lerkasan.literature.entity.Resource;

public interface ResourceRepository extends PagingAndSortingRepository<Resource, Integer> {
	/*@Query("select r from resource r where r.name = :name")
	Resource findByName(@Param("name") String name);*/

	Resource findByName(String name);
	
	@Query("select r from resource r where r.id = :id")
	Resource findById(@Param("id") Integer id);
	
	List<Resource> findByResponseFormat(String format);
	List<Resource> findByResponseFormatNot(String format);
}
