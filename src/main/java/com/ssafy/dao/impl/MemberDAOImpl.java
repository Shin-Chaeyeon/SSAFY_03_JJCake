package com.ssafy.dao.impl;

import com.ssafy.dao.MemberDAO;
import com.ssafy.type.Admin;
import com.ssafy.vo.*;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MemberDAOImpl implements MemberDAO {

    private String ns1 = "MemberMapper.";
    private String ns2 = "RoleMapper.";
    private String ns3 = "ReviewMapper.";
    private String ns4 = "PortfolioMapper.";
    private String ns5 = "OrderMapper.";
    private String ns6 = "MarketMapper.";
    private String ns7 = "EstimateMapper.";

    @Autowired
    private SqlSession sqlSession;

    @Override
    @Transactional
    public List<Member> findMember(Member member) {
        return sqlSession.selectList(ns1 + "findMember", member);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public int addMember(Member member) {
        int result = 0;
        result += sqlSession.insert(ns1 + "addMember", member);
        List<String> roles = member.getRoles();
        for(String role : roles) {
            Role saveRole = new Role();
            saveRole.setMemberId(member.getIdmember());
            saveRole.setRoleName(role);
            result += sqlSession.insert(ns2 + "addRole", saveRole);
        }
        return result;
    }

    @Override
    public int updateMember(Member member) {
        return sqlSession.update(ns1 + "updateMember", member);
    }

    @Override
    @Transactional
    public int deleteMember(Member member) {
        int result = 0;
        Member searched = findMember(member).get(0);
        long idmember = searched.getIdmember();
        result += sqlSession.update(ns1 + "deleteMember", Member.builder().idmember(idmember).build());
        result += sqlSession.update(ns3 + "deleteReview", Review.builder().buyerId(idmember).build());
        result += sqlSession.update(ns3 + "deleteReview", Review.builder().sellerId(idmember).build());
        result += sqlSession.update(ns4 + "deletePortfolio", Portfolio.builder().sellerId(idmember).build());
        result += sqlSession.update(ns5 + "deleteOrder", Order.builder().buyerId(idmember).build());
        result += sqlSession.update(ns5 + "deleteOrder", Order.builder().sellerId(idmember).build());
        result += sqlSession.update(ns6 + "deleteMarket", Market.builder().sellerId(idmember).build());
        result += sqlSession.update(ns7 + "deleteEstimate", Estimate.builder().sellerId(idmember).build());
        return result;
    }

    @Override
    @Transactional
    public int deleteMemberExplicitly(Member member) {
        return sqlSession.delete(ns1 + "deleteMemberExplicitly", member);
    }
}
