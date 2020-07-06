package com.ssafy.service.impl;

import com.ssafy.config.redis.CacheKey;
import com.ssafy.dao.MarketDAO;
import com.ssafy.service.MarketService;
import com.ssafy.vo.Market;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MarketServiceImpl implements MarketService {

    private final MarketDAO marketDAO;

    @Cacheable(value = CacheKey.MARKET, key ="#market", unless = "#result == null")
    @Override
    public List<Market> findMarket(Market market) {
        return marketDAO.findMarket(market);
    }

    @CacheEvict(cacheNames = {CacheKey.MARKET}, allEntries = true)
    @Override
    public int addMarket(Market market) {
        return marketDAO.addMarket(market);
    }

    @CacheEvict(cacheNames = {CacheKey.MARKET}, allEntries = true)
    @Override
    public int updateMarket(Market market) {
        return marketDAO.updateMarket(market);
    }

    @CacheEvict(cacheNames = {CacheKey.MARKET}, allEntries = true)
    @Override
    public int deleteMarket(Market market) {
        return marketDAO.deleteMarket(market);
    }

    @Override
    public int deleteMarketExplicitly(Market market) {
        return marketDAO.deleteMarketExplicitly(market);
    }
}