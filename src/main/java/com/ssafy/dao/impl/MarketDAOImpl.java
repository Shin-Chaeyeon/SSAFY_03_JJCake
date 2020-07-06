package com.ssafy.dao.impl;

import com.ssafy.dao.MarketDAO;
import com.ssafy.vo.Market;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MarketDAOImpl implements MarketDAO {

    private String ns1 = "MarketMapper.";

    @Autowired
    private SqlSession sqlSession;

    @Override
    @Transactional
    public List<Market> findMarket(Market market) {
        return sqlSession.selectList(ns1+"findMarket", market);
    }

    @Override
    @Transactional
    public int addMarket(Market market) {
        return sqlSession.insert(ns1 + "addMarket", market);
    }

    @Override
    @Transactional
    public int updateMarket(Market market) {
        return sqlSession.update(ns1 + "updateMarket", market);
    }

    @Override
    @Transactional
    public int deleteMarket(Market market) {
        return sqlSession.update(ns1 + "deleteMarket", market);
    }

    @Override
    @Transactional
    public int deleteMarketExplicitly(Market market) {
        return sqlSession.delete(ns1 + "deleteMarketExplicitly", market);
    }
}
