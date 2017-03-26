package com.gafker.www.util;


public class Castutil {
	/**
	 * 转换为String(默认为空字符串)
	 * 
	 * @param obj
	 * @return
	 */
	public static String castString(Object obj) {
		return Castutil.castString(obj, "");
	}

	/**
	 * 转换为String(提供默认值)
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static String castString(Object obj, String defaultValue) {
		return obj != null ? String.valueOf(obj) : defaultValue;
	}

	/**
	 * 转换为Double(默认为空字符串)
	 * 
	 * @param obj
	 * @return
	 */
	public static double castDouble(Object obj) {
		return Castutil.castDouble(obj, 0d);
	}

	/**
	 * 转换为Double(提供默认值)
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static double castDouble(Object obj, double defaultValue) {
		double doubleValue = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					doubleValue = Double.parseDouble(strValue);
				} catch (NumberFormatException e) {
					doubleValue = defaultValue;
				}
			}
		}
		return doubleValue;
	}

	/**
	 * 转换为long(默认为空字符串)
	 * 
	 * @param obj
	 * @return
	 */
	public static long castLong(Object obj) {
		return Castutil.castLong(obj, 0l);
	}

	/**
	 * 转换为long(提供默认值)
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static long castLong(Object obj, long defaultValue) {
		long longValue = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					longValue = Long.parseLong(strValue);
				} catch (NumberFormatException e) {
					longValue = defaultValue;
				}
			}
		}
		return longValue;
	}

	/**
	 * 转换为int(默认为空字符串)
	 * 
	 * @param obj
	 * @return
	 */
	public static int castInt(Object obj) {
		return Castutil.castInt(obj, 0);
	}

	/**
	 * 转换为int(提供默认值)
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static int castInt(Object obj, int defaultValue) {
		int intValue = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					intValue = Integer.parseInt(strValue);
				} catch (NumberFormatException e) {
					intValue = defaultValue;
				}
			}
		}
		return intValue;
	}

	/**
	 * 转换为boolean(默认为空字符串)
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean castBoolean(Object obj) {
		return Castutil.castBoolean(obj, false);
	}

	/**
	 * 转换为boolean(提供默认值)
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static boolean castBoolean(Object obj, boolean defaultValue) {
		boolean booleanValue = defaultValue;
		if (obj != null) {
			booleanValue = Boolean.parseBoolean(castString(obj));
		}
		return booleanValue;
	}
}
