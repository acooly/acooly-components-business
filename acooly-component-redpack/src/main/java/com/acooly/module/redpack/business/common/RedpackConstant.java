package com.acooly.module.redpack.business.common;

/**
 * 红包 常量
 * 
 * @author CuiFuQ
 *
 */
public class RedpackConstant {
	
	
	/** redis标记 [_redis_] **/
	public static final String REDIS_MAIN = "_redis_";
	
	/** 红包锁 [_redis_lock_] **/
	public static final String REDIS_LOCK = "_redis_lock_";
	
	/** 红包列表 [_redis_list_] **/
	public static final String REDIS_LIST = "_redis_list_";
	
	/** 红包Map列表 [_redis_map_] **/
	public static final String REDIS_MAP = "_redis_map_";

	/** 过期监听 [_redis_listener_] **/
	public static final String REDIS_LISTENER = "_redis_listener_";

	/** 过期监听-标记[_redis_listener-mark_] **/
	public static final String REDIS_LISTENER_MARK = "_redis_listener-mark_";

}
