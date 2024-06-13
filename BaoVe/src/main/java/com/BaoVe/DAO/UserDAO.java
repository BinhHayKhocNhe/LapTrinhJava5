package com.BaoVe.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.BaoVe.Model.Users;

public interface UserDAO extends JpaRepository<Users, String> {
	@Modifying
	@Transactional
	@Query(value = "UPDATE users SET pass = ?2, fullname = ?3 , email = ?4, admin = ?5 WHERE id = ?1;", nativeQuery = true)
	void update(String id, String password, String fullname, String email, boolean admin);
}
