package com.bloducspauter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


@Slf4j
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final int MAX_REQUESTS = 250; // 同一时间段内允许的最大请求数
    private static final long TIME_PERIOD = 60 * 1000; // 时间段，单位为毫秒 在一分钟内限制ip访问次数为10次

    private Map<String, Integer> requestCounts = new HashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        String address=ipAddress.split(",")[0];
        log.info("Client IP address: {}", address);
        System.out.println(address);
        // 检查 IP 地址是否已经达到最大请求数
        if (requestCounts.containsKey(address) && requestCounts.get(address) >= MAX_REQUESTS) {
            response.setStatus(429); //设置响应状态码
            response.getWriter().write("Too many requests from this IP address");
            return false;
        }

        // 更新 IP 地址的请求数
        requestCounts.put(address, requestCounts.getOrDefault(address, 0) + 1);

        // 在指定时间后清除 IP 地址的请求数
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        requestCounts.remove(address);
                    }
                },
                TIME_PERIOD
        );
        return true;
    }
}
