package com.coolcloud.sacw.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coolcloud.sacw.common.PreSortMapper;
import com.coolcloud.sacw.store.entity.Store;

/**
 * 存储柜Mapper
 * 
 * @author 袁永祥
 *
 * @date 2017年12月21日 下午4:40:56
 */
public interface StoreMapper extends PreSortMapper<Store, String> {

	/**
	 * 根据父id查询子储物柜集合
	 * 
	 * @param parentId
	 * @return
	 */
	List<Store> queryStoresByParentId(String parentId);

	/**
	 * 查询所有为目录的记录
	 * 
	 * @return
	 */
	List<Store> queryAllTree();

	/**
	 * 通过ID查询夫ID
	 * 
	 * @param id
	 * @return
	 */
	String queryPidById(String id);

	/**
	 * 查询储物柜类型
	 */
	String queryType(String id);
	/**
	 * 查询名称
	 */
	String queryName(String id);

	/**
	 * 通过柜子ID查询其下所有财物数量
	 * 
	 * @param list
	 *            柜子ID集合
	 * @return
	 */
	Integer selectProNumByStoreId(@Param("list") List<String> list);

	/**
	 * 查询当前目录及其下所有目录信息（包含物品数量）
	 * 
	 * @param id
	 * @return
	 */
	List<Store> selectChildrenWithPropertiesAmount(String id);

	List<Store> selectStore();

	Integer selectPropertiesAmountByStoreId(String id);
	
	Integer selectPropertiesAmountByStoreIdCar(String id);
	
	//这个方法是用来查询某一个柜子数量的前提
	Integer  selectOneStorePropertyAmount(String id);
		
	
	
}
