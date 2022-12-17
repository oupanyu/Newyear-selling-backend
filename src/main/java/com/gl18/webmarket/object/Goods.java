package com.gl18.webmarket.object;

import com.alibaba.fastjson2.JSONObject;
import com.gl18.webmarket.WebmarketApplication;

import java.sql.*;

public class Goods {

    private String name,description;

    private Integer id,left,maximum,bought,price;

    private Short status,maxpic;

    public Integer getBought() {
        return bought;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLeft() {
        return left;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public Short getMaxpic() {
        return maxpic;
    }

    public String getDescription() {
        return description;
    }

    public Short getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public Goods(Integer gid){

        try {
            String url = String.format("jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=UTF-8&userSSL=false"
                    , WebmarketApplication.config.dbhost
                    ,WebmarketApplication.config.dbname);
            Connection conn = DriverManager.getConnection(url, WebmarketApplication.config.dbusername, WebmarketApplication.config.dbpasswd);
            Statement statement = conn.createStatement();
            String sql = String.format("select * from goods where id = %s", gid);
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                id = result.getInt("id");
                name = result.getString("name");
                left = result.getInt("left");
                status = result.getShort("status");
                description = result.getString("description");
                maxpic = result.getShort("maxpic");
                maximum = result.getInt("maximum");
                bought = result.getInt("bought");
                price = result.getInt("price");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getWithJSONString(){
        JSONObject rootObj = new JSONObject(),object = new JSONObject();
        rootObj.put("code",200);
        object.put("id",id);
        object.put("name",name);
        object.put("left",left);
        object.put("status",status);
        object.put("description",description);
        object.put("maxpic",maxpic);
        object.put("maximum",maximum);
        object.put("bought",bought);
        object.put("price",price);

        rootObj.put("data",object);

        return rootObj.toJSONString();
    }
}
