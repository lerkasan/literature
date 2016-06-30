package com.github.lerkasan.literature.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.dao.UserRepository;
import com.github.lerkasan.literature.entity.User;
import com.github.lerkasan.literature.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Inject
	private UserRepository userRepository;

	public UserServiceImpl() {
	}

	@Override
	public User getById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
}