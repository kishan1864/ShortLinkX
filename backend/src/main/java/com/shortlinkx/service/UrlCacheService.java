package com.shortlinkx.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class UrlCacheService {

    private static final String KEY_PREFIX = "shorturl:";
    private static final Duration CACHE_TTL = Duration.ofHours(1);

    private final StringRedisTemplate redisTemplate;

    public UrlCacheService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getOriginalUrl(String shortCode) {
        return redisTemplate
                .opsForValue()
                .get(KEY_PREFIX + shortCode);
    }

    public void cacheOriginalUrl(
            String shortCode,
            String originalUrl
    ) {
        redisTemplate
                .opsForValue()
                .set(
                        KEY_PREFIX + shortCode,
                        originalUrl,
                        CACHE_TTL
                );
    }

    public void evict(String shortCode) {
        redisTemplate.delete(KEY_PREFIX + shortCode);
    }
}