package com.github.lerkasan.literature.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "resource")
@NamedQuery(name = "Resource.findAll", query = "SELECT r FROM resource r")
public class Resource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "Name can not be empty")
	private String name;

	@NotEmpty(message = "URL can not be empty")
	private String url;

	@NotEmpty(message = "Domain can not be empty")
	private String domain;

	private String parameterFormat;

	@NotEmpty(message = "Response format can not be empty")
	private String responseFormat;

	private String apiKey;

	private String searchEngineKey;

	private String associateId;

	// bi-directional many-to-one association to ItemToRead
	@OneToMany(mappedBy = "resource")
	private List<ItemToRead> itemsToRead;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "addedBy")
	private User user;

	public Resource() {
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParameterFormat() {
		return parameterFormat;
	}

	public void setParameterFormat(String parameterFormat) {
		this.parameterFormat = parameterFormat;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getSearchEngineKey() {
		return searchEngineKey;
	}

	public void setSearchEngineKey(String searchEngineKey) {
		this.searchEngineKey = searchEngineKey;
	}

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public List<ItemToRead> getItemsToRead() {
		return this.itemsToRead;
	}

	public void setItemsToRead(List<ItemToRead> itemsToRead) {
		this.itemsToRead = itemsToRead;
	}

	public ItemToRead addItemToRead(ItemToRead itemToRead) {
		getItemsToRead().add(itemToRead);
		itemToRead.setResource(this);

		return itemToRead;
	}

	public ItemToRead removeItemToRead(ItemToRead itemToRead) {
		getItemsToRead().remove(itemToRead);
		itemToRead.setResource(null);

		return itemToRead;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getResponseFormat() {
		return responseFormat;
	}

	public void setResponseFormat(String responseFormat) {
		this.responseFormat = responseFormat;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}