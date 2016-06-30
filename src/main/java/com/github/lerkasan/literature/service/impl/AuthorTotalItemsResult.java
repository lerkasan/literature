package com.github.lerkasan.literature.service.impl;

import com.github.lerkasan.literature.entity.Author;

public class AuthorTotalItemsResult {
	Author author;
	int totalItems;

	public AuthorTotalItemsResult() {
	}

	public AuthorTotalItemsResult(Author author, int totalItems) {
		super();
		this.author = author;
		this.totalItems = totalItems;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

}
