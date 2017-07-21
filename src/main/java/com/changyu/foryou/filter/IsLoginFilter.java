//package com.changyu.foryou.filter;
//
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponseWrapper;
//import java.io.IOException;
//
//@Component
//public class IsLoginFilter implements Filter {
//    public FilterConfig config;
//
//    public void destroy() {
//        this.config = null;
//    }
//
//    public static boolean isContains(String container, String[] regx) {
//        boolean result = false;
//
//        for (int i = 0; i < regx.length; i++) {
//            if (container.contains(regx[i])) {
//                result = true;
//            }
//        }
//
//        return result;
//    }
//
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        chain.doFilter(request,response);
//        HttpServletRequest hrequest = (HttpServletRequest) request;
//        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response);
//
//        String[] loginList = new String[]{"login","toLogin"};
//
//        if (IsLoginFilter.isContains(hrequest.getRequestURI(), loginList)) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String user = (String) hrequest.getSession().getAttribute("campusAdmin");
//        if (user == null) {
//            wrapper.sendRedirect("/login.html");
//            return;
//        } else {
//            chain.doFilter(request, response);
//            return;
//        }
//    }
//
//    public void init(FilterConfig filterConfig) throws ServletException {
//        config = filterConfig;
//    }
//}
//
