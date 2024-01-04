package com.bloducspauter.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class BsSendEmailFunction implements SendEmail {


    @Autowired
    private SendEmailProperties sendEmailProperties;


    @Override
    public void sendEmailVerifyCode(String request, String to, String code) throws IOException {
        String url = toSendVerifyCode(request, to, code);
        StartToSendEmail(url);
    }

    //审核后发送的邮件
    @Override
    public void sendAuditNotice(String request, String to, String isAudited) throws IOException {
        //TODO
    }

    //拼接url
    private String toSendVerifyCode(String request, String to, String code) {
        return "http://" + sendEmailProperties.getHost() + ":" + sendEmailProperties.getPort() + "/" + request + "?email=" + to + "&code=" + code;
    }

    private String toSendAuditEmail(String request,String to,boolean isAudited){
        return "http://" + sendEmailProperties.getHost() + ":" + sendEmailProperties.getPort() + "/" + request + "?email=" + to +"&isAudited"+isAudited;
    }


    //打印返回码
    private void setResponseCode(int responseCode) {
        switch (responseCode / 100) {
            case 1: {
                log.warn("This request is not finished");
                break;
            }
            case 2: {
                log.info("OK");
                break;
            }
            case 3: {
                log.warn("Redirect Server");
                break;
            }
            case 4: {
                log.error("Request error");
                break;
            }
            case 5: {
                log.error("System Error");
                break;
            }
            case 6: {
                log.error("Unresolvable Response Headers");
            }
        }
    }


    private void StartToSendEmail(String StringUrl) throws IOException {
        if (!sendEmailProperties.isOpenSendEmail()) {
            log.warn("You set the open_send_email is false,so no email was sent");
            return;
        }
        checkSendUrl();
        log.info("The target url:"+StringUrl);
        URL url = new URL(StringUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
// 设置其他请求头...
        int responseCode = connection.getResponseCode();
        setResponseCode(responseCode);
        InputStream inputStream = (responseCode >= 200 && responseCode < 300) ? connection.getInputStream() : connection.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        String responseBody = response.toString();
        System.out.println(responseBody);
    }

    public void checkSendUrl() throws MalformedURLException {
        if(sendEmailProperties.getPort()==null){
            throw new MalformedURLException("Empty server port. You may define com.bs.port");
        }
        if (sendEmailProperties.getHost() == null) {
            throw new MalformedURLException("Empty serer host, You may define com.bs.host");
        }
        if (!sendEmailProperties.getPort().matches("\\d{0,9}")) {
            throw new MalformedURLException("Invalid server port! It must be a number!");
        }
        try {
            int port = Integer.parseInt(sendEmailProperties.getPort());
            if (port <= 0 || port > 65535) {
                throw new MalformedURLException("Invalid server port! It must between 1 and 65535!");
            }
        } catch (NumberFormatException numberFormatException) {
            throw new NumberFormatException("Invalid server port! Please check it");
        }
        if(!sendEmailProperties.isOpenSendEmail()){
            log.warn("The properties of open_send_email was defined false, so no email will be sent");
        }
    }
}
