package com.github.lerkasan.literature.service;

import org.springframework.data.domain.Page;

import com.github.lerkasan.literature.entity.ItemAccessType;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;

public interface ItemToReadService {
	ItemToRead add(ItemToRead itemToRead);
	void delete(int id);
	ItemToRead getById(int id);
	ItemToRead save(ItemToRead itemToRead);
	//void save(ItemToRead itemToRead);
	//Page<ItemToRead> getAll(Pageable pageable);
	ItemToRead getByUrl(String url);
	Page<ItemToRead> getAll(int pageNumber);
	Page<ItemToRead> getByItemType(ItemType itemType, int pageNumber);
	Page<ItemToRead> getByAccessType(ItemAccessType accessType, Integer pageNumber);
	Page<ItemToRead> getByItemTypeAndAccessType(ItemType itemType, ItemAccessType accessType, Integer pageNumber);
	Page<ItemToRead> getAllByKeyword(String searchDatabaseKeyword, Integer pageNumber);
	Page<ItemToRead> getByKeywordAndItemType(String searchDatabaseKeyword, ItemType valueOf, Integer pageNumber);
	Page<ItemToRead> getByKeywordAndItemTypeAndAccessType(String searchDatabaseKeyword, ItemType valueOf,
			ItemAccessType valueOf2, Integer pageNumber);
	Page<ItemToRead> getByKeywordAndAccessType(String searchDatabaseKeyword, ItemAccessType valueOf,
			Integer pageNumber);
}
