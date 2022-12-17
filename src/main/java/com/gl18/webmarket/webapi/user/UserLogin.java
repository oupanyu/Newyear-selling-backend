package com.gl18.webmarket.webapi.user;


import com.alibaba.fastjson.JSONObject;
import com.gl18.webmarket.object.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLogin {
    @GetMapping("/api/user/login")
    public static String login(HttpServletRequest request){

        User user = new User(Integer.valueOf(request.getParameter("sid")));
        String passwd = request.getParameter("passwd");
        JSONObject object = new JSONObject();
        if (user.getPasswd().equals(passwd)){
            object.put("data",JSONObject.parseObject(user.getBasicInfo()).get("data"));
            object.put("token",user.getToken());
            object.put("code",200);

            return object.toJSONString();
        }else {
            object.put("code",801);
            object.put("result","wrong password");
            return object.toJSONString();
        }

    }
}
