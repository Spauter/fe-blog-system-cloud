package com.bloducspauter.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BsSendEmailFunction {

    @Autowired
    private SendEmailProperties sendEmailProperties;

    public void sendEmail(String requst, String to) throws IOException {
        isValidPort();
        isValidHost();
        String s = toSend(requst, to);
        log.info(s);
        URL url = new URL(s);
        log.info(url.toString());
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

    private String toSend(String request, String to) {
        return "http://" + sendEmailProperties.getHost() + ":" + sendEmailProperties.getPort() + "/" + request + "?email=" + to;
    }

    private void isValidPort() throws MalformedURLException {
        if (!sendEmailProperties.getPort().matches("\\d{0,9}")) {
            throw new MalformedURLException("Invalid host! It must be a number!");
        }
        try {
            int port = Integer.parseInt(sendEmailProperties.getPort());
            if (port <= 0 || port > 65535) {
                throw new MalformedURLException("Invalid host! It must between 1 and 65535!");
            }
        } catch (NumberFormatException numberFormatException) {
            throw new MalformedURLException("Invalid host! Please check it");
        }
    }

    private void isValidHost() throws MalformedURLException {
        if (sendEmailProperties.getHost()==null){
            throw new MalformedURLException("Please type a host!");
        }
        if (sendEmailProperties.getHost().isEmpty()){
            throw new MalformedURLException("Please type a host!");
        }
    }

    private void setResponseCode(int responseCode){
        switch (responseCode/100){
            case 1:{
                log.warn("This request is not finished");
                break;
            } case 2:{
                log.info("OK");
                break;
            } case 3:{
                log.warn("Redirect Server");
                break;
            } case 4:{
                log.error("Request error");
                break;
            } case 5:{
                log.error("System Error");
                break;
            } case 6:{
                log.error("Unresolvable Response Headers");
            }
        }
    }
}
