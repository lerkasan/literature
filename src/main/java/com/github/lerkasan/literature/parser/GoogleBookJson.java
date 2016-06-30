package com.github.lerkasan.literature.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;
import com.github.lerkasan.literature.entity.Literature;
import com.github.lerkasan.literature.service.AuthorService;

public class GoogleBookJson implements ConvertableToItemToRead {

	private final int BriefDescriptionSizeInChars = 500;

	@Inject
	private AuthorService authorService;

	private String title;
	private List<String> authors;
	private String publisher;
	private String publishedDate;
	private String description;
	private List<GoogleBookIndustryIdentifier> industryIdentifiers;
	private String pageCount;
	private GoogleBookImageLinks imageLinks;
	private String previewLink;

	public GoogleBookJson() {
		authors = new ArrayList<>();
		industryIdentifiers = new ArrayList<>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	List<GoogleBookIndustryIdentifier> getIndustryIdentifiers() {
		return industryIdentifiers;
	}

	public void setIndustryIdentifiers(List<GoogleBookIndustryIdentifier> industryIdentifiers) {
		this.industryIdentifiers = industryIdentifiers;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public GoogleBookImageLinks getImageLinks() {
		return imageLinks;
	}

	public void setImageLinks(GoogleBookImageLinks imageLinks) {
		this.imageLinks = imageLinks;
	}

	public String getPreviewLink() {
		return previewLink;
	}

	public void setPreviewLink(String previewLink) {
		this.previewLink = previewLink;
	}

	@Override
	public ItemToRead convertToItem() {
		Literature literatureItem = new Literature();
		literatureItem.setTitle(title);
		literatureItem.setUrl(previewLink);
		literatureItem.setContents(description);
		if ((publishedDate != null) && (publishedDate != "")) {

			for (String formatString : DATE_FORMAT_STRINGS) {
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
					LocalDate date = LocalDate.parse(publishedDate, formatter);
					literatureItem.setPublishDate(date);
					literatureItem.setYear(date.getYear());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		literatureItem.setAccessType(ItemAccessType.PAID);
		literatureItem.setItemType(ItemType.BOOK);
		literatureItem.setPublishing(publisher);
		literatureItem.setIsbn(getIndustryIdentifiers().get(0).getIdentifier());
		literatureItem.setImageUrl(getImageLinks().getThumbnail());
		if ((pageCount != null) && (pageCount != "")) {
			try {
				literatureItem.setPages(Short.parseShort(pageCount));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		for (String author : authors) {
			if ((author != null) && (author != "")) {
				String[] fullNameParts = authorService.divideFullName(author);
				Author itemAuthor = authorService.getByFullName(fullNameParts[0], fullNameParts[1]);
				if (itemAuthor == null) {
					itemAuthor = new Author(fullNameParts[0], fullNameParts[1]);
					// This saving is used instead of Cascade.PERSIST to avoid
					// duplication of existing authors:
					authorService.save(itemAuthor);
					itemAuthor = authorService.getByFullName(fullNameParts[0], fullNameParts[1]);
				}
				literatureItem.addAuthor(itemAuthor);
				itemAuthor.addItemToRead(literatureItem);
			}
		}
		return literatureItem;
	}

	@Override
	public String getUrl() {
		return getPreviewLink();
	}

	public String getImgUrl() {
		if (getImageLinks() == null) {
			return "";
		}
		return getImageLinks().getThumbnail();
	}

	public String getIsbn() {
		if (getIndustryIdentifiers() == null) {
			return "";
		} else {
			if (getIndustryIdentifiers().isEmpty()) {
				return "";
			} else if (getIndustryIdentifiers().get(0) == null) {
				return "";
			}
		}
		return getIndustryIdentifiers().get(0).getIdentifier();
	}

	public String getBriefDescription() {
		if (description != null) {
			int size = description.length();
			if (size > BriefDescriptionSizeInChars + 1) {
				return description.substring(0, BriefDescriptionSizeInChars) + "...";
			} else {
				return description.substring(0, size / 2) + "...";
			}
		}
		return "";
	}

}
