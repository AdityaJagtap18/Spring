package com.springcrud.CurdOparation;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.springcrud.CurdOparation.utils.ReadProperties;



@Component
public class CorsConfiguration implements Filter {

	private final Logger log = LoggerFactory.getLogger(CorsConfiguration.class);

	public CorsConfiguration() {
		log.info("SimpleCORSFilter init");
	}

	ReadProperties readProperties = new ReadProperties();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		String corsAllowedMethods = readProperties.getProperty("ACCESS_CONTROL_ALLOW_METHODS");
		if (corsAllowedMethods == null) {
			// if property is not present in property file set default method list
			corsAllowedMethods = "POST, GET, OPTIONS, DELETE, PUT";
		}
		logger.debug("corsAllowedMethods : " + corsAllowedMethods);
		response.setHeader("Access-Control-Allow-Methods", corsAllowedMethods);
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "*");

		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}

}