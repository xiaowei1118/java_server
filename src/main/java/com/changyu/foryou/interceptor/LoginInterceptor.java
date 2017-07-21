package com.changyu.foryou.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhengzhihao on 2017/7/21.
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String[] loginList = new String[]{"login", "toLogin"};

        if (isContains(httpServletRequest.getRequestURI(), loginList)) {
            return true;
        }

        String user = (String) httpServletRequest.getSession().getAttribute("campusAdmin");
        if (user == null) {
            httpServletResponse.sendRedirect("/login.html");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private boolean isContains(String container, String[] url) {
        boolean result = false;

        for (int i = 0; i < url.length; i++) {
            if (container.contains(url[i])) {
                result = true;
            }
        }

        return result;
    }
}
