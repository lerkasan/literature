package com.github.lerkasan.literature.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.github.lerkasan.literature.entity.Comment;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {
	
	@Query("select c from comment c where c.id = :id")
    Comment findById(@Param("id") Integer id);
}
