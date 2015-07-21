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

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.changyu.foryou.tools.Constants;
import com.changyu.foryou.tools.Sign;


/**
 * 实行对接口进行签名
 * @author xiaowei
 * @copyght 倡予科技有限公司
 */
public class SignFilter implements Filter {

	Logger logger=Logger.getLogger(SignFilter.class);
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		/*@SuppressWarnings("unchecked")
		HashMap<String,String[]> map=(HashMap<String,String[]>)request.getParameterMap();
		System.out.println((JSON.toJSONString(map.get("sign"))));
	    System.out.println(Sign.getSignature(map));
	    
	    Map<String,Object> resultMap=new HashMap<String,Object>();
	    PrintWriter out=response.getWriter();
	    Long timeStamp=Long.parseLong(map.get("timestamp")[0]);
	    if((new Date().getTime()-timeStamp)>10*60){
	    	resultMap.put(Constants.STATUS, Constants.FAILURE);
	    	resultMap.put(Constants.MESSAGE, "会话已超时");
	    	out.print(JSON.toJSONString(resultMap));
	    }*/
	    
	    //if(map.get(""))
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
