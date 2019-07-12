package com.coolcloud.sacw.common;

/**
 * 预排序树结构
 * 
 * @author 袁永祥
 *
 * @date 2017年12月21日 下午4:25:03
 */
public abstract class PreSortEntity extends BaseEntity {

	private static final long serialVersionUID = 8390585612747381147L;

	private Integer leftValue;

	private Integer rightValue;

	public Integer getLeftValue() {
		return leftValue;
	}

	public void setLeftValue(Integer leftValue) {
		this.leftValue = leftValue;
	}

	public Integer getRightValue() {
		return rightValue;
	}

	public void setRightValue(Integer rightValue) {
		this.rightValue = rightValue;
	}

}
