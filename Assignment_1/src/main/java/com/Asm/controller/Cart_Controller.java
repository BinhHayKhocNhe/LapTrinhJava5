package com.Asm.controller;

import com.Asm.DAO.InvoiceDetailDAO;
import com.Asm.DAO.InvoicesDAO;
import com.Asm.DAO.ProductDAO;
import com.Asm.DAO.UserDAO;
import com.Asm.Model.Invoices;
import com.Asm.Model.Products;
import com.Asm.Model.Users;
import com.Asm.Utils.*;

import java.util.Optional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Cart_Controller {

	@Autowired
	private CartService cartService = null;

	@Autowired
	private ProductDAO productDAO = null;

	@Autowired
	private SessionService sessionService = null;

	@Autowired
	private UserDAO userDAO = null;

	@Autowired
	private InvoicesDAO invoiceDAO = null;

	@Autowired
	private InvoiceDetailDAO detailDAO = null;

	@GetMapping("/Cart")
	private String views(Model model) {
		model.addAttribute("sessionUser", sessionService.getSession("sessionUser", null));
		model.addAttribute("roleUser", sessionService.getSession("roleUser", null));
		model.addAttribute("IDUser", sessionService.getSession("IDUser", null));

		totalProducts(model);
		return "Cart";
	}

	@GetMapping("/cart")
	private String viewCart(Model model) {
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("totalAmount", cartService.getTotalAmount());

		model.addAttribute("sessionUser", sessionService.getSession("sessionUser", null));
		model.addAttribute("roleUser", sessionService.getSession("roleUser", null));
		model.addAttribute("IDUser", sessionService.getSession("IDUser", null));

		totalProducts(model);
		return "Cart";
	}

	@GetMapping("/cart-checkout")
    public String cartCheckout(Model model) {
       

        // Kiểm tra và thêm thuộc tính "error" vào model nếu cần
        if (!model.containsAttribute("error")) {
            model.addAttribute("error", null); // Nếu không có lỗi, đặt giá trị là null
        } else if (!model.containsAttribute("message")) {
            model.addAttribute("message", null); // Nếu không có lỗi, đặt giá trị là rỗng
        }

        // Thêm các thuộc tính khác vào model
        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("totalAmount", cartService.getTotalAmount());
        model.addAttribute("sessionUser", sessionService.getSession("sessionUser", null));
        model.addAttribute("roleUser", sessionService.getSession("roleUser", null));
        model.addAttribute("IDUser", sessionService.getSession("IDUser", null));

        // Thực hiện các công việc khác nếu cần
        totalProducts(model);

        // Trả về tên của view
        return "Checkout";
    }

	@RequestMapping(value = "/add-cart/{productId}", method = { RequestMethod.GET, RequestMethod.POST })
	private String addToCart(@PathVariable("productId") Long productId,
			@RequestParam(name = "quantity", required = false, defaultValue = "1") int quantity, Model model) {
		Products product = productDAO.findByID(productId);
		if (product != null) {
			cartService.addToCart(product, quantity);
			totalProducts(model);
		}

		return "redirect:/cart";
	}

	@PostMapping("/cart/update")
	private String updateQuantity(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity,
			Model model) {
		cartService.updateQuantity(productId, quantity);
		return "redirect:/cart";
	}

	@PostMapping("/cart/remove")
	private String removeFromCart(@RequestParam("productId") Long productId, Model model) {
		cartService.removeItem(productId);
		return "redirect:/cart";
	}

	@PostMapping("/cart/clear")
	private String clearCart(Model model) {
		cartService.clearCart();
		return "redirect:/cart";
	}

	private void totalProducts(Model model) {
		int totalProducts = cartService.getTotalProducts();
		model.addAttribute("sumProduct", totalProducts);
	}

	@PostMapping(value = "/submit-order")
	public String submitOrder(@RequestParam(value = "productid", required = false) Long productId,
			@RequestParam(value = "id", required = false) Long idUser, @RequestParam("name") String name,
			@RequestParam("phone") String phone, @RequestParam("selectedDistrict") String address,
			@RequestParam("quantity") int quantity, @RequestParam("price") float price, Model model) {
		if (productId == null) {
			model.addAttribute("error", "Vui lòng chọn sản phẩm!");
			return "Checkout";
		} else if (idUser == null) {
			model.addAttribute("error", "Vui lòng đăng nhập để mua sản phẩm!");
			return "Checkout";
		}
		Optional<Users> userOptional = userDAO.findById(idUser);
		Optional<Products> productOptional = productDAO.findById(productId);
		if (userOptional.isPresent()) {
			Users user = userOptional.get();
			// Tạo một đối tượng Invoices và thiết lập người dùng
			Invoices invoice = new Invoices();
			invoice.setUsers(user);
			invoice.setFullname(name);
			invoice.setPhone(phone);
			invoice.setAddress(address);
			invoice.setTotal(price);
			Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
			invoice.setCreate_Date(currentTimestamp);

			invoiceDAO.save(invoice);
			cartService.clearCart();
			if (productOptional.isPresent()) {
				Products product = productOptional.get();
				detailDAO.insertDetail(invoice.getID(), product.getProductID(), quantity, price);
			}
			model.addAttribute("message", "Đặt đơn thành công!");
			model.addAttribute("IDUser", sessionService.getSession("IDUser", null));
		}
		return "Checkout";
	}
}