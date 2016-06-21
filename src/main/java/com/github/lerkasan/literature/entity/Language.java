package com.github.lerkasan.literature.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Language database table.
 * 
 */
@Entity(name="language")
@NamedQuery(name="Language.findAll", query="SELECT l FROM language l")
public class Language implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Short id;

	private String name;

	//bi-directional many-to-one association to ItemToRead
	@OneToMany(mappedBy="language")
	private List<ItemToRead> itemsToRead;

	public Language() {
	}

	public Short getId() {
		return this.id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ItemToRead> getItemToReads() {
		return this.itemsToRead;
	}

	public void setItemsToRead(List<ItemToRead> itemToReads) {
		this.itemsToRead = itemToReads;
	}
	
	public List<ItemToRead> getItemsToRead() {
		return this.itemsToRead;
	}

	public ItemToRead addItemToRead(ItemToRead itemToRead) {
		getItemToReads().add(itemToRead);
		itemToRead.setLanguage(this);

		return itemToRead;
	}

	public ItemToRead removeItemToRead(ItemToRead itemToRead) {
		getItemToReads().remove(itemToRead);
		itemToRead.setLanguage(null);

		return itemToRead;
	}

}