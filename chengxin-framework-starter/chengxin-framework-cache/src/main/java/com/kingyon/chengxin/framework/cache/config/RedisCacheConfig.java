package com.kingyon.chengxin.framework.cache.config;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingyon.chengxin.framework.cache.redis.CacheConstants;
import com.kingyon.chengxin.framework.cache.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

@EnableCaching
//@PropertySource(value={"classpath:config/app/cache.properties"},encoding="UTF-8")
public class RedisCacheConfig extends CachingConfigurerSupport {
	private static final Logger logger = LoggerFactory.getLogger(RedisCacheConfig.class);

	@Autowired
	private CacheExpireKey cacheExpireKey;
	 /**
     * 自定义key.
     * 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key
     */
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder(CacheConstants.CACHE_PREFIX);
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				StringBuilder paramSub = new StringBuilder();
				for (Object obj : params) {
					paramSub.append(JSON.toJSONString(obj));
				}
				sb.append(encoderByMd5(paramSub.toString()));
				return sb.toString();
			}
		};
	}

	/**
     * 缓存管理器.
     * @param redisTemplate
     * @return
     */
//	@SuppressWarnings("rawtypes")
//	@Bean
//	public CacheManager cacheManager(RedisTemplate redisTemplate) {
//		RedisCacheManager cacheManager = new RedisCacheManager();
////		// 设置缓存过期时间,单位(秒)
////		//cacheManager.setDefaultExpiration(300);
////		//缓存过期设置
////		cacheManager.setExpires(cacheExpireKey.expireKeys());
//		return cacheManager;
//	}

	/**
	 * 自定义序列化方法
	 * @param factory
	 * @return
	 */
	@Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
//        StringRedisTemplate template = new StringRedisTemplate(factory);
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setDefaultSerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
//
//        return template;
//    }
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate template = new RedisTemplate();

		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		template.setConnectionFactory(factory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setDefaultSerializer(new StringRedisSerializer());
		template.afterPropertiesSet();

		return template;
	}
	
	@Bean
	public RedisUtil redisUtil() {
		return new RedisUtil();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public CacheExpireKey cacheExpireKey(){
		return new DefaultCacheExpireKey();
	}
	
	public String encoderByMd5(String str){
		if(str==null) return "null";
		//确定计算方法
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Encoder base64Encoder = Base64.getEncoder();
		//加密后的字符串
		String newstr=base64Encoder.encodeToString(md5.digest(str.getBytes(Charset.forName("utf-8"))));
		return newstr;
	}
	
	
}

