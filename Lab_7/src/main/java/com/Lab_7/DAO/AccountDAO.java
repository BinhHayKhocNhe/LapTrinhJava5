package com.Lab_7.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Lab_7.Model.Accounts;

public interface AccountDAO extends JpaRepository<Accounts, String>{

}
