package com.github.lerkasan.literature.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Author database table.
 * 
 */
@Entity(name="author")
@NamedQuery(name="Author.findAll", query="SELECT a FROM author a")
public class Author implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String familyName;

	private String givenName;

	//bi-directional many-to-many association to ItemToRead
	@ManyToMany(mappedBy="authors")
	private List<ItemToRead> itemsToRead;

	public Author() {
	}
	
	public Author(String givenName, String familyName) {
		this.givenName = givenName;
		this.familyName = familyName;
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

	public void setItemToRead(ItemToRead itemToRead) {
		this.itemsToRead.add(itemToRead);
		
	}
	
	public static String[] parseFullName(String fullName) {
		String[] fullNameParts = fullName.split(" ", 2);
		return fullNameParts;
	}
	
	

}