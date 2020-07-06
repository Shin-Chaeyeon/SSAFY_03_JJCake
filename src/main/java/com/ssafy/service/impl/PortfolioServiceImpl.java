package com.ssafy.service.impl;

import com.ssafy.config.redis.CacheKey;
import com.ssafy.dao.PortfolioDAO;
import com.ssafy.service.FileService;
import com.ssafy.service.PortfolioService;
import com.ssafy.vo.Portfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PortfolioServiceImpl implements PortfolioService, FileService<Portfolio> {

    @Value("${spring.url.base-path}")
    private String basePath;
    @Value("${spring.url.base}")
    private String base;
    @Value("${spring.url.user-portfilo}")
    private String resourcesPath;

    private final PortfolioDAO portfolioDAO;

    @Cacheable(value = CacheKey.PORTFOLIOS, key ="#portfolio", unless = "#result == null")
    @Override
    public List<Portfolio> findPortfolio(Portfolio portfolio) {
        return portfolioDAO.findPortfolio(portfolio);
    }

    @CacheEvict(cacheNames = {CacheKey.PORTFOLIOS}, allEntries = true)
    @Override
    public int addPortfolio(Portfolio portfolio) {
        int addResult = -1;
        try {
            fileSave(portfolio);
        }catch (IOException e){
            return addResult;
        }
        try{
            addResult = portfolioDAO.addPortfolio(portfolio);
        }catch (Exception e){
            fileDelete(portfolio);
            addResult = -2;
            return addResult;
        }
        return addResult;
    }

    @CacheEvict(cacheNames = {CacheKey.PORTFOLIOS}, allEntries = true)
    @Override
    public int updatePortfolio(Portfolio portfolio) {
        int updateResult = -1;
        try {
            List<Portfolio> portfolios = portfolioDAO.findPortfolio(Portfolio.builder().idportfolio(portfolio.getIdportfolio()).build());
            fileDelete(portfolios.get(0));
            fileSave(portfolio);
        }catch (IOException e){
            return updateResult;
        }catch (Exception e){
            updateResult = -2;
            return updateResult;
        }
        try{
            updateResult = portfolioDAO.updatePortfolio(portfolio);
        }catch (Exception e){
            fileDelete(portfolio);
            updateResult = -3;
            return updateResult;
        }
        return updateResult;
    }

    @CacheEvict(cacheNames = {CacheKey.PORTFOLIOS}, allEntries = true)
    @Override
    public int deletePortfolio(Portfolio portfolio) {
        return portfolioDAO.deletePortfolio(Portfolio.builder().idportfolio(portfolio.getIdportfolio()).build());
    }

    @Override
    public int deletePortfolioExplicitly(Portfolio portfolio) {
        int result = -1;
        try {
            fileDelete(portfolio);
        }catch (Exception e){
            return result;
        }
        try {
            result = portfolioDAO.deletePortfolioExplicitly(Portfolio.builder().idportfolio(portfolio.getIdportfolio()).build());
        }catch (Exception e){
            result = -2;
            return result;
        }
        return result;
    }

    @Override
    public int fileDelete(Portfolio vo) {
        if(vo.getImageUrl() == null) return -1;
        String[] imageUrl = vo.getImageUrl().split(",");
        int result = 0;
        int count = 0;
        int size = imageUrl.length;
        for(String url : imageUrl){
            String[] splitPath = url.split(base + "/");
            String path = String.format("%s%s", basePath, splitPath[1]);
            File file = new File(path);
            if(file.exists()){
                if(file.delete()){
                    StringBuffer stringBuffer = new StringBuffer();
                    for(int i = 1 + count; i < size; i++){
                        stringBuffer.append(imageUrl[i]);
                        if(size - 1 != i)stringBuffer.append(",");
                    }
                    vo.setImageUrl(stringBuffer.toString());
                    result = 1; // 삭제 성공
                }else{
                    result = 0; // 삭제 실패
                    break;
                }
            }else{
                result = -1; // 파일이 존재하지 않음
                break;
            }
            count++;
        }
        return result;
    }

    @Override
    public void fileSave(Portfolio vo) throws IOException {
        MultipartFile[] multipartFiles = vo.getImage();
        StringBuffer stringBuffer = new StringBuffer();
        int count = 0;
        int size = multipartFiles.length;
        for(MultipartFile multipartFile : multipartFiles){
            String originFileName = multipartFile.getOriginalFilename();
            if(multipartFile != null){
                String saveFileName = String.format("%s_%s", vo.getSellerId(), originFileName);
                String savePath = String.format("%s/%s%s", base, resourcesPath, saveFileName);
                multipartFile.transferTo(new File(String.format("%s%s", basePath, resourcesPath), saveFileName));
                stringBuffer.append(savePath);
                if(size - 1 != count)stringBuffer.append(",");
            }
            count++;
        }
        vo.setImageUrl(stringBuffer.toString());
    }
}