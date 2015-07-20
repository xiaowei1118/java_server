package com.changyu.foryou.tools;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.Filter;
public class IsLoginFilter implements Filter{
	 public FilterConfig config;
	    
	    public void destroy() {
	        this.config = null;
	    }
	    
	    public static boolean isContains(String container, String[] regx) {
	        boolean result = false;

	        for (int i = 0; i < regx.length; i++) {
	            if (container.indexOf(regx[i]) != -1) {
	                return true;
	            }
	        }
	        return result;
	    }

	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	        HttpServletRequest hrequest = (HttpServletRequest)request;
	        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response);
	        
	        String loginStrings = config.getInitParameter("loginStrings");        
	        String includeStrings = config.getInitParameter("includeStrings");    
	        String redirectPath = hrequest.getContextPath() + config.getInitParameter("redirectPath");
	        String disabletestfilter = config.getInitParameter("disabletestfilter");
	        
	        if (disabletestfilter.toUpperCase().equals("Y")) {   
	            chain.doFilter(request, response);
	            return;
	        }
	        String[] loginList = loginStrings.split(";");
	        String[] includeList = includeStrings.split(";");
	        
	        if (!IsLoginFilter.isContains(hrequest.getRequestURI(), includeList)) {
	            chain.doFilter(request, response);
	            return;
	        }
	        
	        if (IsLoginFilter.isContains(hrequest.getRequestURI(), loginList)) {//
	            chain.doFilter(request, response);
	            return;
	        }
	        
	        String user = ( String ) hrequest.getSession().getAttribute("phone");
	        if (user == null) {
	            wrapper.sendRedirect(redirectPath);
	            return;
	        }else {
	            chain.doFilter(request, response);
	            return;
	        }
	    }

	    public void init(FilterConfig filterConfig) throws ServletException {
	        config = filterConfig;
	    }
		
				  
}

