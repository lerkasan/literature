package com.github.lerkasan.literature.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;


/**
 * The persistent class for the Comment database table.
 * 
 */
@Entity(name="comment")
@NamedQuery(name="Comment.findAll", query="SELECT c FROM comment c")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Lob
	private String text;

	private LocalDateTime timespamp;

	//bi-directional many-to-one association to ItemToRead
	@ManyToOne
	@JoinColumn(name="itemToReadId")
	private ItemToRead itemToRead;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;

	public Comment() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getTimespamp() {
		return this.timespamp;
	}

	public void setTimespamp(LocalDateTime timespamp) {
		this.timespamp = timespamp;
	}

	public ItemToRead getItemToRead() {
		return this.itemToRead;
	}

	public void setItemToRead(ItemToRead itemToRead) {
		this.itemToRead = itemToRead;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}