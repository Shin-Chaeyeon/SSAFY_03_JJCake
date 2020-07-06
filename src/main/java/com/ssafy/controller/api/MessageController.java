package com.ssafy.controller.api;

import com.ssafy.advice.exception.CustomMessageNotFoundException;
import com.ssafy.service.MessageService;
import com.ssafy.service.ResponseSerivce;
import com.ssafy.type.IsRead;
import com.ssafy.vo.Message;
import com.ssafy.vo.response.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "3. Message")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class MessageController {

    private final MessageService messageService;
    private final ResponseSerivce responseSerivce;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "쪽지 조회", notes = "쪽지 조회 할 경우 \n" +
            "### 항상 5개 씩 가지고 감, pk 역순으로 정렬되어 가지고감(항상 최신 데이터 부터)\n" +
            "1. 처음 조회 할 때: pageIndex = 0으로 값 설정 \n" +
            "2. 다음 페이지 일 때: pageIndex = nextPageIndex 값을 넣어 보내면 됨 \n" +
            "3. ::예시:: " +
            " -> 제일 처음 pageIndex = 0으로 설정(17 ~ 12 까지 가지고 감)" +
            " -> 그 다음 페이지 pageIndex = 12로 설정(11 ~ 7 까지 가지고 감)")
    @PostMapping(value = "/user/messages")
    public CommonResult findMessage(@RequestBody Message message){
        List<Message> result = messageService.findMessage(message);
        if(result.size() == 0) throw new CustomMessageNotFoundException();
        return responseSerivce.getListResult(result, result.get(result.size()-1).getIdmessage());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "메지지 작성", notes = "메시지를 작성 할 경우[...]")
    @PostMapping(value = "/user/message")
    public CommonResult addMessage(@RequestBody Message message){
        message.setIsRead(IsRead.FALSE.NUMBER);
        messageService.addMessage(message);
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "메지지 수정", notes = "메시지를 수정 할 경우[...]")
    @PutMapping(value = "/user/message")
    public CommonResult updateMessage(@RequestBody Message message){
        messageService.updateMessage(message);
        return responseSerivce.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "메지지 삭제", notes = "메시지를 삭제 할 경우[...]")
    @DeleteMapping(value = "/user/message")
    public CommonResult deleteMessageExplicitly(@RequestBody Message message){
        messageService.deleteMessageExplicitly(message);
        return responseSerivce.getSuccessResult();
    }
}
