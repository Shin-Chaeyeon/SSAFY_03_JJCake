package com.ssafy.service.impl;

import com.ssafy.config.redis.CacheKey;
import com.ssafy.dao.ReviewDAO;
import com.ssafy.service.FileService;
import com.ssafy.service.ReviewService;
import com.ssafy.vo.Portfolio;
import com.ssafy.vo.Review;
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
public class ReviewServiceImpl implements ReviewService, FileService<Review> {

    @Value("${spring.url.base-path}")
    private String basePath;
    @Value("${spring.url.base}")
    private String base;
    @Value("${spring.url.user-review}")
    private String resourcesPath;

    private final ReviewDAO reviewDAO;

    @Cacheable(value = CacheKey.REVIEWS, key ="#review", unless = "#result == null")
    @Override
    public List<Review> findReview(Review review) {
        return reviewDAO.findReview(review);
    }

    @CacheEvict(cacheNames = {CacheKey.REVIEWS}, allEntries = true)
    @Override
    public int addReview(Review review) {
        int addResult = -1;
        try{
            fileSave(review);
        }catch (IOException e){
            return addResult;
        }
        try{
            addResult = reviewDAO.addReview(review);
        }catch (Exception e){
            fileDelete(review);
            addResult = -2;
            return addResult;
        }
        return addResult;
    }

    @CacheEvict(cacheNames = {CacheKey.REVIEWS}, allEntries = true)
    @Override
    public int updateReview(Review review) {
        int updateResult = -1;
        try {
            List<Review> reviews = reviewDAO.findReview(Review.builder().idreview(review.getIdreview()).build());
            fileDelete(reviews.get(0));
            fileSave(review);
        }catch (IOException e){
            return updateResult;
        }catch (Exception e){
            updateResult = -2;
            return updateResult;
        }
        try{
            updateResult = reviewDAO.updateReview(review);
        }catch (Exception e){
            fileDelete(review);
            updateResult = -3;
            return updateResult;
        }
        return updateResult;
    }

    @CacheEvict(cacheNames = {CacheKey.REVIEWS}, allEntries = true)
    @Override
    public int deleteReview(Review review) {
        return reviewDAO.deleteReview(review);
    }

    @Override
    public int deleteReviewExplicitly(Review review) {
        int result = -1;
        try {
            fileDelete(review);
        }catch (Exception e){
            return result;
        }
        try {
            result = reviewDAO.deleteReviewExplicitly(Review.builder().idreview(review.getIdreview()).build());
        }catch (Exception e){
            result = -2;
            return result;
        }
        return result;
    }

    @Override
    public int fileDelete(Review vo) {
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
    public void fileSave(Review vo) throws IOException {
        MultipartFile[] multipartFiles = vo.getImage();
        StringBuffer stringBuffer = new StringBuffer();
        int count = 0;
        int size = multipartFiles.length;
        for(MultipartFile multipartFile : multipartFiles){
            String originFileName = multipartFile.getOriginalFilename();
            if(multipartFile != null){
                String saveFileName = String.format("%s_%s", vo.getOrderId(), originFileName);
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
