package com.avensys.rts.usergroupservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.avensys.rts.usergroupservice.interceptor.AuditInterceptor;
import com.avensys.rts.usergroupservice.interceptor.AuthInterceptor;

/**
 * @author Kotaiah nalleboina This class is used to configure the application.
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

	@Autowired
	private AuthInterceptor authInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuditInterceptor());
		registry.addInterceptor(authInterceptor);
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}