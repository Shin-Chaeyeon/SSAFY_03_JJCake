package com.ssafy.dao.impl;

import com.ssafy.dao.MessageDAO;
import com.ssafy.vo.Message;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MessageDAOImpl implements MessageDAO {

    private String ns1 = "MessageMapper.";

    @Autowired
    private SqlSession sqlSession;

    @Override
    @Transactional
    public List<Message> findMessage(Message message) {
        return sqlSession.selectList(ns1 + "findMessage", message);
    }

    @Override
    @Transactional
    public int addMessage(Message message) {
        return sqlSession.insert(ns1 + "addMessage", message);
    }

    @Override
    @Transactional
    public int updateMessage(Message message) {
        return sqlSession.update(ns1 + "updateMessage", message);
    }

    @Override
    @Transactional
    public int deleteMessageExplicitly(Message message) {
        return sqlSession.delete(ns1 + "deleteMessageExplicitly", message);
    }
}
