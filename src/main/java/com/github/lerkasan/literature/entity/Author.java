package com.github.lerkasan.literature.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

@Entity(name = "author")
@NamedQuery(name = "Author.findAll", query = "SELECT a FROM author a")
// @NamedNativeQuery(name = "findAuthorsWithItemsTotal", query="select a.id,
// a.givenName, a.familyName, (select count(*) from item_authors i where a.id =
// i.authorId) from author a")
public class Author implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String familyName;

	private String givenName;

	// bi-directional many-to-many association to ItemToRead
	// @ManyToMany(mappedBy="authors", cascade = {CascadeType.ALL })
	@ManyToMany(mappedBy = "authors")
	private List<ItemToRead> itemsToRead;

	// @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE })
	/*
	 * @ManyToMany
	 * 
	 * @JoinTable(name="item_authors", joinColumns=@JoinColumn(name="authorId"),
	 * inverseJoinColumns=@JoinColumn(name="itemToReadId")) private
	 * List<ItemToRead> itemsToRead;
	 */

	public Author() {
		itemsToRead = new ArrayList<>();
	}

	public Author(String givenName, String familyName) {
		this.givenName = givenName;
		this.familyName = familyName;
		itemsToRead = new ArrayList<>();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return this.givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public List<ItemToRead> getItemsToRead() {
		return this.itemsToRead;
	}

	public void setItemsToRead(List<ItemToRead> itemToReads) {
		this.itemsToRead = itemToReads;
	}

	public void addItemToRead(ItemToRead itemToRead) {
		getItemsToRead().add(itemToRead);

	}

	public String getFullName() {
		return this.givenName + " " + this.familyName;
	}

}