package com.ssafy.service.impl;

import com.ssafy.config.redis.CacheKey;
import com.ssafy.dao.EstimateDAO;
import com.ssafy.service.EstimateService;
import com.ssafy.vo.Estimate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EstimateServiceImpl implements EstimateService {

    private final EstimateDAO estimateDAO;

    @Cacheable(value = CacheKey.ESTIMATES, key ="#estimate", unless = "#result == null")
    @Override
    public List<Estimate> findEstimate(Estimate estimate) {
        return estimateDAO.findEstimate(estimate);
    }

    @CacheEvict(cacheNames = {CacheKey.ESTIMATES}, allEntries = true)
    @Override
    public int addEstimate(Estimate estimate) {
        return estimateDAO.addEstimate(estimate);
    }

    @CacheEvict(cacheNames = {CacheKey.ESTIMATES}, allEntries = true)
    @Override
    public int updateEstimate(Estimate estimate) {
        return estimateDAO.updateEstimate(estimate);
    }

    @CacheEvict(cacheNames = {CacheKey.ESTIMATES}, allEntries = true)
    @Override
    public int deleteEstimate(Estimate estimate) {
        return estimateDAO.deleteEstimate(estimate);
    }

    @Override
    public int deleteEstimateExplicitly(Estimate estimate) {
        return estimateDAO.deleteEstimateExplicitly(estimate);
    }

    @Override
    public int selectionEstimate(Estimate estimate) {
        return estimateDAO.selectionEstimate(estimate);
    }
}