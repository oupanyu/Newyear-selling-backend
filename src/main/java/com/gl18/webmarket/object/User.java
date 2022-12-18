package com.gl18.webmarket.object;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.gl18.webmarket.WebmarketApplication;
import com.gl18.webmarket.database.CheckName;
import com.gl18.webmarket.object.exception.DuplicationOfNameException;
import com.gl18.webmarket.object.exception.IncompleteInfoException;
import com.gl18.webmarket.object.exception.OverCountException;
import com.gl18.webmarket.utils.DBUtil;

import java.sql.*;

public class User {

    private String name,passwd,things,token;

    private Integer id,grade,classnum,sid;
    private Double price;

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

    public Double getPrice() {
        return price;
    }

    public Short getStatus() {
        return status;
    }

    public void setClassnum(Integer classnum) {
        this.classnum = classnum;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public void setThings(String things) {
        this.things = things;
    }

    public void setToken(String token) {
        this.token = token;
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
                price = result.getDouble("price");
                passwd = result.getString("passwd");
                token = result.getString("token");
                grade = result.getInt("grade");
                status = result.getShort("status");
                i++;
            }
            statement.cancel();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User(){}

    public String getBasicInfo(){
        JSONObject rootObj = new JSONObject();
        JSONObject obj = new JSONObject();

        obj.put("name",name);
        obj.put("id",id);
        obj.put("sid",sid);
        obj.put("grade",grade);
        obj.put("class",classnum);
        obj.put("things",JSONArray.parse(things));
        obj.put("status",status);
        obj.put("price",price);

        //生成JSON
        rootObj.put("code",200);
        rootObj.put("data",obj);

        return rootObj.toJSONString();

    }

    public void createAtDatabase() throws IncompleteInfoException, DuplicationOfNameException {
        String send = "INSERT INTO `buyer` (`sid`, `passwd`, `name`, `grade`, `class`) VALUES " +
                "(?, ?, ?, ?, ?)";

        if (sid == null && passwd ==null && name == null && grade == null && classnum == null){
            throw new IncompleteInfoException();
        }//如果信息不完整，抛出异常

        if ((CheckName.ifDuplicationOfName(name) && CheckName.ifDuplicationOfStudentID(sid)) || CheckName.ifDuplicationOfStudentID(sid)){
            throw new DuplicationOfNameException();
        }

        Connection connection = DBUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(send);
            preparedStatement.setInt(1,sid);
            preparedStatement.setString(2,passwd);
            preparedStatement.setString(3,name);
            preparedStatement.setInt(4,grade);
            preparedStatement.setInt(5,classnum);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addToCart(Integer goodID,Integer count) throws OverCountException {
        JSONArray jsonArray = JSONArray.parseArray(things);
        int size = jsonArray.size();
        for (int i = 0; i < size ;i++){
            if(jsonArray.getJSONObject(i).getInteger("id").equals(goodID)){

                if (new Goods(goodID).checkIfOverCount(count)){
                    throw new OverCountException();
                }
                jsonArray.getJSONObject(i).replace("count",
                        jsonArray.getJSONObject(i).getInteger("count") + count);
                new Goods(goodID).buy(count);
                String send = "UPDATE buyer SET things=?,price=? WHERE id = ?";
                Connection connection = DBUtil.getConnection();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(send);
                    preparedStatement.setString(1,jsonArray.toJSONString());
                    preparedStatement.setDouble(2,price + (new Goods(goodID).getPrice()) * count);
                    preparedStatement.setInt(3,id);

                    preparedStatement.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                return true;
            }
        }
        if (new Goods(goodID).checkIfOverCount(count)){
            throw new OverCountException();
        }
        JSONObject object = new JSONObject();
        object.put("id",goodID);
        object.put("group_name",new Goods(goodID).getGroup_name());
        object.put("name",new Goods(goodID).getName());
        object.put("description",new Goods(goodID).getDescription());
        object.put("count",count);
        jsonArray.add(object);
        new Goods(goodID).buy(count);
        String send = "UPDATE buyer SET things=?,price=? WHERE id=?";
        Connection connection = DBUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(send);
            preparedStatement.setString(1,jsonArray.toJSONString());
            preparedStatement.setDouble(2,price + (new Goods(goodID).getPrice()) * count);
            preparedStatement.setInt(3,id);


            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public void deleteGoods(Integer gid){
        JSONArray jsonArray = JSONArray.parseArray(things);
        int size = jsonArray.size();
        for (int i = 0; i < size ;i++){
            if(jsonArray.getJSONObject(i).getInteger("id").equals(gid)){
                Integer count = jsonArray.getJSONObject(i).getInteger("count");
                jsonArray.remove(i);
                new Goods(gid).add(count);
                String send = "UPDATE buyer SET things=?,price=? WHERE id = ?";
                Connection connection = DBUtil.getConnection();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(send);
                    preparedStatement.setString(1,jsonArray.toJSONString());
                    preparedStatement.setDouble(2,price - (new Goods(gid).getPrice()) * count);
                    preparedStatement.setInt(3,id);

                    preparedStatement.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


            }
        }

    }
}
