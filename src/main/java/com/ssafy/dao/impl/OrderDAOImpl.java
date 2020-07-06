package com.ssafy.dao.impl;

import com.ssafy.dao.OrderDAO;
import com.ssafy.vo.Estimate;
import com.ssafy.vo.Order;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private String ns1 = "OrderMapper.";
    private String ns7 = "EstimateMapper.";

    @Autowired
    private SqlSession sqlSession;

    @Override
    @Transactional
    public List<Order> findOrder(Order order) {
        return sqlSession.selectList(ns1 + "findOrder", order);
    }

    @Override
    @Transactional
    public int addOrder(Order order) {
        return sqlSession.insert(ns1 + "addOrder",order);
    }

    @Override
    @Transactional
    public int updateOrder(Order order) {
        return sqlSession.update(ns1 + "updateOrder", order);
    }

    @Override
    @Transactional
    public int deleteOrder(Order order) {
        int result = 0;
        long idorder = order.getIdorder();
        result += sqlSession.update(ns1 + "deleteOrder", order);
        result += sqlSession.update(ns7 + "deleteEstimate", Estimate.builder().orderId(idorder).build());
        return result;
    }

    @Override
    @Transactional
    public int deleteOrderExplicitly(Order order) {
        return sqlSession.delete(ns1 + "deleteOrderExplicitly", order);
    }
}
