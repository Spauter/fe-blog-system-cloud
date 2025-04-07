package com.bloducspauter.chat.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.bloducspauter.chat.entity.AuditChatAPI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class AuditUtil {
    static AuditChatAPI api = new AuditChatAPI();
    static Map<String, String> querys = new HashMap<>();
    static Map<String, String> bodys = new HashMap<>();
    static Map<String, String> headers = new HashMap<>();

    static {
        headers.put("Authorization", "APPCODE " + api.getAppcode());
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    }

    public static Map<String, Object> getResult(String text) throws Exception {
        bodys.put("text", "text");
        Map<String,Object>map=new HashMap<>();
        HttpResponse response = HttpUtils.doPost(api.getHost(), api.getPath(), api.getMethod(), headers, querys, bodys);
        String string= EntityUtils.toString(response.getEntity(),"UTF-8");
        JSONObject jsonObject=JSONObject.parseObject(string);
        if ((jsonObject.get("result").toString()).equals("1")) {
            map.put("code", 200);
        } else {
            JSONArray jsonArray=jsonObject.getJSONArray("resultItems");
            JSONObject result=jsonArray.getJSONObject(0);
            String msg=result.getString("msg");
            map.put("code",500);
            map.put("msg",msg);
        }
        return map;
    }
}
