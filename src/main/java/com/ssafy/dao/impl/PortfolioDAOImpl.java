package com.ssafy.dao.impl;

import com.ssafy.dao.PortfolioDAO;
import com.ssafy.vo.Portfolio;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PortfolioDAOImpl implements PortfolioDAO {

    private String ns1 = "PortfolioMapper.";

    @Autowired
    private SqlSession sqlSession;

    @Override
    @Transactional
    public List<Portfolio> findPortfolio(Portfolio portfolio) {
        return sqlSession.selectList(ns1+"findPortfolio", portfolio);
    }

    @Override
    @Transactional
    public int addPortfolio(Portfolio portfolio) {
        return sqlSession.insert(ns1 + "addPortfolio", portfolio);
    }

    @Override
    @Transactional
    public int updatePortfolio(Portfolio portfolio) {
        return sqlSession.update(ns1 + "updatePortfolio", portfolio);
    }

    @Override
    @Transactional
    public int deletePortfolio(Portfolio portfolio) {
        return sqlSession.update(ns1 + "deletePortfolio", portfolio);
    }

    @Override
    @Transactional
    public int deletePortfolioExplicitly(Portfolio portfolio) {
        return sqlSession.update(ns1 + "deletePortfolioExplicitly", portfolio);
    }
}
