package com.gl18.webmarket.webapi.goods;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.gl18.webmarket.utils.DBUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
public class GetAllGoodsInfo {
    @GetMapping("/api/goods/getgoodinfo/all")
    public static String getAll(){
        try {
            Connection conn = DBUtil.getConnection();
            Statement statement = conn.createStatement();
            String sql = "select * from goods";
            ResultSet result = statement.executeQuery(sql);

            JSONObject obj = new JSONObject();
            obj.put("code",200);
            JSONArray jsonArray = new JSONArray();
            int i = 0;
            while (result.next()) {
                JSONObject object = new JSONObject();
                object.put("id",result.getInt("id"));
                object.put("name",result.getString("name"));
                object.put("group_name",result.getString("group_name"));
                object.put("status",result.getShort("status"));
                object.put("description",result.getString("description"));
                object.put("bought",result.getInt("bought"));
                object.put("maxpic",result.getInt("maxpic"));
                object.put("maximum",result.getInt("maximum"));
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
