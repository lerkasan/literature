package com.github.lerkasan.literature.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.lerkasan.literature.dao.ItemToReadRepository;
import com.github.lerkasan.literature.entity.Author;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.service.AuthorService;
import com.github.lerkasan.literature.service.ItemToReadService;

@Service
public class ItemToReadServiceImpl implements ItemToReadService {

	@Inject
	private ItemToReadRepository itemToReadRepository;
	
	@Inject
	private AuthorService authorService;
	
	@PersistenceContext
    private EntityManager em;
	
	public ItemToReadServiceImpl() {
	}

	@Override
	public ItemToRead add(ItemToRead itemToRead) {
		return itemToReadRepository.save(itemToRead);
	}

	@Override
	public void delete(int id) {
		itemToReadRepository.delete(id);
	}

	@Override
	public ItemToRead getById(int id) {
		return itemToReadRepository.findById(id);
	}

	@Override
	@Transactional
	public ItemToRead save(ItemToRead itemToRead) {
	//public void save(ItemToRead itemToRead) {
		//category also should be worked out
	/*	List<Author> authors = itemToRead.getAuthors();
		if ((authors != null) && (!authors.isEmpty())) {
			for (Author author : authors) {
				if ((author.getGivenName() != null) && (author.getFamilyName() != null)) {
					Author foundAuthor = authorService.getByFullName(author.getGivenName(), author.getFamilyName());
					if (foundAuthor == null) {
						authorService.save(author);
					}
				}
			}
		}*/
		return itemToReadRepository.save(itemToRead);
	/*	em.getTransaction();
		em.persist(itemToRead);
		em.close(); */
	}

	@Override
	public Page<ItemToRead> getAll(Pageable pageable) {
		return itemToReadRepository.findAll(pageable);
	}

	@Override
	public ItemToRead getByUrl(String url) {
		return itemToReadRepository.findByUrl(url);
	}

}
