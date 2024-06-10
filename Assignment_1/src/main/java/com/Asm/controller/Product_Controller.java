package com.Asm.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Asm.DAO.ProductDAO;
import com.Asm.Model.Products;
import com.Asm.Utils.CartService;
import com.Asm.Utils.SessionService;

@Controller
public class Product_Controller {
	@Autowired
	private ProductDAO dao = null;
	@Autowired
	private SessionService session = null;

	@Autowired
	private CartService cartService = null;

	private void totalProducts(Model model) {
		int totalProducts = cartService.getTotalProducts();
		model.addAttribute("sumProduct", totalProducts);
	}

	private void loadProductData(Model model, Optional<String> key, Optional<Integer> p) {
		String keywords = key.orElse(session.getSession("keywordsProduct", ""));
		session.setSession("keywordsProduct", keywords);

		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<Products> page = dao.findByKeywords("%" + keywords + "%", pageable);

		model.addAttribute("pageProduct", page);
		model.addAttribute("keywordsProduct", session.getSession("keywordsProduct", ""));

		model.addAttribute("sessionUser", session.getSession("sessionUser", null));
		model.addAttribute("roleUser", session.getSession("roleUser", null));

		totalProducts(model);
	}

	@RequestMapping(value = "/Product", method = { RequestMethod.GET, RequestMethod.POST })
	private String searchProduct(Model model, @RequestParam("keywordsProduct") Optional<String> key,
			@RequestParam("p") Optional<Integer> p) {
		Products product = new Products();
		model.addAttribute("product", product);
		loadProductData(model, key, p);
		return "Product";
	}

	@GetMapping(value = "/editProduct/{ProductID}")
	private String edit(Model model, @PathVariable("ProductID") Long id,
			@RequestParam("keywordsProduct") Optional<String> key, @RequestParam("p") Optional<Integer> p) {
		Products product = dao.findById(id).orElse(null);
		model.addAttribute("product", product);

		loadProductData(model, key, p);
		return "Product";
	}

	@GetMapping(value = "/deleteProduct/{ProductID}")
	private String deleteProduct(@PathVariable("ProductID") Long id,
			@RequestParam("keywordsProduct") Optional<String> key, @RequestParam("p") Optional<Integer> p,
			@ModelAttribute("product") Products product, Model model) {
		dao.deleteById(id);
		model.addAttribute("message", "Thành công!");
		System.out.println("Xóa thành công!");
		loadProductData(model, key, p);
		return "Product";
	}

	@PostMapping(value = "/addProduct")
	private String addProduct(Model model, @RequestParam("keywordsProduct") Optional<String> key,
			@RequestParam("p") Optional<Integer> p, @ModelAttribute("product") Products product,
			@RequestParam("attach") MultipartFile attach) {
		try {
			// Lưu tên file vào đối tượng Products
			String fileName = StringUtils.cleanPath(attach.getOriginalFilename());
			product.setImageURL(fileName);

			dao.insertProduct(product.getProductTitle(), product.getPrice(), product.getImageURL(), product.getQuantity(),
					product.getCategories().getCategoryID(), product.getSale(), product.getNote());
			loadProductData(model, key, p);
			return doUpload(model, product, attach);
		} catch (Exception e) {
			model.addAttribute("error", "Lỗi khi thêm sản phẩm: " + e.getMessage());
			return "Product";
		}
	}

	@PostMapping(value = "/updateProduct")
	private String updateProduct(Model model, @RequestParam("keywordsProduct") Optional<String> key,
			@RequestParam("p") Optional<Integer> p, @ModelAttribute("product") Products product,
			@RequestParam("attach") MultipartFile attach) {
		System.out.println(product.getProductID());
		if (product.getProductID() == null || !dao.existsById(product.getProductID())) {
			model.addAttribute("error", "Sản phẩm không tồn tại hoặc ID sản phẩm không hợp lệ!");
			System.out.println("Sản phẩm không tồn tại hoặc ID sản phẩm không hợp lệ!");
		} else {
			try {
				String fileName = StringUtils.cleanPath(attach.getOriginalFilename());
				product.setImageURL(fileName);
				dao.save(product);
				loadProductData(model, key, p);
				return doUpload(model, product, attach);
			} catch (Exception e) {
				model.addAttribute("error", "Lỗi khi cập nhật sản phẩm: " + e.getMessage());
			}
		}

		return "Product";
	}

	private String doUpload(Model model, Products product, MultipartFile attach) {
		try {
			if (!attach.isEmpty()) {
				String uploadDir = "src/main/resources/static/img"; // Đường dẫn tương đối
				String fileName = StringUtils.cleanPath(attach.getOriginalFilename());
				File uploadPath = new File(uploadDir);

				// Tạo thư mục lưu trữ nếu nó không tồn tại
				if (!uploadPath.exists()) {
					uploadPath.mkdirs();
				}

				// Kiểm tra loại file
				if (!fileName.toLowerCase().endsWith(".jpg") && !fileName.toLowerCase().endsWith(".png")) {
					model.addAttribute("error", "Chỉ chấp nhận file JPG hoặc PNG");
					System.out.println("Chỉ chấp nhận file JPG hoặc PNG");
					return "Product";
				}

				// Lưu file vào thư mục lưu trữ
				try (InputStream inputStream = attach.getInputStream()) {
					Files.copy(inputStream, Paths.get(uploadPath.getAbsolutePath(), fileName),
							StandardCopyOption.REPLACE_EXISTING);
				}

				model.addAttribute("message", "Thành công!");
			}
		} catch (IOException e) {
			model.addAttribute("error", "Lỗi khi lưu file: " + e.getMessage());
		}
		return "Product";
	}
}
