package com.ssafy.service.impl;

import com.ssafy.advice.exception.CustomImageSaveException;
import com.ssafy.config.redis.CacheKey;
import com.ssafy.dao.MemberDAO;
import com.ssafy.service.FileService;
import com.ssafy.service.MemberService;
import com.ssafy.type.Admin;
import com.ssafy.type.IsDeleted;
import com.ssafy.type.Roles;
import com.ssafy.vo.Member;
import com.ssafy.service.ResponseSerivce;
import com.ssafy.vo.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements UserDetailsService, MemberService, FileService<Member> {
    @Value("${spring.url.base-path}")
    private String basePath;
    @Value("${spring.url.base}")
    private String base;
    @Value("${spring.url.user-profile}")
    private String resourcesPath;

    private final MemberDAO memberDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Member member = new Member();
            member.setEmail(username);
            return memberDAO.findMember(member).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Cacheable(value = CacheKey.USER, key ="#member", unless = "#result == null")
    @Override
    public List<Member> findMember(Member member) {
        return memberDAO.findMember(member);
    }

    @CacheEvict(cacheNames = {CacheKey.USER}, allEntries = true)
    @Override
    public int addMember(Member member) {
        int addResult = -1;
        if(memberDAO.findMember(Member.builder().email(member.getEmail()).build()).size() != 0){
            return addResult = -2;
        }
        if(memberDAO.findMember(Member.builder().nickname(member.getNickname()).build()).size() != 0){
            return addResult = -3;
        }
        try {
            fileSave(member);
        }catch (IOException e){
            return addResult;
        }
        try{
            addResult = memberDAO.addMember(member);
        }catch (Exception e){
            fileDelete(member);
            addResult = -2;
            return addResult;
        }
        return addResult;
    }

    @CacheEvict(cacheNames = {CacheKey.USER}, allEntries = true)
    @Override
    public int updateMember(Member member) {
        int updateResult = -1;
        Member preMember = memberDAO.findMember(Member.builder().idmember(member.getIdmember()).build()).get(0);
        if(!member.getEmail().equals(preMember.getEmail())){
            if(memberDAO.findMember(Member.builder().email(member.getEmail()).build()).size() != 0){
                return updateResult = -2;
            }
        }
        if(!member.getNickname().equals(preMember.getNickname())){
            if(memberDAO.findMember(Member.builder().nickname(member.getNickname()).build()).size() != 0){
                return updateResult = -3;
            }
        }
        try {
            fileDelete(preMember);
            fileSave(member);
        }catch (IOException e){
            return updateResult;
        }
        try{
            updateResult = memberDAO.updateMember(member);
        }catch (Exception e){
            fileDelete(member);
            updateResult = -4;
            return updateResult;
        }
        return updateResult;
    }

    @CacheEvict(cacheNames = {CacheKey.USER}, allEntries = true)
    @Override
    public int deleteMember(Member member) {
        return memberDAO.deleteMember(member);
    }

    @CacheEvict(cacheNames = {CacheKey.USER}, allEntries = true)
    @Override
    public int deleteImage(Member member) {
        int result = -1;
        try {
            fileDelete(member);
        }catch (Exception e){
            return result;
        }
        try {
            result = memberDAO.updateMember(Member.builder().idmember(member.getIdmember()).build());
        }catch (Exception e){
            result = -2;
            return result;
        }
        return result;
    }

    @Override
    public int deleteMemberExplicitly(Member member) {
        int result = -1;
        try {
            fileDelete(member);
        }catch (Exception e){
            return result;
        }
        try {
            result = memberDAO.deleteMemberExplicitly(Member.builder().idmember(member.getIdmember()).build());
        }catch (Exception e){
            result = -2;
            return result;
        }
        return result;
    }

    @Override
    public int fileDelete(Member vo) {
        if(vo.getImageUrl() != null){
            String[] splitPath = vo.getImageUrl().split(base + "/");
            String path = String.format("%s%s", basePath, splitPath[1]);
            File file = new File(path);
            if(file.exists()){
                if(file.delete()){
                    vo.setImageUrl("");
                    return 1; // 삭제 성공
                }else{
                    return 0; // 삭제 실패
                }
            }else{
                return -1; // 파일이 존재하지 않음
            }
        }
        return -1;
    }

    @Override
    public void fileSave(Member vo) throws IOException{
        MultipartFile multipartFile = vo.getImage();
        if(multipartFile != null){
            String originFileName = multipartFile.getOriginalFilename();
            String saveFileName = String.format("%s_%s", vo.getEmail(), originFileName);
            String savePath = String.format("%s/%s%s", base, resourcesPath, saveFileName);
            multipartFile.transferTo(new File(String.format("%s%s", basePath, resourcesPath), saveFileName));
            vo.setImageUrl(savePath);
        }
    }

}
