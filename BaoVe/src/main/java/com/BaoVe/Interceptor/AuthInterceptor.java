package com.BaoVe.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.BaoVe.DAO.UserDAO;
import com.BaoVe.Model.Users;
import com.BaoVe.Utils.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthInterceptor implements HandlerInterceptor {
	@Autowired
	private UserDAO dao = null;
	@Autowired
	private SessionService service = null;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		String username = service.getSession("username", null);
		Users user = dao.findById(username).orElse(null);
		String error = "";
		if (user == null) {
			error = "Please login!";
		} else if (user.isAdmin() != true) {
			error = "Access denied!";
		}

		if (error.length() > 0) {
			service.setSession("security-uri", uri);
			response.sendRedirect("/login?error=" + error);
			return false;
		}
		return true;
	}
}
