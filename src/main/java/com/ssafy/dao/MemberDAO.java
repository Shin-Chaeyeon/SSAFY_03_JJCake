package com.ssafy.dao;

import com.ssafy.vo.Member;

import java.util.List;

public interface MemberDAO {
    public List<Member> findMember(Member member);
    public int addMember(Member member);
    public int updateMember(Member member);
    public int deleteMember(Member member);
    public int deleteMemberExplicitly(Member member);
}
