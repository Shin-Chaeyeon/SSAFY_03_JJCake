package com.ssafy.dao.impl;

import com.ssafy.dao.EstimateDAO;
import com.ssafy.type.Status;
import com.ssafy.vo.Estimate;
import com.ssafy.vo.Order;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EstimateDAOImpl implements EstimateDAO {

    private String ns1 = "EstimateMapper.";
    private String ns5 = "OrderMapper.";

    @Autowired
    private SqlSession sqlSession;

    @Override
    @Transactional
    public List<Estimate> findEstimate(Estimate estimate) {
        return sqlSession.selectList(ns1+"findEstimate", estimate);
    }

    @Override
    @Transactional
    public int addEstimate(Estimate estimate) {
        return sqlSession.insert(ns1 + "addEstimate", estimate);
    }

    @Override
    @Transactional
    public int updateEstimate(Estimate estimate) {
        return sqlSession.update(ns1 + "updateEstimate", estimate);
    }

    @Override
    @Transactional
    public int deleteEstimate(Estimate estimate) {
        return sqlSession.update(ns1 + "deleteEstimate", estimate);
    }

    @Override
    @Transactional
    public int deleteEstimateExplicitly(Estimate estimate) {
        return sqlSession.delete(ns1 + "deleteEstimateExplicitly", estimate);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public int selectionEstimate(Estimate estimate) {
        int result = 0;
        List<Order> list = sqlSession.selectList(ns5 + "findOrder", Order.builder().idorder(estimate.getOrderId()).build());
        Order order = list.get(0);
        order.setSellerId(estimate.getSellerId());
        order.setStatus(Status.MATCHED.NUMBER);
        result += sqlSession.update(ns1 + "updateEstimate", estimate);
        result += sqlSession.update(ns5 + "updateOrder", order);
        return result;
    }
}
