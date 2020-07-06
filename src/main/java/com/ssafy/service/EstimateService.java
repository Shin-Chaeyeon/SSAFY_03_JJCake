package com.ssafy.service;

import com.ssafy.vo.Estimate;

import java.util.List;

public interface EstimateService {
    public List<Estimate> findEstimate(Estimate estimate);
    public int addEstimate(Estimate estimate);
    public int updateEstimate(Estimate estimate);
    public int deleteEstimate(Estimate estimate);
    public int deleteEstimateExplicitly(Estimate estimate);
    public int selectionEstimate(Estimate estimate);
}
