package com.gl18.webmarket.webapi.user;

import com.gl18.webmarket.object.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserInfo {

    @GetMapping("/api/user/getinfo")
    public static String getInfo(HttpServletRequest request){

        User user = new User(Integer.valueOf(request.getParameter("sid")));
        return user.getName();
    }
}
