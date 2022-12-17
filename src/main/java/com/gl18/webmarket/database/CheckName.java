package com.gl18.webmarket.database;

import com.gl18.webmarket.utils.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckName {

    public static boolean ifDuplicationOfName(String name){
        Connection conn = DBUtil.getConnection();
        Statement st = null;
        try {
            st = conn.createStatement();
            String sql= String.format("select * from buyer where name='%s'",name);
            ResultSet re=st.executeQuery(sql);
            if(re.next())
                return true;
            else
                return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static boolean ifDuplicationOfStudentID(Integer sid){
        Connection conn = DBUtil.getConnection();
        Statement st = null;
        try {
            st = conn.createStatement();
            String sql= String.format("select * from buyer where sid='%s'",sid);
            ResultSet re=st.executeQuery(sql);
            if(re.next())
                return true;
            else
                return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
