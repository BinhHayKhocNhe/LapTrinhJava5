package com.Lab_7.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Lab_7.Helper.MailerHelper;
import com.Lab_7.Model.MailInfo;
import com.Lab_7.Service.MailerService;

import jakarta.mail.MessagingException;

@Controller
public class Bai_3_Controller {
	@Autowired
	private MailerService service = null;

	@GetMapping("/Bai3")
	private String View() {
		return "Bai_3";
	}

	@PostMapping("/Bai3")
	private String Bai_3(Model model, @RequestParam("txtTo") String txtTo,
			@RequestParam(value = "txtCC", required = false) String txtCC,
			@RequestParam(value = "txtBCC", required = false) String txtBCC,
			@RequestParam("txtSubject") String txtSubject, @RequestParam("txtContent") String txtContent,
			@RequestParam("file") MultipartFile multipartFile) throws IOException, MessagingException {
		MailerHelper helper = new MailerHelper();
		List<File> files = new ArrayList<>();
		String[] emailCC = helper.parseStringEmailToArray(txtCC);
		String[] emailBCC = helper.parseStringEmailToArray(txtBCC);
		MailInfo mail = new MailInfo();
		mail.setFrom("duongminhbinh999@gmail.com");
		mail.setTo(txtTo);
		mail.setCc(emailCC);
		mail.setBcc(emailBCC);
		mail.setSubject(txtSubject);
		mail.setBody(txtContent);
		// covert MultipartFile to File
		if (!multipartFile.isEmpty()) {
			File file = helper.convertMultipartFileToFile(multipartFile);
			files.add(file);
			// Set cho MailInfo
			mail.setFiles(files);
		}
		service.queue(mail);
		model.addAttribute("message", "Gửi mail thành công!");
		return "Bai_3";
	}
}
