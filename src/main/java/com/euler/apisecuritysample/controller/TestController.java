package com.euler.apisecuritysample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: xjf
 * Version: 1.0.0
 * Description:
 * CreateDateTime: 2023-12-12
 */

@RequestMapping("/api")
@RestController
public class TestController {

    @GetMapping("/testing")
    public String testing(){
        return "testing";
    }

}
