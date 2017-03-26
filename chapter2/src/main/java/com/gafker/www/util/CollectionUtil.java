package com.gafker.www.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

/**
 * 集合工具类
 * 
 * @author gafker
 *
 */
public class CollectionUtil {
	/**
	 * 判断Collection 是否为空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return CollectionUtils.isEmpty(collection);
	}

	/**
	 * 判断Collection 是否非空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}
	/**
	 * 判断Map是否为空
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?,?> map){
		return MapUtils.isEmpty(map);
	}
	/**
	 * 判断Map为是否非空
	 * @param map
	 * @return
	 */
	public static boolean isNotEmpty(Map<?,?> map){
		return !isEmpty(map);
	}
}
