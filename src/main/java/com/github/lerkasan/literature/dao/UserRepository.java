package com.github.lerkasan.literature.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.github.lerkasan.literature.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	
	@Query("select u from user u where u.id = :id")
	User findById(@Param("id") Integer id);

}
