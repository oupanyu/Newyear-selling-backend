package com.gl18.webmarket;

import com.alibaba.fastjson2.JSONObject;

public class ReCAPTCHA {

    String captcha = "https://www.recaptcha.net/recaptcha/api/siteverify?secret=%s&response=%s";

    public boolean checkByReturnBoolean(String response){
        JSONObject object = JSONObject.parseObject(Request.get(String.format(captcha,WebmarketApplication.config.recaptcha_key,response)));
        return object.getBoolean("success");
    }
}
