package com.ssafy.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@EnableCaching
@Configuration
public class RedisConfig {
    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() // null value 캐시안함
                .entryTtl(Duration.ofSeconds(CacheKey.DEFAULT_EXPIRE_SEC)) // 캐시의 기본 유효시간 설정
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        // redis 캐시 데이터 저장방식을 StringSeriallizer로 지정
        // 캐시키별 default 유효시간 설정
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(CacheKey.USER, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.USER_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.MARKET, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.MARKET_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.PORTFOLIO, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.PORTFOLIO_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.PORTFOLIOS, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.PORTFOLIO_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.ORDER, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.ORDER_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.ORDERS, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.ORDER_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.ESTIMATE, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.ESTIMATE_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.ESTIMATES, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.ESTIMATE_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.REVIEW, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.REVIEW_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.REVIEWS, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.REVIEW_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.MESSAGE, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.MESSAGE_EXPIRE_SEC)));
        cacheConfigurations.put(CacheKey.MESSAGES, RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheKey.MESSAGE_EXPIRE_SEC)));
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory).cacheDefaults(configuration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }
}
