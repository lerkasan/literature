package com.github.lerkasan.literature.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity(name="item_to_read")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="dtype", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("item_to_read")
@NamedQuery(name="ItemToRead.findAll", query="SELECT i FROM item_to_read i")
public class ItemToRead implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private LocalDate addedAt;

	@Lob
	private String contents;

	private String linkToFile;
	
	private LocalDate publishDate;

	@Lob
	private String text;
	
	private ItemType itemType;

	private ItemAccessType accessType;

	private int timesDownloaded;

	private int timesViewed;

	private String title;

	private String url;

	private boolean visible;
	
	private String keywords;

	@ManyToMany
	@JoinTable(name="user_categories",
		joinColumns=@JoinColumn(name="itemToReadId"),
		inverseJoinColumns=@JoinColumn(name="categoryId"))
	private List<Category> categories;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="itemToRead", cascade = {CascadeType.PERSIST,CascadeType.MERGE })
	private List<Comment> comments;

	//bi-directional many-to-many association to Author
	//@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST })  
	//DO NOT USE CASCADE to avoid author record duplication in author table when this table already contains this author
	@ManyToMany
	@JoinTable(name="item_authors", 
		joinColumns=@JoinColumn(name="itemToReadId", referencedColumnName = "id"), 
		inverseJoinColumns=@JoinColumn(name="authorId", referencedColumnName = "id")) 
	private List<Author> authors;

	//bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name="languageId")
	private Language language;

	//bi-directional many-to-one association to Resource
	@ManyToOne
	@JoinColumn(name="resourceId")
	private Resource resource;

	//bi-directional many-to-one association to Subject
	@ManyToOne
	@JoinColumn(name="subjectId")
	private Subject subject;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="addedBy")
	private User user;

	public ItemToRead() {
		categories = new ArrayList<>();
		comments = new ArrayList<>();
		authors = new ArrayList<>(); 
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getAddedAt() {
		return this.addedAt;
	}

	public void setAddedAt(LocalDate addedAt) {
		this.addedAt = addedAt;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getLinkToFile() {
		return this.linkToFile;
	}

	public void setLinkToFile(String linkToFile) {
		this.linkToFile = linkToFile;
	}
	
	public LocalDate getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public int getTimesDownloaded() {
		return this.timesDownloaded;
	}

	public void setTimesDownloaded(int timesDownloaded) {
		this.timesDownloaded = timesDownloaded;
	}

	public int getTimesViewed() {
		return this.timesViewed;
	}

	public void setTimesViewed(int timesViewed) {
		this.timesViewed = timesViewed;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean getVisible() {
		return this.visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Category addCategory(Category category) {
		getCategories().add(category);
	//	category.setItemToRead(this);

		return category;
	}

	public Category removeCategory(Category category) {
		getCategories().remove(category);
	//	category.setItemToRead(null);

		return category;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
	//	comment.setItemToRead(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
	//	comment.setItemToRead(null);

		return comment;
	}

	public ItemAccessType getAccessType() {
		return this.accessType;
	}

	public void setAccessType(ItemAccessType accessType) {
		this.accessType = accessType;
	}

	public List<Author> getAuthors() {
		return this.authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	
	public Author addAuthor(Author author) {
		getAuthors().add(author);
		/* if (!author.getItemsToRead().contains(this)) {
			author.addItemToRead(this);   //Maybe should be deleted
		} */
		return author;
	}

	public Author removeAuthor(Author author) {
		getAuthors().remove(author);
		//author.getItemsToRead().remove(this);  //Maybe should be deleted

		return author;
	}

	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}