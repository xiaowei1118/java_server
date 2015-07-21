package com.changyu.foryou.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.changyu.foryou.tools.XssHttpServletRequestWraper;

/**
 * 防止xss攻击过滤器
 * @author xiaowei
 *
 */
public class XssFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		  chain.doFilter(new XssHttpServletRequestWraper((HttpServletRequest) request), response);
	}

	@Override
	public void destroy() {
		
	}

}
