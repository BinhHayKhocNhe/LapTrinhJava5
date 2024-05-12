package com.Lab_1.utils.Cookie;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

public class CookieUtils {
	public static Cookie setCookie(HttpServletResponse response, String name, String value, int hours) {
		// create a cookie
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(hours * 3600);
		cookie.setPath("/");
		response.addCookie(cookie);
		return cookie;
	}

	public static String getCookie(HttpServletRequest request, String name) {
		Cookie cookies[] = request.getCookies();
		if (cookies == null) {
			return "";
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase(name)) {
				return cookie.getValue();
			}
		}

		return "";
	}
}