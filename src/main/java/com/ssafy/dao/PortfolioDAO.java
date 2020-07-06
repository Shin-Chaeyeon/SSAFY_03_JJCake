package com.ssafy.dao;

import com.ssafy.vo.Portfolio;

import java.util.List;

public interface PortfolioDAO {
    public List<Portfolio> findPortfolio(Portfolio portfolio);
    public int addPortfolio(Portfolio portfolio);
    public int updatePortfolio(Portfolio portfolio);
    public int deletePortfolio(Portfolio portfolio);
    public int deletePortfolioExplicitly(Portfolio portfolio);
}