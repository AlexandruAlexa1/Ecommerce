package com.ecommerce.admin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.admin.paging.SearchRepository;
import com.ecommerce.common.entity.User;

@Repository
public interface UserRepository extends SearchRepository<User, Integer> {

	@Query("SELECT user FROM User user WHERE user.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	public Long countById(Integer id);
	
	@Query("SELECT user FROM User user WHERE " +
			"CONCAT(user.id, ' ', user.email, ' ', user.firstName, ' ', user.lastName) LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
	
	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
}
