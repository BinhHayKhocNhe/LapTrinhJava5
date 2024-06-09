package com.Lab_8;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class I18NConfig implements WebMvcConfigurer {
	@Bean("messageSource")
	public MessageSource getHomeMessageSource() {
		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
		ms.setBasenames("classpath:i18n/home", "classpath:i18n/aside");
		ms.setDefaultEncoding("UTF-8");
		return ms;
	}

	@Bean 
	public LocaleResolver localeResolver() {
		CookieLocaleResolver clr = new CookieLocaleResolver();
		clr.setDefaultLocale(Locale.ENGLISH);
		clr.setCookieName("myAppLocaleCookie");
		clr.setCookieMaxAge(3600);
		return clr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor locale = new LocaleChangeInterceptor();
		locale.setParamName("lang");
		registry.addInterceptor(locale).addPathPatterns("/**");
	}

	@Bean("localeResolver")
	public LocaleResolver getLocaleResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("vi"));
		resolver.setCookieMaxAge(10 * 24 * 60 * 60); // 10 ngày
		resolver.setCookiePath("/");
		return resolver;
	}
}
