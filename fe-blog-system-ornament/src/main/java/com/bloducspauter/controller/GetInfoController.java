package com.bloducspauter.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@RestController("fe-ornament")
@Slf4j
public class GetInfoController {

    @GetMapping("CPUUsage")
    public Map<String,Object> getCPUUsage() {
        double cpuUsage = 0;
        Map<String,Object>map=new HashMap<>();
        try {
            MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
            AttributeList list = mbsc.getAttributes(name, new String[]{"ProcessCpuLoad"});
            if (list.isEmpty()) {
               map.put("code",500);
               map.put("cpuUsage",0);
               return map;
            }

            Attribute att = (Attribute) list.get(0);
            Double value = (Double) att.getValue();

            // value为-1表示无法获取CPU使用情况
            if (value == -1) {
                map.put("code",500);
                map.put("cpuUsage",0);
                return map;
            }

            cpuUsage = ((int) (value * 1000) / 10.0);
            map.put("code",200);
            map.put("cpuUsage",cpuUsage);
        } catch (Exception e) {
            map.put("code",500);
            map.put("msg",e.getCause());
            map.put("cpuUsage",0);
            e.printStackTrace();
        }
        return map;
    }


    @GetMapping("RAMUsage")
    public Map<String, Object> getRAMUsage() {
        Map<String, Object> map = new HashMap<>();
        try {
            MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
            AttributeList list = mbsc.getAttributes(name, new String[]{"TotalPhysicalMemorySize", "FreePhysicalMemorySize"});
            if (list.isEmpty()) {
                map.put("code", 0);
                map.put("data", 0);
            }

            long totalMemory = 0;
            long freeMemory = 0;

            Attribute att = (Attribute) list.get(0);
            totalMemory = Long.parseLong(att.getValue().toString());

            att = (Attribute) list.get(1);
            freeMemory = Long.parseLong(att.getValue().toString());
            map.put("code", 200);
            map.put("totoalMemory", String.format("%.2f", totalMemory * 1.0 / 1024 / 1024 / 1024));
            map.put("freeMemory", String.format("%.2f", freeMemory * 1.0 / 1024 / 1024 / 1024));
            map.put("usagMemory", String.format("%.2f", (totalMemory - freeMemory) * 1.0 / 1024 / 1024 / 1024));
        } catch (Exception e) {
            map.put("code", 500);
            map.put("msg",e.getCause());
            e.printStackTrace();
            map.put("totoalMemory", 0);
            map.put("freeMemory", 0);
        }
        return map;
    }

    @GetMapping("JVMUsage")
    public Map<String,Object> getJVMUsage() {
        Map<String,Object>map=new HashMap<>();
        try {
            Runtime runtime = Runtime.getRuntime();
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;
            map.put("code",200);
            map.put("totalMemory",String.format("%.2f",totalMemory*1.0/1024/1024));
            map.put("freeMemory",String.format("%.2f",freeMemory*1.0 /1024/1024));
            map.put("usedMemory",String.format("%.2f",usedMemory*1.0 /1024/1024));
        } catch (Exception e) {
            map.put("code",500);
            map.put("msg",e.getCause());
            map.put("totalMemory",0);
            map.put("freeMemory",0);
            map.put("usedMemory",0);
            e.printStackTrace();
        }
        return map;
    }

    @Test
    public void Test1() {
        log.info("CPU" + getCPUUsage());
        log.info("RAM" + getRAMUsage());
        log.info("JVM" + getJVMUsage());
    }
}
