package com.Asm.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Asm.Model.Users;

public interface UserDAO extends JpaRepository<Users, Long> {
	// Phân trang user
	@Query(value = "SELECT * FROM Users WHERE Username LIKE ?1 OR Fullname LIKE ?2", nativeQuery = true)
	Page<Users> findByKeywords(String username, String fullname, Pageable pageable);
}