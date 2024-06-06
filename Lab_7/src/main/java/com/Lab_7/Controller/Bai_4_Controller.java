package com.Lab_7.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Bai_4_Controller {
	@RequestMapping("/index")
	public String index() {
		return "Index";
	}

	@RequestMapping("/about")
	public String about() {
		return "about";
	}
}
