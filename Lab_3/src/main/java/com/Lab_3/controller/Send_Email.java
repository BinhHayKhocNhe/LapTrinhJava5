package com.Lab_3.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class Send_Email {
	@Autowired
	private JavaMailSender javaMailSender;

	@GetMapping("/")
	public String show() {
		return "SendEmail";
	}

	@Autowired
	HttpServletRequest request;

	@PostMapping("/send")
	public String sendEmail(@RequestParam("attach") MultipartFile attach, Model model) {
		try {
			// Xác định đường dẫn tương đối đến thư mục chứa mã nguồn của bạn
			String sourceDirectory = System.getProperty("user.dir");

			// Xây dựng đường dẫn đến thư mục tạm trong cùng thư mục với mã nguồn
			String tempDir = sourceDirectory + File.separator + "src" + File.separator + "main" + File.separator
					+ "resources" + File.separator + "static" + File.separator + "temp";

			// Tạo thư mục lưu trữ nếu nó không tồn tại
			File uploadPath = new File(tempDir);
			if (!uploadPath.exists()) {
				uploadPath.mkdirs();
			}

			// Tạo tệp tạm trong thư mục tạm
			File tempFile = new File(tempDir, attach.getOriginalFilename());
			attach.transferTo(tempFile);

			// Tạo một đối tượng MimeMessage để gửi email
			MimeMessage message = javaMailSender.createMimeMessage();

			// Sử dụng lớp MimeMessageHelper để đính kèm file vào email
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(request.getParameter("email"));
			helper.setSubject("Image");
			helper.setText("Image");

			// Đính kèm file vào email
			helper.addAttachment("AttachmentName.jpg", tempFile);

			// Gửi email
			javaMailSender.send(message);

			// Xóa file tạm sau khi gửi xong
			tempFile.delete();

			// Thêm thông báo thành công vào model
			model.addAttribute("message", "Email đã được gửi thành công");

		} catch (IOException | MessagingException e) {
			// Xử lý nếu có lỗi xảy ra
			e.printStackTrace();
			model.addAttribute("message", "Có lỗi xảy ra khi gửi email");
		}
		return "SendEmail";
	}
}
