package com.github.lerkasan.literature.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	//bi-directional many-to-many association to ItemToRead
	@ManyToMany(mappedBy="categories")
	private List<ItemToRead> itemsToRead;

	public Category() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setItemToRead(ItemToRead itemToRead) {
		// TODO Auto-generated method stub
		
	}
	
	public List<ItemToRead> getItemsToRead() {
		return this.itemsToRead;
	}

	public void setItemsToRead(List<ItemToRead> itemToReads) {
		this.itemsToRead = itemToReads;
	}

}