package com.gl18.webmarket.webapi.cart;

import com.alibaba.fastjson2.JSONObject;
import com.gl18.webmarket.object.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCart {
    @GetMapping("/api/cart/get")
    public static String getCart(HttpServletRequest request){
        //String token = request.getParameter("token");
        Integer sid = Integer.valueOf(request.getParameter("sid"));
        JSONObject object = new JSONObject();
        User user = new User(sid);
        object.put("code",200);
        if(user.getThings() == null){
            object.put("data","[]");
        }else {
            object.put("data",user.getThings());
        }
        return object.toJSONString();
    }
}
