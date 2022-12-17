package com.gl18.webmarket.webapi.user;

import com.alibaba.fastjson.JSONObject;
import com.gl18.webmarket.object.User;
import com.gl18.webmarket.object.exception.DuplicationOfNameException;
import com.gl18.webmarket.object.exception.IncompleteInfoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddUser {

    @GetMapping("/api/user/add")
    public static String addUser(HttpServletRequest request){
        String recaptchaCode = request.getParameter("response");
        JSONObject rootObj = new JSONObject();

        User user = new User();
        try {
            user.setClassnum(Integer.valueOf(
                    request.getParameter("class")));

            user.setName(request.getParameter("name"));

            user.setGrade(Integer.valueOf(
                    request.getParameter("grade")));

            user.setSid(Integer.valueOf(
                    request.getParameter("sid")));

            user.setPasswd(request.getParameter("passwd"));

        }catch (Exception e){
            rootObj.put("code",900);
            rootObj.put("result","incomplete information");
            return rootObj.toJSONString();
        }


        try {
            user.createAtDatabase();

            rootObj.put("code",200);
            rootObj.put("result","success");
            return rootObj.toJSONString();

        } catch (IncompleteInfoException | DuplicationOfNameException ignored) {
            rootObj.put("code",901);
            rootObj.put("result","duplication of name");
            return rootObj.toJSONString();

        }

    }
}
