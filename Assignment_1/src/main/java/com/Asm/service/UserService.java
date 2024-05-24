package com.Asm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Asm.Interface.UserInterface;
import com.Asm.Model.Users;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserInterface user;

	public List<Users> findAll() {
		return user.findAll();
	}
}
