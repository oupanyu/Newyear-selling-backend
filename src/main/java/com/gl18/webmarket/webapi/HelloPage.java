package com.gl18.webmarket.webapi;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloPage {
    @GetMapping("/")
    public static String helloPage(){
        return "当你看到这个界面时，服务已经成功运行了！";
    }
}
