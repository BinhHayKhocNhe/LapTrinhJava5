package com.Lab_1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Lab_1.entity.Profiles;

@Controller
public class HomeController {
	@RequestMapping("/home")
	public String Index(Model model) {
		List<Profiles> profiles = new ArrayList<>();
		profiles.add(new Profiles("Nguyễn Văn A", "Quận 1, TP.HCM", 25));
		profiles.add(new Profiles("Trần Thị B", "Quận 2, TP.HCM", 30));
		profiles.add(new Profiles("Trần Thị C", "Quận 3, TP.HCM", 30));
		profiles.add(new Profiles("Dương Minh Bình", "Bình Tân, TP.HCM", 20));

		model.addAttribute("profiles", profiles);
		return "index";
	}
	
}
