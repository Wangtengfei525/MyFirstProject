package com.coolcloud.sacw.system.entity;

import com.coolcloud.sacw.common.BaseEntity;

/**
 * 分类代码类别
 * 
 * @author 袁永祥
 *
 * @date 2017年8月24日 上午10:22:02
 */
public class CategoryType extends BaseEntity {

	private static final long serialVersionUID = 1128389259487265065L;

	/**
	 * Database Column Remarks: 类别名称
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column sys_category_type.name
	 *
	 * @mbg.generated Thu Aug 24 10:18:29 CST 2017
	 */
	private String name;

	/**
	 * Database Column Remarks: 类别代码
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column sys_category_type.code
	 *
	 * @mbg.generated Thu Aug 24 10:18:29 CST 2017
	 */
	private String code;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sys_category_type.name
	 *
	 * @return the value of sys_category_type.name
	 *
	 * @mbg.generated Thu Aug 24 10:18:29 CST 2017
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sys_category_type.name
	 *
	 * @param name
	 *            the value for sys_category_type.name
	 *
	 * @mbg.generated Thu Aug 24 10:18:29 CST 2017
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sys_category_type.code
	 *
	 * @return the value of sys_category_type.code
	 *
	 * @mbg.generated Thu Aug 24 10:18:29 CST 2017
	 */
	public String getCode() {
		return code;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sys_category_type.code
	 *
	 * @param code
	 *            the value for sys_category_type.code
	 *
	 * @mbg.generated Thu Aug 24 10:18:29 CST 2017
	 */
	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

}