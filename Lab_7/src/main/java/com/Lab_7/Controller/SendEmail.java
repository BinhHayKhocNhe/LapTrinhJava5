package com.Lab_7.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.Lab_7.Model.MailInfo;
import com.Lab_7.Service.MailerService;

@Controller
public class SendEmail {

	@Autowired
	private MailerService service = null;

	@GetMapping("/")
	private String index() {
		return "SendMail";
	}

	@PostMapping("/SendMail")
	private String send(Model model, @RequestParam("to") String to, @RequestParam("subject") String subject,
			@RequestParam("content") String content) {
		try {
			MailInfo mailInfo = new MailInfo(to, subject, content);
//			service.send(mailInfo);
			service.queue(mailInfo);
			model.addAttribute("message", "Gửi mail thành công!");
		} catch (Exception e) {
			model.addAttribute("error", "Gửi mail thất bại!");
		}
		return "SendMail";
	}

}
