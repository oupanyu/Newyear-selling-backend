package com.gl18.webmarket.object;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.gl18.webmarket.WebmarketApplication;

import java.sql.*;

public class User {

    private String name,passwd,things,token;

    private Integer id,grade,classnum,sid,price;

    private Short status;

    public Integer getClassnum() {
        return classnum;
    }

    public Integer getGrade() {
        return grade;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSid() {
        return sid;
    }

    public String getPasswd() {
        return passwd;
    }

    public JSONArray getThings() {
        return JSONArray.parse(things);
    }

    public String getToken() {
        return token;
    }

    public Integer getPrice() {
        return price;
    }

    public User(Integer sid){
        try {
            String url = String.format("jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=UTF-8&userSSL=false"
                    ,WebmarketApplication.config.dbhost
                    ,WebmarketApplication.config.dbname);
            Connection conn = DriverManager.getConnection(url, WebmarketApplication.config.dbusername, WebmarketApplication.config.dbpasswd);
            Statement statement = conn.createStatement();
            String sql = String.format("select * from buyer where sid = %s", sid);
            ResultSet result = statement.executeQuery(sql);
            int i = 0;
            while (result.next()){
                id = result.getInt("id");
                this.sid = result.getInt("sid");
                name = result.getString("name");
                classnum = result.getInt("class");
                things = result.getString("things");
                price = result.getInt("price");
                passwd = result.getString("passwd");
                token = result.getString("token");
                grade = result.getInt("grade");
                status = result.getShort("status");
                i++;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBasicInfo(){
        JSONObject rootObj = new JSONObject();
        JSONObject obj = new JSONObject();

        obj.put("name",name);
        obj.put("id",id);
        obj.put("sid",sid);
        obj.put("class",classnum);
        obj.put("things",JSONArray.parse(things));
        obj.put("status",status);


        rootObj.put("code",200);
        rootObj.put("data",obj);

        //TODO: add basic info into JSON
        return rootObj.toJSONString();

    }

}
