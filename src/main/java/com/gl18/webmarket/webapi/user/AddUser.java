package com.gl18.webmarket.webapi.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddUser {

    @GetMapping("/api/user/add")
    public static String addUser(HttpServletRequest request){
        return null;
    }
}
