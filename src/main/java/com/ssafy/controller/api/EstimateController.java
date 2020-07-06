package com.ssafy.controller.api;

import com.ssafy.advice.exception.CustomEstimateNotFoundException;
import com.ssafy.service.ResponseSerivce;
import com.ssafy.service.EstimateService;
import com.ssafy.type.IsDeleted;
import com.ssafy.type.IsSelected;
import com.ssafy.vo.Estimate;
import com.ssafy.vo.response.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "1. Estimate")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class EstimateController {

    private final EstimateService estimateService;
    private final ResponseSerivce responseSerivce;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "견적서 조회", notes = "견적서 조회 할 경우 \n" +
            "### 항상 5개 씩 가지고 감, pk 역순으로 정렬되어 가지고감(항상 최신 데이터 부터)\n" +
            "1. 처음 조회 할 때: pageIndex = 0으로 값 설정 \n" +
            "2. 다음 페이지 일 때: pageIndex = nextPageIndex 값을 넣어 보내면 됨 \n" +
            "3. ::예시:: " +
            " -> 제일 처음 pageIndex = 0으로 설정(17 ~ 12 까지 가지고 감)" +
            " -> 그 다음 페이지 pageIndex = 12로 설정(11 ~ 7 까지 가지고 감)")
    @PostMapping(value = "/user/estimates")
    public CommonResult findEstimate(@RequestBody Estimate estimate){
        estimate.setIsDeleted(IsDeleted.FALSE.NUMBER);
        List<Estimate> result = estimateService.findEstimate(estimate);
        if(result.size() == 0) throw new CustomEstimateNotFoundException();
        return responseSerivce.getListResult(result, result.get(result.size()-1).getIdestimate());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "견적서 작성", notes = "견적서를 작성 할 경우[...]")
    @PostMapping(value = "/user/estimate")
    public CommonResult addEstimate(@RequestBody Estimate estimate){
        estimate.setIsDeleted(IsDeleted.FALSE.NUMBER);
        estimate.setIsDeleted(IsSelected.FALSE.NUMBER);
        estimateService.addEstimate(estimate);
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "견적서 수정", notes = "견적서를 수정 할 경우[...]")
    @PutMapping(value = "/user/estimate")
    public CommonResult updateEstimate(@RequestBody Estimate estimate){
        estimateService.updateEstimate(estimate);
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "견적서 삭제", notes = "견적서를 삭제 할 경우[...]")
    @DeleteMapping(value = "/user/estimate")
    public CommonResult deleteEstimate(@RequestBody Estimate estimate){
        estimateService.deleteEstimate(estimate);
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "견적서 선택", notes = "견적서를 선택 할 경우[...]")
    @PutMapping(value = "/user/estimate/selection")
    public CommonResult selectionEstimate(@RequestBody Estimate estimate){
        estimateService.selectionEstimate(estimate);
        return responseSerivce.getSuccessResult();
    }
}
