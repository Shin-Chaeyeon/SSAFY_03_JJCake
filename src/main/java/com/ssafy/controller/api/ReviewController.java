package com.ssafy.controller.api;

import com.ssafy.advice.exception.CustomDataBaseException;
import com.ssafy.advice.exception.CustomImageDeleteException;
import com.ssafy.advice.exception.CustomImageSaveException;
import com.ssafy.advice.exception.CustomReviewNotFoundException;
import com.ssafy.service.ResponseSerivce;
import com.ssafy.service.ReviewService;
import com.ssafy.type.IsDeleted;
import com.ssafy.vo.Review;
import com.ssafy.vo.response.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "6. Review")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class ReviewController {

    private final ReviewService reviewService;
    private final ResponseSerivce responseSerivce;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "리뷰 조회", notes = "리뷰 조회 할 경우 \n" +
            "### 항상 5개 씩 가지고 감, idreview 역순으로 정렬되어 가지고감(항상 최신 데이터 부터)\n" +
            "1. 처음 조회 할 때: pageIndex = 0으로 값 설정 \n" +
            "2. 다음 페이지 일 때: pageIndex = nextPageIndex 값을 넣어 보내면 됨 \n" +
            "3. ::예시:: " +
            " -> 제일 처음 pageIndex = 0으로 설정(17 ~ 12 까지 가지고 감)" +
            " -> 그 다음 페이지 pageIndex = 12로 설정(11 ~ 7 까지 가지고 감)")
    @PostMapping(value = "/user/reviews")
    public CommonResult findReview(@RequestBody Review review){
        review.setIsDeleted(IsDeleted.FALSE.NUMBER);
        List<Review> result = reviewService.findReview(review);
        if(result.size() == 0) throw new CustomReviewNotFoundException();
        return responseSerivce.getListResult(result, result.get(result.size()-1).getIdreview());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "리뷰 작성", notes = "리뷰를 작성 할 경우[...]")
    @PostMapping(value = "/user/review")
    public CommonResult addReview(Review review){
        review.setIsDeleted(IsDeleted.FALSE.NUMBER);
        int result = reviewService.addReview(review);
        if(result == -1){
            throw new CustomImageSaveException();
        }else if(result == -2){
            throw new CustomDataBaseException();
        }
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "리뷰 수정", notes = "리뷰를 수정 할 경우[...]")
    @PutMapping(value = "/user/review")
    public CommonResult updateReview(@RequestBody Review review){
        int result = reviewService.updateReview(review);
        if(result == -1){
            throw new CustomImageSaveException();
        }else if(result == -2){
            throw new RuntimeException();
        }else if(result == -3){
            throw new CustomDataBaseException();
        }
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "리뷰 삭제", notes = "리뷰를 삭제 할 경우[...]")
    @DeleteMapping(value = "/user/review")
    public CommonResult deleteReview(@RequestBody Review review){
        reviewService.deleteReview(review);
        return responseSerivce.getSuccessResult();
    }
}
