package com.ssafy.service;

import com.ssafy.vo.Member;
import com.ssafy.vo.response.CommonResult;

import java.util.List;

public interface MemberService {
    public List<Member> findMember(Member member);
    public int addMember(Member member);
    public int updateMember(Member member);
    public int deleteMember(Member member);
    public int deleteMemberExplicitly(Member member);
    public int deleteImage(Member member);
}
