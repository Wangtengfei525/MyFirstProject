package com.coolcloud.sacw.common;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 预排序树结构Mapper接口
 * 
 * @author 袁永祥
 *
 * @date 2017年12月21日 下午4:40:00
 */
public interface PreSortMapper<T extends PreSortEntity, P extends Serializable> extends BaseMapper<T, P> {

	/**
	 * 锁表
	 */
	void lock();

	/**
	 * 释放表锁
	 */
	void unlock();

	/**
	 * 查询当前数据中右值的最大值
	 * 
	 * @return 右值最大值
	 */
	Integer selectMaxRightValue();

	/**
	 * 添加节点前更新其后节点的左值，左值大于value的节点其左值加2
	 * 
	 * @param value
	 *            要插入节点的左值 - 1（上一节点的右值）
	 * @return 影响的行数
	 */
	Integer updateLeftValuePlus(Integer value);

	/**
	 * 添加节点前更新其后节点的右值，右值大于value的节点其右值加2
	 * 
	 * @param value
	 *            要插入节点的左值 - 1（上一节点的右值）
	 * @return 影响的行数
	 */
	Integer updateRightValuePlus(Integer value);

	/**
	 * 查询所有子节点，包括自身
	 * 
	 * @param id
	 * @return
	 */
	List<T> selectAllChildren(String id);

	/**
	 * 删除节点时更新其后节点的左值，左值大于right的节点其左值减去width
	 * 
	 * @param width
	 *            节点宽度（右值 - 左值 + 1）
	 * @param right
	 *            删除的节点的右值
	 * @return
	 */
	Integer updateLeftValueMinus(@Param("width") Integer width, @Param("right") Integer right);

	/**
	 * 删除节点时更新其后节点的右值，右值大于right的节点其右值减去width
	 * 
	 * @param width
	 *            节点宽度（右值 - 左值 + 1）
	 * @param right
	 *            删除的节点的右值
	 * @return
	 */
	Integer updateRightValueMinus(@Param("width") Integer width, @Param("right") Integer right);

}
