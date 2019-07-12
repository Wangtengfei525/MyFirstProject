package com.coolcloud.sacw.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.coolcloud.sacw.property.entity.PropertyLog;

/**
 * 财物变更事件
 * 
 * @author xyz
 *
 */
public class PropertyChangeEvent extends ApplicationEvent {

    private static final long serialVersionUID = 6021647847496908057L;

    public PropertyChangeEvent(List<PropertyLog> logs) {
        super(logs);
    }

    @SuppressWarnings("unchecked")
    public List<PropertyLog> getPropertyLogs() {
        return (List<PropertyLog>) getSource();
    }
}
