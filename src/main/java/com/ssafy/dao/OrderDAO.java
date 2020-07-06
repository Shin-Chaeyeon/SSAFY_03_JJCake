package com.ssafy.dao;

import com.ssafy.vo.Order;

import java.util.List;

public interface OrderDAO {
    public List<Order> findOrder(Order order);
    public int addOrder(Order order);
    public int updateOrder(Order order);
    public int deleteOrder(Order order);
    public int deleteOrderExplicitly(Order order);
}
