package com.gl18.webmarket.object;

import com.alibaba.fastjson2.JSONObject;
import com.gl18.webmarket.WebmarketApplication;
import com.gl18.webmarket.database.CheckName;
import com.gl18.webmarket.object.exception.DuplicationOfNameException;
import com.gl18.webmarket.object.exception.IncompleteInfoException;
import com.gl18.webmarket.utils.DBUtil;

import java.sql.*;

public class Goods {

    private String name,description;

    private Integer id,left,maximum,bought;
    private Double price;

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
                price = result.getDouble("price");

            }
            statement.cancel();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Goods(){}

    public String getWithJSONString(){
        JSONObject rootObj = new JSONObject(),object = new JSONObject();
        rootObj.put("code",200);
        object.put("id",id);
        object.put("name",name);
        object.put("left",maximum - bought);
        object.put("status",status);
        object.put("description",description);
        object.put("maxpic",maxpic);
        object.put("maximum",maximum);
        object.put("bought",bought);
        object.put("price",price);

        rootObj.put("data",object);

        return rootObj.toJSONString();
    }

    public void createAtDatabase() throws IncompleteInfoException {
        String send = "INSERT INTO `goods` (`name`, `price`, `status`, `description`, `maximum`) VALUES " +
                "(?, ?, ?, ?, ?)";

        if (name == null || maximum ==null || price == null || status == null){
            throw new IncompleteInfoException();
        }//如果信息不完整，抛出异常
        if (description == null){
            description = "No description.";
        }


        Connection connection = DBUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(send);
            preparedStatement.setString(1,name);
            preparedStatement.setDouble(2,price);
            preparedStatement.setInt(3,status);
            preparedStatement.setString(4,description);
            preparedStatement.setInt(5,maximum);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
