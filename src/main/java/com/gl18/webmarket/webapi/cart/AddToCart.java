package com.gl18.webmarket.webapi.cart;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.gl18.webmarket.object.User;
import com.gl18.webmarket.object.exception.OverCountException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddToCart {
    @GetMapping("/api/cart/add")
    public static String add(HttpServletRequest request){
        String token = request.getParameter("token");
        Integer sid = Integer.valueOf(request.getParameter("sid"));
        Integer gid = Integer.valueOf(request.getParameter("gid"));
        Integer count = Integer.valueOf(request.getParameter("count"));

        JSONObject object = new JSONObject();
        User user = new User(sid);
        if (!user.getToken().equals(token)){
            object.put("code",-1);
            object.put("result","password incorrect");
        }else {
            try {
                if(user.addToCart(gid,count)){
                    object.put("code",200);
                    object.put("result","success");
                }else {
                    object.put("code",0);
                    object.put("result","error");
                }
            } catch (OverCountException e) {
                object.put("code",-2);
                object.put("result","count over maxicount");
            }
        }

        return object.toJSONString();
    }
}
