package com.ecarinfo.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @Description: 封装各种生成唯一性ID算法的工具类
 * @Date 2012-11-6
 * @Version V1.0
 */

public class IdentitiesUtil {
	/**
	 * @Title:生成去掉"-"的32位UUID
	 * @return
	 */
	public static String uuid32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid36() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}
}
