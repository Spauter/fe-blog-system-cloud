package com.bloducspauter.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "fe-ornament")
public interface GetSystemUsageAPI {
    @GetMapping("CPUUsage")
    Map<String, Object> getCPUUsage();

    @GetMapping("RAMUsage")
    Map<String, Object> getRAMUsage();

    @GetMapping("JVMUsage")
    Map<String,Object> getJVMUsage();
}
