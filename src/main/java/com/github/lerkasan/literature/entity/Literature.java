package com.github.lerkasan.literature.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Literature database table.
 * 
 */
@Entity(name="literature")
@DiscriminatorValue("literature")
@NamedQuery(name="Literature.findAll", query="SELECT l FROM literature l")
public class Literature extends ItemToRead implements Serializable {
	private static final long serialVersionUID = 1L;

	private String doi;

	private String isbn;

	private String issn;

	private short pages;

	private short volume;

	private short year;
	
	private String publishing;
	
	private String issueOrEditionNumber;
	
	//bi-directional one-to-one association to ItemToRead
	@OneToOne
	//@PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
	@JoinColumn(name="id", referencedColumnName="id")
	private ItemToRead itemToRead;

	public Literature() {
	}

	public String getDoi() {
		return this.doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getIssn() {
		return this.issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getIssueOrEditionNumber() {
		return issueOrEditionNumber;
	}

	public void setIssueOrEditionNumber(String issueOrEditionNumber) {
		this.issueOrEditionNumber = issueOrEditionNumber;
	}

	public short getPages() {
		return this.pages;
	}

	public void setPages(short pages) {
		this.pages = pages;
	}

	public short getVolume() {
		return this.volume;
	}

	public void setVolume(short volume) {
		this.volume = volume;
	}

	public short getYear() {
		return this.year;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public ItemToRead getItemToRead() {
		return this.itemToRead;
	}

	public void setItemToRead(ItemToRead itemToRead) {
		this.itemToRead = itemToRead;
	}

	public String getPublishing() {
		return publishing;
	}

	public void setPublishing(String publishing) {
		this.publishing = publishing;
	}
	
	


}