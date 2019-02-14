package com.kingyon.chengxin.framework.cache.config;

import java.util.Collections;
import java.util.Map;

public class DefaultCacheExpireKey implements CacheExpireKey{

	@Override
	public Map<String, Long> expireKeys() {
		return Collections.emptyMap();
	}

}
