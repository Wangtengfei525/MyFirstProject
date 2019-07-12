package com.coolcloud.sacw.operation.controller;

import java.io.UnsupportedEncodingException;

public class Commons {

	public   String    SolveIllegalCharacter(String  composite)
	{
		String   s1=null;
		if(composite!=null&&composite!="")
    	{
    		 try {
    		 s1=	 new String(composite.getBytes("ISO8859-1"), "UTF-8");
         	} catch (UnsupportedEncodingException e) {
         		e.printStackTrace();
         	}
    	}
		return  s1;
	}
	
	
	
}
