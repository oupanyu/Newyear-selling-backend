package com.gl18.webmarket.webapi.cart;

import com.alibaba.fastjson2.JSONObject;
import com.gl18.webmarket.object.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DelCart {
    @GetMapping("/api/cart/delete")
    public static String delete(HttpServletRequest request){
        String token = request.getParameter("token");
        Integer sid = Integer.valueOf(request.getParameter("sid"));
        Integer gid = Integer.valueOf(request.getParameter("gid"));
        JSONObject rootObj = new JSONObject();

        User user = new User(sid);
        if(user.getToken().equals(token)){
            rootObj.put("code",200);
            user.deleteGoods(gid);
            rootObj.put("result","success");
        }else {
            rootObj.put("code",-1);
            rootObj.put("result","wrong token");
        }
        return rootObj.toJSONString();
    }
}
