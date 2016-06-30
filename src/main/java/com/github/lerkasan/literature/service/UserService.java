package com.github.lerkasan.literature.service;

import com.github.lerkasan.literature.entity.User;

public interface UserService {
	
	public final int USER_ID = 1; // TO BE REMOVED when user features will be implemented
	
	User getById(int id);
	User save(User user);
}
