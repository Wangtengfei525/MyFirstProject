package com.coolcloud.sacw.property.entity;

import org.hibernate.validator.constraints.Length;

import com.coolcloud.sacw.common.BasePaginationForm;

/**
 * 拆分机动车查询参数
 * 
 * @author xyz
 *
 */
public class MotorSearchParam extends BasePaginationForm {

    /**
     * 案件名称模糊查询
     */
    @Length(max = 300, message = "案件名称长度应小于{max}个字符")
    private String caseNameLike;

    public String getCaseNameLike() {
        return caseNameLike;
    }

    public void setCaseNameLike(String caseNameLike) {
        this.caseNameLike = caseNameLike;
    }

}
