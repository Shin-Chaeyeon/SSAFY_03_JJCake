package com.ssafy.jjc;

import com.ssafy.dao.*;
import com.ssafy.type.*;
import com.ssafy.vo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
class JjcApplicationTests {
    @Autowired
    private MemberDAO memberDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private EstimateDAO estimateDAO;
    @Autowired
    private PortfolioDAO portfolioDAO;
    @Autowired
    private ReviewDAO reviewDAO;
    @Autowired
    private MarketDAO marketDAO;
    @Autowired
    private MessageDAO messageDAO;

    @Test
    void contextLoads() {
//        memberTest();
//        orderTest();
//        estimateTest();
//        portfolioTest();
//        reviewTest();
//        marketTest();
//        messageTest();
    }

    /* MemeberDAO TEST*/
    private void memberTest() {
        Member member = Member.builder()
                .email("admin@gmail.com")
                .password("1234")
                .admin(Admin.SELLER.NUMBER)
                .certification(Certification.HOME_PAGE.NUMBER)
                .nickname("판매자다")
                .address("해운대 바로앞")
                .roles(Collections.singletonList("ROLE_SELLER"))
                .isDeleted(IsDeleted.FALSE.NUMBER).build();
        // 삽입
        memberDAO.addMember(member);
        // 조회
        long memberPk = member.getIdmember();
        Member result = memberDAO.findMember(Member.builder().idmember(memberPk).build()).get(0);
        System.out.println(result);
        // 수정
        result.setNickname("어익후");
        memberDAO.updateMember(result);
        System.out.println(memberDAO.findMember(Member.builder().idmember(memberPk).build()).get(0));
        // 삭제
        memberDAO.deleteMember(result);
        System.out.println(memberDAO.findMember(Member.builder().idmember(memberPk).build()).get(0));
        // 진짜 삭제
        memberDAO.deleteMemberExplicitly(result);
    }

    /* OrderDAO TEST */
    private void orderTest(){
        Order order = Order.builder()
                .buyerId(2L)
                .sellerId(4L)
                .title("내일파뤼할거에요")
                .contents("진쨔진쨔 마싯는 케이크주떼염 >_<")
                .status(Status.SUBMITTED.NUMBER)
                .maxPrice(50_000)
                .radius(10)
                .receiveWay(ReceiveWay.DELIVERY.NUMBER)
                .receiveDate("2020-05-30")
                .dueDate("2020-05-29")
                .isDeleted(IsDeleted.FALSE.NUMBER).build();
        // 삽입
        orderDAO.addOrder(order);
        // 조회
        Order result = orderDAO.findOrder(Order.builder().idorder(3L).build()).get(0);
        System.out.println(result);
        // 수정
        result.setTitle("아아아앙 너무 급해요");
        orderDAO.updateOrder(result);
        result = orderDAO.findOrder(Order.builder().idorder(3L).build()).get(0);
        System.out.println(result);
        // 삭제
        orderDAO.deleteOrder(Order.builder().idorder(1L).build());
        // 진짜 삭제
        orderDAO.deleteOrderExplicitly(Order.builder().idorder(1L).build());
    }

    /* EstimateDAO TEST */
    private void estimateTest() {
        Estimate estimate = Estimate.builder()
                .sellerId(9)
                .orderId(3)
                .contents("나도 만들수있는뎀")
                .price(40000)
                .isSelected(1)
                .isDeleted(1).build();
        // 삽입
        estimateDAO.addEstimate(estimate);
        // 조회
        long estimatePk = estimate.getIdestimate();
        Estimate result = estimateDAO.findEstimate(Estimate.builder().idestimate(estimatePk).build()).get(0);
        System.out.println(result);
        // 수정
        result.setPrice(39000);
        estimateDAO.updateEstimate(result);
        System.out.println(estimateDAO.findEstimate(Estimate.builder().idestimate(estimatePk).build()).get(0));
        // 삭제
        estimateDAO.deleteEstimate(result);
        System.out.println(estimateDAO.findEstimate(Estimate.builder().idestimate(estimatePk).build()).get(0));
        // 진짜 삭제
        estimateDAO.deleteEstimateExplicitly(result);
    }

