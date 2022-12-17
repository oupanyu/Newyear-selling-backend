package com.gl18.webmarket.webapi.goods;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.gl18.webmarket.SendError;
import com.gl18.webmarket.WebmarketApplication;
import com.gl18.webmarket.object.Goods;
import com.gl18.webmarket.utils.DBUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class GetGoodsInfoByList {

    @GetMapping("/api/goods/getinfo/bylist")
    public String getInfo(HttpServletRequest request) {
        try {
            Integer page = Integer.valueOf(request.getParameter("page"));
            Connection conn = DBUtil.getConnection();
            Statement statement = conn.createStatement();
            String sql = String.format("select * from goods where %s < id < %s", (page - 1) * 10 , page * 10);
            ResultSet result = statement.executeQuery(sql);

            JSONObject obj = new JSONObject();
            obj.put("code",200);
            JSONArray jsonArray = new JSONArray();
            int i = 0;
            while (result.next()) {
                JSONObject object = new JSONObject();
                object.put("id",result.getInt("id"));
                object.put("name",result.getString("name"));
                object.put("status",result.getShort("status"));
                object.put("description",result.getString("description"));
                object.put("bought",result.getInt("bought"));
                object.put("price",result.getInt("price"));
                jsonArray.add(object);
                i++;
            }
            obj.put("data",jsonArray);
            return obj.toJSONString();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}