package com.kingyon.chengxin.framework.cache.config;

import java.util.Map;

public interface CacheExpireKey {

	/**
	 * key  要设置过期时间的key
	 * value 过期时间单位为秒
	 * @return
	 */
	public Map<String,Long> expireKeys();
}
