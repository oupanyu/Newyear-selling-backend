package com.gl18.webmarket.webapi.goods;

import com.gl18.webmarket.SendError;
import com.gl18.webmarket.object.Goods;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class getGoodsInfo {

    @GetMapping("/api/goods/getinfo")
    public String getInfo(HttpServletRequest request){
        try {
            Goods goods = new Goods(Integer.valueOf(request.getParameter("gid")));
            return goods.getWithJSONString();
        }catch (Exception e){
            return SendError.toFront();
        }
    }
}
