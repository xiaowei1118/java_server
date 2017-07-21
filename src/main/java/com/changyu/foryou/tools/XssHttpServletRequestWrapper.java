package com.changyu.foryou.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 
 * @author xiaowei
 *copyright @倡予信息科技有限公司
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getParameter(String name) {
		return clearXss(super.getParameter(name));
	}
	
	@Override
	public String getHeader(String name) {
		return clearXss(super.getHeader(name));
	}
	
	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if(values!=null){
			String[] newValues = new String[values.length];
			
			for(int i =0; i< values.length; i++){
				newValues[i] = clearXss(values[i]).trim();
			}

			values = newValues;
		}

		return values;
	}

	/**
	 * 处理字符转义
	 * @param value
	 * @return
	 */
	private String clearXss(String value){
		if (value == null || "".equals(value)) {
			return value;
		}

		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("\\(", "&#40;").replace("\\)", "&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replace("script", "");
		return value;
	}
	
}
