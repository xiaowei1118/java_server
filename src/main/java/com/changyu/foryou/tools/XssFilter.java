package com.changyu.foryou.tools;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class XssFilter implements Filter {

	Logger logger=Logger.getLogger(XssFilter.class);
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		/*@SuppressWarnings("unchecked")
		HashMap<String,String> map=(HashMap<String,String>)request.getParameterMap();
		map.remove("secret");
		
	    logger.debug(Sign.getSignature(map));*/
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
