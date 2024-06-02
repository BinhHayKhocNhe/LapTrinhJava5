package com.Lab_6.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Lab_6.DAO.ProductDAO;
import com.Lab_6.Entity.Report;

@Controller
public class Bai_3_Controller {
	@Autowired
	private ProductDAO dao = null;

	@GetMapping("/report")
	private String inventory(Model model) {
		List<Report> items = dao.getInventoryByCategory();
		model.addAttribute("items", items);
		return "Bai_3";
	}
}
