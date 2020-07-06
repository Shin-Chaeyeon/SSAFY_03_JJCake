package com.ssafy.service.impl;

import com.ssafy.config.redis.CacheKey;
import com.ssafy.dao.MessageDAO;
import com.ssafy.service.MessageService;
import com.ssafy.vo.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageDAO messageDAO;

    @Cacheable(value = CacheKey.MESSAGES, key ="#message", unless = "#result == null")
    @Override
    public List<Message> findMessage(Message message) {
        return messageDAO.findMessage(message);
    }

    @CacheEvict(cacheNames = {CacheKey.MESSAGES}, allEntries = true)
    @Override
    public int addMessage(Message message) {
        return messageDAO.addMessage(message);
    }

    @CacheEvict(cacheNames = {CacheKey.MESSAGES}, allEntries = true)
    @Override
    public int updateMessage(Message message) {
        return messageDAO.updateMessage(message);
    }

    @Override
    public int deleteMessageExplicitly(Message message) {
        return messageDAO.deleteMessageExplicitly(message);
    }
}
