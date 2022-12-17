package com.gl18.webmarket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SendError {

    public static String toFront(){
        JSONObject object = new JSONObject();
        object.put("data",new JSONObject().toJSONString());
        object.put("status",-1);
        return object.toJSONString();
    }
}
