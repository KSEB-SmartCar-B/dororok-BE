package com.smartcar.dororok.search.service;

import com.smartcar.dororok.global.auth.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private static final int MAX_SEARCHES = 20;

    private final RedisTemplate<String, String> redisTemplate;

    private String getKeyForUser() {
        String username = SecurityUtils.getCurrentUsername();
        return "recentSearches:" + username;
    }

    public void addSearchLog(String searchLog) {
        String key = getKeyForUser();

        // 이미 리스트에 있는 경우 제거
        redisTemplate.opsForList().remove(key, 1, searchLog);

        // 검색어를 리스트의 맨 위로 추가
        redisTemplate.opsForList().leftPush(key, searchLog);

        // 리스트 크기를 MAX_SEARCHES로 유지
        redisTemplate.opsForList().trim(key, 0, MAX_SEARCHES - 1);
    }

    public List<String> getRecentSearchLogs() {
        String key = getKeyForUser();
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public void deleteSearchLog(String searchLog) {
        String key = getKeyForUser();
        redisTemplate.opsForList().remove(key, 0, searchLog);
    }

    public void deleteAllSearchLogs() {
        String key = "recentSearches:" + SecurityUtils.getCurrentUsername();
        redisTemplate.delete(key);
    }
}
