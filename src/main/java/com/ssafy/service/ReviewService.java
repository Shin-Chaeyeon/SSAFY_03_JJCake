package com.ssafy.service;

import com.ssafy.vo.Review;

import java.util.List;

public interface ReviewService {
    public List<Review> findReview(Review review);
    public int addReview(Review review);
    public int updateReview(Review review);
    public int deleteReview(Review review);
    public int deleteReviewExplicitly(Review review);
}
