package com.gl18.webmarket.webapi;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutPersonWish {

    @GetMapping("api/putwish")
    public static void putWish(HttpServletRequest request){
        JSONObject object = JSONObject.parseObject(request.getParameter("json"));


    }
}
