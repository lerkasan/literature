package com.github.lerkasan.literature.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity(name = "user_group")
@NamedQuery(name = "UserGroup.findAll", query = "SELECT u FROM user_group u")
public class UserGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Byte id;

	private String name;

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "userGroup")
	private List<User> users;

	public UserGroup() {
	}

	public Byte getId() {
		return this.id;
	}

	public void setId(Byte id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUserGroup(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUserGroup(null);

		return user;
	}

}