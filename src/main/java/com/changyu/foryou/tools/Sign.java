package com.changyu.foryou.tools;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.KeyGenerator;

/***
 * 
 * @author xiaowei
 *coryright @ xiaowei
 */

public class Sign {
	/**
	 * 签名生成算法
	 * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型
	 * @param String secret 签名密钥
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignature(HashMap<String,String[]> params) throws IOException
	{
		String[] secret=params.get("secret");
		//params.remove("secret");
	    // 先将参数以其参数名的字典序升序进行排序
	    Map<String, String[]> sortedParams = new TreeMap<String, String[]>(params);
	    Set<Entry<String, String[]>> entrys = sortedParams.entrySet();
	   
	    // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
	    StringBuilder basestring = new StringBuilder();
	    for (Entry<String, String[]> param : entrys) {
	    	if(param.getKey().equals("timestamp")||param.getKey().equals("secret")||param.getKey().equals("sign")){
	    		continue;
	    	}
	    	String value = ((String[])param.getValue())[0];
	        basestring.append(param.getKey()).append("=").append(value);
	    }
	    basestring.append(secret[0]);
	 
	    // 使用MD5对待签名串求签
	    byte[] bytes = null;
	    try {
	      MessageDigest md5 = MessageDigest.getInstance("MD5");    
	      bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
	      System.out.println(basestring.toString());  //一次
	      
	    } catch (GeneralSecurityException ex) {
	        throw new IOException(ex);
	    }
	 
	    // 将MD5输出的二进制结果转换为小写的十六进制
	    StringBuilder sign = new StringBuilder();
	    for (int i = 0; i < bytes.length; i++) {
	        String hex = Integer.toHexString(bytes[i] & 0xFF);
	        if (hex.length() == 1) {
	            sign.append("0");
	        }
	        sign.append(hex);
	    }
	    return sign.toString();
	}
	
	public static byte[] createPrivateKey(){
		byte[] privateKey = null;
		try {
			KeyGenerator keyGenerator=KeyGenerator.getInstance("DESede");
			 keyGenerator.init(168); //选择DESede算法,密钥长度为112位或168位   
	         Key myKey = keyGenerator.generateKey(); //生成密钥   
	        // System.out.println("得到单钥加密密钥"+myKey.getEncoded());
	         privateKey=myKey.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return privateKey;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(new String(Sign.createPrivateKey()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
