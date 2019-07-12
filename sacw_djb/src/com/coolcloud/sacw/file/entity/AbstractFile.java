package com.coolcloud.sacw.file.entity;

/**
 * 抽象文件类型，用于在保存文件后传递文件属性
 * 
 * @author 袁永祥
 *
 * @date 2017年8月19日 下午1:19:00
 */
public class AbstractFile {
	/**
	 * 文件原始名称
	 */
	private String name;

	/**
	 * 文件绝对路径
	 */
	private String path;

	/**
	 * 文件后缀
	 */
	private String suffix;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
