package com.Lab_3.controller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.Lab_3.model.Student;

@Controller
@RequestMapping("/student")
public class Controller_Bai_1 {

	@GetMapping("/form")
	public String form(@ModelAttribute("student") Student student) {
//		student.setName("John Doe");
//		student.setEmail("john.doe@example.com");
//		student.setMarks(8.5);
//		student.setGender(true); // true: Male, false: Female
//		student.setFaculty("QTDN");
//		student.setHobbies(Arrays.asList("T", "M"));
		return "Bai_1";
	}

	@PostMapping("/save")
	public String save(@Validated @ModelAttribute("student") Student student, Errors errors, Model model,
			@RequestParam("attach") MultipartFile attach) {
		if (errors.hasErrors()) {
			model.addAttribute("message", "Vui lòng sửa các trường bị sai");
			return "Bai_1";
		} else {
			if (!attach.isEmpty()) {
				try {
					String uploadDir = "src/main/resources/img"; // Đường dẫn tương đối
					String fileName = StringUtils.cleanPath(attach.getOriginalFilename());
					File uploadPath = new File(uploadDir);

					// Tạo thư mục lưu trữ nếu nó không tồn tại
					if (!uploadPath.exists()) {
						uploadPath.mkdirs();
					}

					// Kiểm tra loại file
					if (!fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".png")) {
						model.addAttribute("message", "Chỉ chấp nhận file JPG hoặc PNG");
						return "Bai_1";
					}

					// Lưu file vào thư mục lưu trữ
					try (InputStream inputStream = attach.getInputStream()) {
						Files.copy(inputStream, Paths.get(uploadPath.getAbsolutePath(), fileName),
								StandardCopyOption.REPLACE_EXISTING);
					}

					model.addAttribute("message", "Chúc mừng, bạn đã nhập đúng");
				} catch (IOException e) {
					model.addAttribute("message", "Lỗi khi lưu file: " + e.getMessage());
				}
			}
			return "Bai_1";
		}
	}

	@ModelAttribute("genders")
	public Map<Boolean, String> getGenders() {
	    Map<Boolean, String> map = new HashMap<>();
	    map.put(true, "Male");
	    map.put(false, "Female");
	    return map;
	}


	@ModelAttribute("faculties")
	public List<String> getFaculties() {
		return Arrays.asList("CNTT", "DLNHKS", "QTDN");
	}

	@ModelAttribute("hobbies")
	public Map<String, String> getHobbies() {
		Map<String, String> map = new HashMap<>();
		map.put("T", "Travelling");
		map.put("M", "Music");
		map.put("F", "Food");
		map.put("O", "Other");
		return map;
	}

}
