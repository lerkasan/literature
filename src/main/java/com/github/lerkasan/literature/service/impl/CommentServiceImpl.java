package com.github.lerkasan.literature.service.impl;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.lerkasan.literature.dao.CommentRepository;
import com.github.lerkasan.literature.entity.Comment;
import com.github.lerkasan.literature.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Inject
	private CommentRepository commentRepository;

	public CommentServiceImpl() {
	}

	@Override
	public Comment add(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public void delete(int id) {
		commentRepository.delete(id);
		
	}

	@Override
	public Comment getById(int id) {
		return commentRepository.findById(id);
	}

	@Override
	@Transactional
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public Page<Comment> getAll(Pageable pageable) {
		return commentRepository.findAll(pageable);
	}

}
