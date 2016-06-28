package com.github.lerkasan.literature.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.ItemType;

public interface ItemToReadRepository extends PagingAndSortingRepository<ItemToRead, Integer> {
	@Query("select i from item_to_read i where i.title= :title")
    ItemToRead findByTitle(@Param("title") String title);
	
	@Query("select i from item_to_read i where i.id = :id")
    ItemToRead findById(@Param("id") Integer id);
	
	@Query("select i from item_to_read i where i.url = :url")
    ItemToRead findByUrl(@Param("url") String url);

	@Query("select i from item_to_read i where i.itemType = :itemType")
	Page<ItemToRead> findByItemType(@Param("itemType") ItemType itemType, Pageable pageable);

}
