package com.Lab_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Controller_Bai_1 {

	public String m1() {
		return "OK 1";
	}

	public String m2() {
		return "OK 2";
	}

	public String m3() {
		return "OK 3";
	}

	@GetMapping("/")
	public String index() {
		return "Bai_1";
	}

	@RequestMapping(value = "/ok", method = { RequestMethod.GET, RequestMethod.POST })
	public String ok(Model model, @RequestParam(value = "method", required = false) String method) {
		String methodName = "";
		if ("OK1".equals(method)) {
			methodName = m1();
		} else if ("OK2".equals(method)) {
			methodName = m2();
		} else if ("OK3".equals(method)) {
			methodName = m3();
		}

		model.addAttribute("methodName", methodName);
		return "Bai_1";
	}
}
