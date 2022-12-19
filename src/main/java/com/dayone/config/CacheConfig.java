package com.dayone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class CacheConfig {

	@Value(value = "${spring.redis.host}")
	private String host;

	@Value(value = "${spring.redis.port}")
	private int port;

	@Bean
	public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
			//직렬화 / 역직렬화 방식
			.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
			.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

		return RedisCacheManager.RedisCacheManagerBuilder
			.fromConnectionFactory(redisConnectionFactory)
			.cacheDefaults(conf)
			.build();

	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		// RedisClusterConnection conf //for clustering

		//single instance server
		RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration();
		conf.setHostName(this.host);
		conf.setPort(this.port);

		return new LettuceConnectionFactory(conf);
	}
}
