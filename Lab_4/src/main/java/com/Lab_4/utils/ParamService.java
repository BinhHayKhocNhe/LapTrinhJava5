package com.Lab_4.utils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ParamService {
	@Autowired
	HttpServletRequest request;

	public String getStringParameter(String name, String defaultValue) {
		String value = request.getParameter(name);
		return (value == null || value.isEmpty()) ? defaultValue : value;
	}

	public int getInt(String name, int defaultValue) {
		String value = getStringParameter(name, String.valueOf(defaultValue));
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public double getDouble(String name, double defaultValue) {
		String value = getStringParameter(name, String.valueOf(defaultValue));
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public boolean getBoolean(String name, boolean defaultValue) {
		String value = getStringParameter(name, String.valueOf(defaultValue));
		try {
			return Boolean.parseBoolean(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public Date getDate(String name, String pattern) {
		String value = getStringParameter(name, null);
		if (value == null) {
			return null;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			return dateFormat.parse(value);
		} catch (ParseException e) {
			throw new RuntimeException("Lỗi sai định dạng cho tham số '" + name + "'", e);
		}
	}

	public File save(MultipartFile file, String path) {
		if (!file.isEmpty()) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			try {
				File saveFile = new File(dir, file.getOriginalFilename());
				file.transferTo(saveFile);
				System.out.println("File saved at: " + saveFile.getAbsolutePath());
				return saveFile;
			} catch (Exception e) {
				System.err.println("File save error: " + e.getMessage());
			}
		}
		return null;
	}
}
