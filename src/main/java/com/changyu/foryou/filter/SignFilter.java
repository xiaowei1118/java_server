package com.changyu.foryou.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.changyu.foryou.service.KeyService;
import com.changyu.foryou.tools.Constants;
import com.changyu.foryou.tools.Sign;

/**
 * 实行对接口进行签名
 * 
 * @author xiaowei
 * @copyght 倡予科技有限公司
 */
public class SignFilter implements Filter {

	Logger logger = Logger.getLogger(SignFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		@SuppressWarnings("unchecked")
		HashMap<String, String[]> map = (HashMap<String, String[]>) request
				.getParameterMap();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSON.toJSONString(resultMap);

		if (map.get("server") != null
				&& map.get("server")[0]
						.equals("56846a8a2fee49d14901d39cc48b8b2a")) {
			chain.doFilter(request, response);
			return;
		}
		if (map.get("timestamp") == null || map.get("secret") == null
				|| map.get("sign") == null) { // 签名参数不能为空
			response.reset();
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			resultMap.put(Constants.STATUS, Constants.FAILURE);
			resultMap.put(Constants.MESSAGE, "签名必要参数不能为空");
			out.print(JSON.toJSONString(resultMap));
			return;
		}

		String sign = Sign.getSignature(map);
		String secret = map.get("secret")[0];
		System.out.println("server=" + sign);
		System.out.println("client=" + map.get("sign")[0]);
		/*
		 * System.out.println(timeStamp); System.out.println(new
		 * Date().getTime());
		 */
		if ((new Date().getTime() - Long.parseLong(map.get("timestamp")[0])) > 10 * 1000 * 6) {
			response.reset();
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			resultMap.put(Constants.STATUS, Constants.FAILURE);
			resultMap.put(Constants.MESSAGE, "会话已超时");
			out.print(JSON.toJSONString(resultMap));
			return;
		} else if (!map.get("sign")[0].equals(sign)) {
			response.reset();
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			resultMap.put(Constants.STATUS, Constants.FAILURE);
			resultMap.put(Constants.MESSAGE, "接口签名错误");
			out.print(JSON.toJSONString(resultMap));
			return;
		} else {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("secrect", secret);
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(httpRequest.getSession()
							.getServletContext());

			// String flag=keyService.SelectKey(paramMap);
			KeyService keyService = (KeyService) wac.getBean("keyService");
			String flag = keyService.SelectKey(paramMap);
			if (flag == null) {
				response.reset();
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();

				resultMap.put(Constants.STATUS, Constants.FAILURE);
				resultMap.put(Constants.MESSAGE, "密钥不存在");
				out.print(JSON.toJSONString(resultMap));
				return;
			}
			chain.doFilter(request, response);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
