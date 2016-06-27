package com.github.lerkasan.literature.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.github.lerkasan.literature.entity.ItemToRead;

public interface ItemToReadRepository extends PagingAndSortingRepository<ItemToRead, Integer> {
	@Query("select i from item_to_read i where i.title= :title")
    ItemToRead findByTitle(@Param("title") String title);
	
	@Query("select i from item_to_read i where i.id = :id")
    ItemToRead findById(@Param("id") Integer id);
	
	@Query("select i from item_to_read i where i.url = :url")
    ItemToRead findByUrl(@Param("url") String url);

}
