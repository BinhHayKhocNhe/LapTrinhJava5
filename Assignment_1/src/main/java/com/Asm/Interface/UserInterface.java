package com.Asm.Interface;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Asm.Model.Users;

public interface UserInterface extends JpaRepository<Users, Long>{

}