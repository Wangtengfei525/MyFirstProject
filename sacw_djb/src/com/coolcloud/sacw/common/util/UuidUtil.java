package com.coolcloud.sacw.common.util;

import java.util.UUID;

public class UuidUtil {

	/**
	 * 随机生成32位唯一识别码
	 * 
	 * @return
	 */
	public static String get32UUID() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
		
		
		
	}

	
	
	
}
