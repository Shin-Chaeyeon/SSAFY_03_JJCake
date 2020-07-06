package com.ssafy.dao;

import com.ssafy.vo.Message;

import java.util.List;

public interface MessageDAO {
    public List<Message> findMessage(Message message);
    public int addMessage(Message message);
    public int updateMessage(Message message);
    public int deleteMessageExplicitly(Message message);
}