    /* PortfolioDAO TEST */
    private void portfolioTest() {
        Portfolio portfolio = Portfolio.builder()
                .sellerId(9)
                .title("my pf")
                .contents("this is contents of pf")
                .imageUrl("www.ww.ww")
                .isDeleted(1).build();
        // 삽입
        portfolioDAO.addPortfolio(portfolio);
        // 조회
        long portfolioPk = portfolio.getIdportfolio();
        Portfolio result = portfolioDAO.findPortfolio(Portfolio.builder().idportfolio(portfolioPk).build()).get(0);
        System.out.println(result);
        // 수정
        result.setTitle("my new title");
        portfolioDAO.updatePortfolio(result);
        System.out.println(portfolioDAO.findPortfolio(Portfolio.builder().idportfolio(portfolioPk).build()).get(0));
        // 삭제
        portfolioDAO.deletePortfolio(result);
        System.out.println(portfolioDAO.findPortfolio(Portfolio.builder().idportfolio(portfolioPk).build()).get(0));
        // 진짜 삭제
        portfolioDAO.deletePortfolioExplicitly(result);
    }

    /* Review TEST */
    private void reviewTest() {
        Review review = Review.builder()
                .orderId(3)
                .buyerId(6)
                .sellerId(9)
                .stars(5)
                .contents("제가 원한대로네요")
                .imageUrl("www.aa.bb")
                .isDeleted(1).build();
        // 삽입
        reviewDAO.addReview(review);
        // 조회
        long reviewPk = review.getIdreview();
        Review result = reviewDAO.findReview(Review.builder().idreview(reviewPk).build()).get(0);
        System.out.println(result);
        // 수정
        result.setStars(3);
        reviewDAO.updateReview(result);
        System.out.println(reviewDAO.findReview(Review.builder().idreview(reviewPk).build()).get(0));
        // 삭제
        reviewDAO.deleteReview(result);
        System.out.println(reviewDAO.findReview(Review.builder().idreview(reviewPk).build()).get(0));
        // 진짜 삭제
        reviewDAO.deleteReviewExplicitly(result);
    }

    /* MarketDAO TEST */
    private void marketTest() {
        Market market = Market.builder()
                .sellerId(1)
                .name("메죠오옹드케익")
                .intro("고오급 주문제작 케익숍입니다")
                .openTime("2020-01-01 08:00:00")
                .closeTime("2020-01-01 22:00:00")
                .dayOff("매주 수요일 휴무")
                .phoneNumber("010-5656-5656")
                .address("평양시")
                .deliveryArea("북한 전지역 쌉가능")
                .businessRegistrationNumber("14-93258-32")
                .isDeleted(1).build();
        // 삽입
        marketDAO.addMarket(market);
        // 조회
        long marketPk = market.getIdmarket();
        Market result = marketDAO.findMarket(Market.builder().idmarket(marketPk).build()).get(0);
        System.out.println(result);
        // 수정
        result.setName("new market name");
        marketDAO.updateMarket(result);
        System.out.println(marketDAO.findMarket(Market.builder().idmarket(marketPk).build()).get(0));
        // 삭제
        marketDAO.deleteMarket(result);
        System.out.println(marketDAO.findMarket(Market.builder().idmarket(marketPk).build()).get(0));
        // 진짜 삭제
        marketDAO.deleteMarketExplicitly(result);
    }

    /* Message TEST */
    private void messageTest() {
        Message message = Message.builder()
                .sender(8)
                .receiver(6)
                .title("문의 답변입니다")
                .contents("네 가능합니다")
                .sendDate("2020-05-27")
                .isRead(1).build();
        // 삽입
        messageDAO.addMessage(message);
        // 조회
        long messagePk = message.getIdmessage();
        Message result = messageDAO.findMessage(Message.builder().idmessage(messagePk).build()).get(0);
        System.out.println(result);
        // 수정
        result.setTitle("new message!");
        messageDAO.updateMessage(result);
        System.out.println(messageDAO.findMessage(Message.builder().idmessage(messagePk).build()).get(0));
        // 진짜 삭제
        messageDAO.deleteMessageExplicitly(result);
    }
}
