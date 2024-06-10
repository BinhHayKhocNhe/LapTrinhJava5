package com.Asm.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.Asm.DAO.UserDAO;
import com.Asm.Model.Users;
import com.Asm.Utils.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthInterceptor implements HandlerInterceptor {
	@Autowired
	private SessionService session = null;

	@Autowired
	private UserDAO dao = null;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		String username = session.getSession("sessionUser", "");
		Users user = dao.findByUsername(username);
		String error = "";
		if (user == null) {
			error = "Please login!";
		} else if (!user.getRole().equals("Admin")) {
			error = "Access denied!";
		}

		if (error.length() > 0) {
			session.setSession("security-uri", uri);
			response.sendRedirect("/SignIn?error=" + error);
			return false;
		}
		return true;
	}
}
