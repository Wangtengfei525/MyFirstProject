package com.coolcloud.sacw.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.coolcloud.sacw.property.entity.PropertyLog;
import com.coolcloud.sacw.property.service.PropertyLogService;

/**
 * 财物变更事件监听器
 * 
 * @author xyz
 *
 */
@Component
public class PropertyChangeEventListener implements ApplicationListener<PropertyChangeEvent> {

    @Autowired
    private PropertyLogService propertyLogService;

    /**
     * 异步写入财物变更记录
     */
    //@Async
    @Override
    public void onApplicationEvent(PropertyChangeEvent event) {
        List<PropertyLog> logs = event.getPropertyLogs();
        for (PropertyLog log : logs) {
            propertyLogService.save(log);
        }
    }

}
