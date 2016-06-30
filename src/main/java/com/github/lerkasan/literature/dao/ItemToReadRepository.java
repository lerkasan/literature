package com.github.lerkasan.literature.dao;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.github.lerkasan.literature.entity.ItemAccessType;
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

	@Query("select i from item_to_read i where i.accessType = :accessType")
	Page<ItemToRead> findByAccessType(@Param("accessType") ItemAccessType accessType, Pageable pageable);

	Page<ItemToRead> findByItemTypeAndAccessType(@Param("itemType") ItemType itemType,
			@Param("accessType") ItemAccessType accessType, Pageable pageable);

	@Query("select i from item_to_read i where ((i.title like :searchDatabaseKeyword) or (i.contents like :searchDatabaseKeyword)) and ((i.publishDate >= :periodSelection) or (i.publishDate is null))")
	Page<ItemToRead> findByKeyword(@Param("searchDatabaseKeyword") String searchDatabaseKeyword,
			@Param("periodSelection") LocalDate periodSelection, Pageable pageable);

	@Query("select i from item_to_read i where (i.itemType = :itemType) and ((i.publishDate >= :periodSelection) or (i.publishDate is null)) and ((i.title like :searchDatabaseKeyword) or (i.contents like :searchDatabaseKeyword))")
	Page<ItemToRead> findByKeywordAndItemType(@Param("searchDatabaseKeyword") String searchDatabaseKeyword,
			@Param("itemType") ItemType itemType, @Param("periodSelection") LocalDate periodSelection,
			Pageable pageable);

	@Query("select i from item_to_read i where (i.itemType = :itemType) and (i.accessType = :accessType) and ((i.publishDate >= :periodSelection) or (i.publishDate is null)) and ((i.title like :searchDatabaseKeyword) or (i.contents like :searchDatabaseKeyword))")
	Page<ItemToRead> findByKeyWordAndItemTypeAndAccessType(@Param("searchDatabaseKeyword") String searchDatabaseKeyword,
			@Param("itemType") ItemType itemType, @Param("accessType") ItemAccessType accessType,
			@Param("periodSelection") LocalDate periodSelection, Pageable pageable);

	@Query("select i from item_to_read i where (i.accessType = :accessType) and ((i.publishDate >= :periodSelection) or (i.publishDate is null)) and ((i.title like :searchDatabaseKeyword) or (i.contents like :searchDatabaseKeyword))")
	Page<ItemToRead> findByKeywordAndAccessType(@Param("searchDatabaseKeyword") String searchDatabaseKeyword,
			@Param("accessType") ItemAccessType accessType, @Param("periodSelection") LocalDate periodSelection,
			Pageable pageable);

}
