package com.github.lerkasan.literature.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.github.lerkasan.literature.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	@Query("select u from user u where u.id = :id")
	User findById(@Param("id") Integer id);

	/*
	 * @Query(
	 * "select i from item_to_read i, user_library u where(u.userId = :id) and (i.id = u.itemToReadId )"
	 * ) Page<ItemToRead> getLibraryByUserId(@Param("id") Integer id);
	 */

}
