package com.bloducspauter.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("fe-ornament")
@Slf4j
public class GetInfoController {

    @RequestMapping("/CPUUsage")
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
                map.put("cpuUsage",-1);
                return map;
            }

            cpuUsage =  ((value * 1000) / 10.0);
            map.put("code",200);
            map.put("cpuUsage",String.format("%.2f",cpuUsage*100));
            map.put("cpuFree",String.format("%.2f",100-cpuUsage*100));
        } catch (Exception e) {
            map.put("code",500);
            map.put("msg",e.getCause());
            map.put("cpuUsage",0);
            map.put("cpuFree",0);
            e.printStackTrace();
        }
        return map;
    }


    @RequestMapping("/RAMUsage")
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
            map.put("usagMemory", 0);
        }
        return map;
    }

    @RequestMapping("/JVMUsage")
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

    @RequestMapping("/DiskUsage")
    public Map<String,Object> getDiskUsed() {
        File win = new File("/");
        Map<String,Object>map=new HashMap<>();
        if (win.exists()) {
            long total = win.getTotalSpace();
            long freeSpace = win.getFreeSpace();
            System.out.println("磁盘总量：" + total / 1024 / 1024 / 1024);
            System.out.println("磁盘剩余总量：" + freeSpace / 1024 / 1024 / 1024);
            System.out.println("磁盘已用总量：" + (total - freeSpace) / 1024 / 1024 / 1024);
            map.put("total",total/ 1024 / 1024 / 1024);
            map.put("freeSpace",freeSpace/ 1024 / 1024 / 1024);
            map.put("usage",(total-freeSpace) / 1024 / 1024 / 1024);
        }
        return map;
    }

    @Test
    public void v(){
        log.info(getCPUUsage()+"");
        log.info(getJVMUsage()+"");
        log.info(getRAMUsage()+"");
        log.info(getDiskUsed()+"");
    }
}
