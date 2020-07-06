package com.ssafy.controller.exception;

import com.ssafy.advice.exception.CustomAuthenticationEntryPointException;
import com.ssafy.vo.response.CommonResult;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"***Exception***"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {
    @GetMapping(value = "/entrypoint")
    public CommonResult entrypointException() {
        throw new CustomAuthenticationEntryPointException();
    }

    @GetMapping(value = "/accessdenied")
    public CommonResult accessdeniedException() {
        throw new AccessDeniedException("");
    }
}
