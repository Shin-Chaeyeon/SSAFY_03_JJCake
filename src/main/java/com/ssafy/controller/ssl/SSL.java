package com.ssafy.controller.ssl;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"***SSL***"})
@RequiredArgsConstructor
@RestController
public class SSL {

    @GetMapping("/.well-known/acme-challenge/{value}")
    public String SSLCheck(@PathVariable String value){
        return value;
    }
}
