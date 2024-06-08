 package com.Lab_5.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Lab_5.DAO.ProductDAO;
import com.Lab_5.Entity.Products;

@Controller
public class Bai_5_Controller {
	@Autowired
	private ProductDAO productDAO = null;

	@GetMapping("/page/sort")
	private String paginate(Model model, @RequestParam("p") Optional<Integer> p,
			@RequestParam("sortField") Optional<String> sortField, @RequestParam("sortDir") Optional<String> sortDir) {

		String currentSortField = sortField.orElse("id");
		String currentSortDir = sortDir.orElse("desc");
		Sort sort = Sort.by(currentSortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
				currentSortField);

		Pageable pageable = PageRequest.of(p.orElse(0), 5, sort);
		Page<Products> page = productDAO.findAll(pageable);
		model.addAttribute("page", page);
		model.addAttribute("sortField", currentSortField);
		model.addAttribute("sortDir", currentSortDir);
		model.addAttribute("reverseSortDir", currentSortDir.equals("asc") ? "desc" : "asc");
		return "Bai_5";
	}
}
