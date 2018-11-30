package com.example.demo.jmx.profile;

import com.google.gson.Gson;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.HashMap;
import java.util.Map;

/**
 * system common monitor
 *
 * @author nanco
 * @create 2018/8/8
 **/
@Configuration
@ManagedResource(objectName = "monitor:name=SystemCommonMonitor")
public class SystemCommonMonitorMBean {

    private String systemName;

    private Gson gsonTool = new Gson();


    @ManagedAttribute(description = "system_name", defaultValue = "demo")
    public String getSystemName() {
        return this.systemName;
    }

    @ManagedAttribute
    public void setSystemName(String name) {
        this.systemName = name;
    }

    @ManagedOperation(description = "systemInfo")
    public String systemInfo() {
        Map<String, String> system = new HashMap(8);
        system.put("cpuCoreSize", "4");
        system.put("memorySize", "8G");
        system.put("cpuRatio", "20%");
        system.put("memoryRatio", "2%");
        system.put("totalDisk", "200G");
        system.put("usedDisk", "120G");
        system.put("freeDisk", "80G");


        return gsonTool.toJson(system);
    }


}
