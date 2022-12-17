package com.gl18.webmarket.utils;

import com.gl18.webmarket.WebmarketApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static Connection connection;

    static {
        String url = String.format("jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=UTF-8&userSSL=false"
                , WebmarketApplication.config.dbhost
                ,WebmarketApplication.config.dbname);
        try {
            connection = DriverManager.getConnection(url, WebmarketApplication.config.dbusername, WebmarketApplication.config.dbpasswd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
