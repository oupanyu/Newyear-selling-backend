package com.gl18.webmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebmarketApplication {

    public static DBConfigReader config;

    public static void main(String[] args) throws ClassNotFoundException {

        config = new DBConfigReader();
        Class.forName("com.mysql.cj.jdbc.Driver");
        SpringApplication.run(WebmarketApplication.class, args);
    }

}
