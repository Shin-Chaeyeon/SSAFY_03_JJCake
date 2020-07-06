package com.ssafy.controller.api;

import com.ssafy.advice.exception.CustomMarketNotFoundException;
import com.ssafy.service.MarketService;
import com.ssafy.service.ResponseSerivce;
import com.ssafy.type.IsDeleted;
import com.ssafy.vo.Market;
import com.ssafy.vo.response.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "2. Market")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class MarketController {

    private final MarketService marketService;
    private final ResponseSerivce responseSerivce;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "가맹점 정보 조회", notes = "가맹점 정보를 조회 할 경우[...]")
    @PostMapping(value = "/user/markets")
    public CommonResult findMarket(@RequestBody Market market){
        market.setIsDeleted(IsDeleted.FALSE.NUMBER);
        List<Market> result = marketService.findMarket(market);
        if(result.size() == 0) throw new CustomMarketNotFoundException();
        return responseSerivce.getListResult(result,0);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "가맹점 정보 작성", notes = "가맹점 정보를 작성 할 경우[...]")
    @PostMapping(value = "/user/market")
    public CommonResult addMarket(@RequestBody Market market){
        marketService.addMarket(market);
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "가맹점 정보 수정", notes = "가맹점 정보를 수정 할 경우[...]")
    @PutMapping(value = "/user/market")
    public CommonResult updateMarket(@RequestBody Market market){
        marketService.updateMarket(market);
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "가맹점 정보 삭제", notes = "가맹점 정보를 삭제 할 경우[...]")
    @DeleteMapping(value = "/user/market")
    public CommonResult deleteMarket(@RequestBody Market market){
        marketService.deleteMarket(market);
        return responseSerivce.getSuccessResult();
    }
}
