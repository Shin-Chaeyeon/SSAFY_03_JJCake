package com.ssafy.controller.user;

import com.ssafy.advice.exception.*;
import com.ssafy.config.jwt.JwtTokenProvider;
import com.ssafy.dao.MemberDAO;
import com.ssafy.service.MemberService;
import com.ssafy.service.ResponseSerivce;
import com.ssafy.type.Admin;
import com.ssafy.type.IsDeleted;
import com.ssafy.type.Roles;
import com.ssafy.vo.Member;
import com.ssafy.vo.response.CommonResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Api(tags = {"***User***"})
@RequiredArgsConstructor
@RestController
public class UserController {

    @Value("${spring.redis.prefix.id}")
    private String redisPrefix;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final RedisTemplate redisTemplate;
    private final ResponseSerivce responseSerivce;

    @ApiOperation(value = "회원가입", notes = "우리 홈페이지에서 가입 할 경우[조건 email형식 중복 안됨]")
    @PostMapping("/sign_up")
    public CommonResult join(Member member){
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        if(member.getAdmin() == Admin.BUYER.NUMBER){
            member.getRoles().add(Roles.ROLE_BUYER.NAME);
            member.getRoles().add(Roles.ROLE_USER.NAME);
        }else if(member.getAdmin() == Admin.SELLER.NUMBER){
            member.getRoles().add(Roles.ROLE_SELLER.NAME);
            member.getRoles().add(Roles.ROLE_USER.NAME);
        }
        member.setIsDeleted(IsDeleted.FALSE.NUMBER);
        int result = memberService.addMember(member);
        if(result == -1){
            throw new CustomImageSaveException();
        }else if(result == -2){
            throw new CustomDuplicateEmailException();
        }else if(result == -3){
            throw new CustomDuplicateNicknameException();
        }
        return responseSerivce.getSuccessResult();
    }

    @ApiOperation(value = "로그인", notes = "로그인을 할 경우[...]")
    @PostMapping("/sign_in")
    public CommonResult login(@RequestBody Member member){
        Member search = Member.builder().email(member.getEmail()).build();
        List<Member> result = memberService.findMember(search);
        if(result.size() == 0) throw new CustomEmailSigninFailedException();
        else if (!passwordEncoder.matches(member.getPassword(), result.get(0).getPassword())) {
            throw new CustomEmailSigninFailedException();
        }else if(result.get(0).getIsDeleted() == 2){
            throw new CustomEmailSigninFailedException();
        }
        return responseSerivce.getSingleResult(jwtTokenProvider.createToken(result.get(0).getIdmember(), result.get(0).getUsername(), result.get(0).getRoles()));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "로그아웃", notes = "로그아웃을 할 경우[...]")
    @GetMapping("/sign_out")
    public CommonResult logout(HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        if(jwtTokenProvider.validateToken(token)){
            Date expirationDate = jwtTokenProvider.getExpirationDate(token);
            redisTemplate.opsForValue().set(redisPrefix + token,
                    "1",
                    expirationDate.getTime() - System.currentTimeMillis(),
                    TimeUnit.MILLISECONDS);
        }
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원탈퇴", notes = "회원탈퇴를 할 경우[...]")
    @DeleteMapping("/user/member")
    public CommonResult deleteMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        memberService.deleteMember(Member.builder().email(email).build());
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원정보수정", notes = "회원정보를 수정 할 경우 \n" +
            "1. 회원정보 수정을 할 때는 수정하려는 회원 정보를 한번 불러 온 다음 수정 할 것" +
            "2. 변경된 정보만 반영")
    @PutMapping("/user/member")
    public CommonResult updateMember(Member member){
        List<Member> tempMember = memberService.findMember(Member.builder().idmember(member.getIdmember()).build());
        if(!tempMember.get(0).getPassword().equals(member.getPassword())){
            member.setPassword(passwordEncoder.encode(member.getPassword()));
        }
        int result = memberService.updateMember(member);
        if(result == -1){
            throw new CustomImageSaveException();
        }else if(result == -2){
            throw new CustomDuplicateEmailException();
        }else if(result == -3){
            throw new CustomDuplicateNicknameException();
        }
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @PutMapping("/user/member/image")
    @ApiOperation(value = "회원프로필사진삭제", notes = "프로필을 삭제 할 경우 \n" +
            "1. 회원정보 수정 과 마찬가지로 회원 정보를 한번 불러 온다음 수정 할 것")
    public CommonResult deleteImage(Member member){
        int result = memberService.deleteImage(member);
        if(result == -1){
            throw new CustomImageDeleteException();
        }
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원정보 가지고 오기", notes = "회원정보를 가지고 올 경우 \n" +
            "1. 자신의 정보를 가져올 때:: /user/member/0  \n" +
            "2. 다른 사람의 정보를 가지고 올때:: /user/member/상대방 pk")
    @GetMapping("/user/member/{idmember}")
    public CommonResult findMember(@PathVariable long idmember){
        if(idmember == 0){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            List<Member> result = memberService.findMember(Member.builder().email(email).isDeleted(IsDeleted.FALSE.NUMBER).build());
            if(result.size() == 0) throw new CustomMemberNotFoundException();
            else return responseSerivce.getListResult(result, 0);
        }else {
            List<Member> result = memberService.findMember(Member.builder().idmember(idmember).isDeleted(IsDeleted.FALSE.NUMBER).build());
            if(result.size() == 0) throw new CustomMemberNotFoundException();
            else return responseSerivce.getListResult(result, 0);
        }
    }
}
