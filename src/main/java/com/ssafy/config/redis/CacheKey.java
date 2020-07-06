package com.ssafy.config.redis;

public class CacheKey {
    public static final int DEFAULT_EXPIRE_SEC = 60; // 1 minutes

    public static final String USER = "user";
    public static final int USER_EXPIRE_SEC = 60 * 5; // 5 minutes

    public static final String MARKET = "market";
    public static final int MARKET_EXPIRE_SEC = 60 * 5; // 5 minutes

    public static final String PORTFOLIO = "portfolio";
    public static final String PORTFOLIOS = "portfolios";
    public static final int PORTFOLIO_EXPIRE_SEC = 60 * 5; // 5 minutes

    public static final String ORDER = "order";
    public static final String ORDERS = "orders";
    public static final int ORDER_EXPIRE_SEC = 60 * 5; // 5 minutes

    public static final String ESTIMATE = "estimate";
    public static final String ESTIMATES = "estimates";
    public static final int ESTIMATE_EXPIRE_SEC = 60 * 5; // 5 minutes

    public static final String MESSAGE = "message";
    public static final String MESSAGES = "messages";
    public static final int MESSAGE_EXPIRE_SEC = 60 * 5; // 5 minutes

    public static final String REVIEW = "review";
    public static final String REVIEWS = "reviews";
    public static final int REVIEW_EXPIRE_SEC = 60 * 5; // 5 minutes
}
