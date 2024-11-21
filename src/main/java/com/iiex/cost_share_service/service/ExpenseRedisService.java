package com.iiex.cost_share_service.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iiex.cost_share_service.entity.Expense;

@Service
public class ExpenseRedisService implements IExpenseRedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${app.cache.expense.ttl:3600}")
    private long cacheTtl;

    private String generateCacheKey(Long userId, Long groupId) {
        return "expense:user:" + userId + ":group:" + groupId;
    }

    @Override
    public List<Expense> getExpensesFromCache(Long userId, Long groupId) {
        String key = generateCacheKey(userId, groupId);
        Object cachedData = redisTemplate.opsForValue().get(key);

        if (cachedData != null) {
            try {
                String jsonData = objectMapper.writeValueAsString(cachedData);

            return objectMapper.readValue(jsonData, new TypeReference<List<Expense>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to deserialize expenses from cache", e);
            }
        }

        return null;
    }

    @Override
    public void saveExpensesToCache(Long userId, Long groupId, List<Expense> expenses) {
        String key = generateCacheKey(userId, groupId);
        redisTemplate.opsForValue().set(key, expenses, Duration.ofSeconds(cacheTtl));
    }

    @Override
    public void evictCache(Long userId, Long groupId) {
        redisTemplate.delete(generateCacheKey(userId, groupId));
    }

}
