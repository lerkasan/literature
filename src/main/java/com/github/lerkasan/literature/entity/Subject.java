package com.github.lerkasan.literature.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity(name="subject")
@NamedQuery(name="Subject.findAll", query="SELECT s FROM subject s")
public class Subject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to ItemToRead
	@OneToMany(mappedBy="subject")
	private List<ItemToRead> itemsToRead;

	public Subject() {
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

	public List<ItemToRead> getItemsToRead() {
		return this.itemsToRead;
	}

	public void setItemsToRead(List<ItemToRead> itemsToRead) {
		this.itemsToRead = itemsToRead;
	}

	public ItemToRead addItemsToRead(ItemToRead itemToRead) {
		getItemsToRead().add(itemToRead);
		itemToRead.setSubject(this);

		return itemToRead;
	}

	public ItemToRead removeItemToRead(ItemToRead itemToRead) {
		getItemsToRead().remove(itemToRead);
		itemToRead.setSubject(null);

		return itemToRead;
	}

}