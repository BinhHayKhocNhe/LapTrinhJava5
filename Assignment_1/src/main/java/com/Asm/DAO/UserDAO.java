package com.Asm.DAO;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.Asm.Model.Users;
import org.springframework.transaction.annotation.Transactional;

public interface UserDAO extends JpaRepository<Users, Long> {
	// Phân trang user
	@Query(value = "SELECT * FROM Users WHERE Username LIKE ?1 "
			+ "OR Fullname LIKE ?2 ORDER BY ID_User DESC", nativeQuery = true)
	Page<Users> findByKeywords(String username, String fullname, Pageable pageable);

	@Query(value = "SELECT * FROM Users WHERE Username LIKE ?1", nativeQuery = true)
	Users findByUsername(String username);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO Users (Username, Password, Fullname, Email, Phone, Gender, Birthday, Role) "
			+ "VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)", nativeQuery = true)
	void addUser(String username, String password, String fullname, String email, String phone, boolean gender,
			Date birthday, String role);
	
	@Modifying
	@Transactional
	@Query("UPDATE Users u SET u.Username = ?2, u.Password = ?3, u.Fullname = ?4, u.Email = ?5, "
			+ "u.Phone = ?6, u.Gender = ?7, u.Birthday = ?8, u.Role = ?9 WHERE u.ID_User = ?1")
	void updateUser(Long userId, String username, String password, String fullname, String email, String phone,
			boolean gender, Date birthday, String role);

}