package com.bloducspauter.api.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient(name = "fe-ornament")
public interface GetSystemUsageAPI {
    @RequestMapping("/fe-ornament/CPUUsage")
    Map<String, Object> getCPUUsage();

    @RequestMapping("/fe-ornament/RAMUsage")
    Map<String, Object> getRAMUsage();

    @RequestMapping("/fe-ornament/JVMUsage")
    Map<String,Object> getJVMUsage();

    @RequestMapping("/DiskUsage")
    Map<String,Object> getDiskUsed();
}
