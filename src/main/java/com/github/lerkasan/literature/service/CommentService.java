package com.github.lerkasan.literature.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.github.lerkasan.literature.entity.Comment;

public interface CommentService {
	Comment add(Comment comment);
	void delete(int id);
	Comment getById(int id);
	Comment save(Comment comment);
	Page<Comment> getAll(Pageable pageable);

}
