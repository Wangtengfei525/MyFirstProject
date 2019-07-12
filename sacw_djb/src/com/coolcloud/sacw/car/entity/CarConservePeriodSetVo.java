package com.coolcloud.sacw.car.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class CarConservePeriodSetVo {

    @NotBlank(message = "养护内容代码不可为空")
    private String conserveContentCode;

    /**
     * 养护周期（天）
     */
    @NotNull(message = "养护周期不可为空")
    @Range(min = 1, max = Short.MAX_VALUE, message = "养护周期应在{min}到{max}之间")
    private Integer conservePeriod;

    /**
     * 是否启用，0不启用1启用
     */
    @NotNull(message = "是否启用不可为空")
    @Range(min = 0, max = 1, message = "是否启用取值只能为0或1")
    private Integer enabled;

    public String getConserveContentCode() {
        return conserveContentCode;
    }

    public void setConserveContentCode(String conserveContentCode) {
        this.conserveContentCode = conserveContentCode;
    }

    public Integer getConservePeriod() {
        return conservePeriod;
    }

    public void setConservePeriod(Integer conservePeriod) {
        this.conservePeriod = conservePeriod;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
     
    
}