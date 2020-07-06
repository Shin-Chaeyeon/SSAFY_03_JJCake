package com.ssafy.dao.impl;

import com.ssafy.dao.ReviewDAO;
import com.ssafy.vo.Review;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ReviewDAOImpl implements ReviewDAO {

    private String ns1 = "ReviewMapper.";

    @Autowired
    private SqlSession sqlSession;

    @Override
    @Transactional
    public List<Review> findReview(Review review) {
        return sqlSession.selectList(ns1 + "findReview", review);
    }

    @Override
    @Transactional
    public int addReview(Review review) {
        return sqlSession.insert(ns1 + "addReview", review);
    }

    @Override
    @Transactional
    public int updateReview(Review review) {
        return sqlSession.update(ns1 + "updateReview", review);
    }

    @Override
    @Transactional
    public int deleteReview(Review review) {
        return sqlSession.update(ns1 + "deleteReview", review);
    }

    @Override
    @Transactional
    public int deleteReviewExplicitly(Review review) {
        return sqlSession.delete(ns1 + "deleteReviewExplicitly", review);
    }
}
