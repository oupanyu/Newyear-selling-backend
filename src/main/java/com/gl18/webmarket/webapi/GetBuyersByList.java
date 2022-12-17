package com.gl18.webmarket.webapi;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class GetBuyersByList {

    @GetMapping("/api/getbuyers/bylist")
    public static String GetBuyersByList(HttpServletRequest request){
        int pages = Integer.parseInt(request.getParameter("pages"));
        try {
            String url = String.format("jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=UTF-8&userSSL=false","127.0.0.1","gl18");
            Connection conn = DriverManager.getConnection(url, "root", "gl182213");
            Statement statement = conn.createStatement();
            String sql = String.format("select * from buyer where %s < id < %s", (pages-1) * 10 , pages * 10);
            ResultSet result = statement.executeQuery(sql);
            JSONArray obj = new JSONArray();
            int i = 0;
            while (result.next()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", result.getString("id"));
                jsonObject.put("name", result.getString("name"));
                jsonObject.put("class", result.getString("class"));
                jsonObject.put("things", JSONArray.parse(result.getString("things")));
                jsonObject.put("price", result.getInt("price"));
                jsonObject.put("status", result.getInt("status"));
                obj.add(jsonObject);
                i++;
                jsonObject = null;
            }
            return obj.toJSONString();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
