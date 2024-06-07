package com.Lab_7.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.Lab_7.Model.Accounts;
import com.Lab_7.Utils.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthInterceptor implements HandlerInterceptor {
	@Autowired
	private SessionService session = null;

	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	        String uri = request.getRequestURI();
	        Accounts user = session.getSession("user", ""); // lấy từ session
	        String error = "";

	        if (user == null) { // chưa đăng nhập
	            error = "Please login!";
	        } else if (!user.isAdmin() && uri.startsWith("/login") && !uri.equals("/login")) { // không đúng vai trò
	            error = "Access denied!";
	        }

	        if (error.length() > 0) { // có lỗi
	            session.setSession("security-uri", uri);
	            response.sendRedirect("/login?error=" + error);
	            return false;
	        }
	        return true;
	    }

}
