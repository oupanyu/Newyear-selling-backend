package com.gl18.webmarket.webapi.goods;

import com.alibaba.fastjson2.JSONObject;
import com.gl18.webmarket.SendError;
import com.gl18.webmarket.WebmarketApplication;
import com.gl18.webmarket.object.Goods;
import com.gl18.webmarket.object.exception.DuplicationOfNameException;
import com.gl18.webmarket.object.exception.IncompleteInfoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddGoods {

    @GetMapping("/api/admin/goods/add")
    public String getInfo(HttpServletRequest request){

        Goods goods = new Goods();
        JSONObject rootObj = new JSONObject();

        if (!request.getParameter("token").equals(WebmarketApplication.config.admin_token)){
            rootObj.put("code",-1);
            rootObj.put("result","No permission.");
            return rootObj.toJSONString();
        }


        try {
            goods.setName(request.getParameter("name"));

            try {
                goods.setDescription(request.getParameter("description"));
            }catch (Exception ignored){}

            goods.setPrice(Double.valueOf(
                    request.getParameter("price")));

            goods.setMaximum(Integer.valueOf(
                    request.getParameter("maximum")));

            goods.setStatus(Short.valueOf(request.getParameter("status")));

        }catch (Exception e){
            rootObj.put("code",700);
            rootObj.put("result","incomplete information");
            return rootObj.toJSONString();
        }


        try {
            goods.createAtDatabase();

            rootObj.put("code",200);
            rootObj.put("result","success");
            return rootObj.toJSONString();

        } catch (IncompleteInfoException e) {
            rootObj.put("code",700);
            rootObj.put("result","incomplete information");
            return rootObj.toJSONString();

        }
    }
}

