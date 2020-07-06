package com.ssafy.dao;

import com.ssafy.vo.Market;

import java.util.List;

public interface MarketDAO {
    public List<Market> findMarket(Market market);
    public int addMarket(Market market);
    public int updateMarket(Market market);
    public int deleteMarket(Market market);
    public int deleteMarketExplicitly(Market market);
}
