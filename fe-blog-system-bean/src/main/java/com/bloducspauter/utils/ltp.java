package com.bloducspauter.utils;

import com.alibaba.fastjson.JSONObject;
import com.bloducspauter.FeBlogBeanMain;
import com.bloducspauter.bean.ltpData;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class ltp {
    // webapi接口地址
    static String key = "";
    private static final String WEBTTS_URL = "http://ltpapi.xfyun.cn/v1/ke";
    // 应用ID
    private static final String APPID = "ddd62ac7";
    // 接口密钥
    private static final String API_KEY = "cb8daf4a691f5f78c9c371ed1e714e88";
    // 文本
    private static String TEXT = "自然语言处理是计算机科学领域与人工智能领域中的一个重要方向。它研究能实现人与计算机之间用自然语言进行有效通信的各种理论和方法。自然语言处理是一门融语言学、计算机科学、数学于一体的科学。因此，这一领域的研究将涉及自然语言，即人们日常使用的语言，所以它与语言学的研究有着密切的联系，但又有重要的区别。自然语言处理并不是一般地研究自然语言，而在于研制能有效地实现自然语言通信的计算机系统，特别是其中的软件系统。因而它是计算机科学的一部分。";


    private static final String TYPE = "dependent";


    public static void main(String[] args) throws IOException {
//		System.out.println(TEXT.length());
        try {
            Map<String, String> header = buildHttpHeader();
            String result = HttpUtil.doPost1(WEBTTS_URL, header, "text=" + URLEncoder.encode(TEXT, StandardCharsets.UTF_8));
            System.out.println("itp 接口调用结果：" + result);
            ltpData ltpData = JSONObject.parseObject(result, ltpData.class);

            assert ltpData != null;
            ltpData.getData().get(0).get("ke").forEach(k -> {
                key += k.get("word") + ",";
            });
            System.out.println(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FeBlogBeanMain.start();
//
    }

    /**
     * 组装http请求头
     */
    public static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
        String curTime = System.currentTimeMillis() / 1000L + "";
        String param = "{\"type\":\"" + TYPE + "\"}";
        String paramBase64 = new String(Base64.encodeBase64(param.getBytes(StandardCharsets.UTF_8)));
        String checkSum = DigestUtils.md5Hex(API_KEY + curTime + paramBase64);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Param", paramBase64);
        header.put("X-CurTime", curTime);
        header.put("X-CheckSum", checkSum);
        header.put("X-Appid", APPID);
        return header;
    }
}
