package com.coolcloud.sacw.system.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.exception.OperationFailedException;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.system.entity.Unit;
import com.coolcloud.sacw.system.entity.UnitExample;
import com.coolcloud.sacw.system.mapper.UnitMapper;

/**
 * 交换单位服务类
 * 
 * @author 袁永祥
 *
 * @date 2017年8月21日 上午11:43:46
 */
@Service
public class UnitService extends BaseService<Unit, String> {

    @Autowired
    private UnitMapper unitMapper;

    /**
     * 获取单位下的所有子单位代码
     * 
     * @param id
     * @return
     */
    public List<String> getAllChildrenCodes(String id) {
        List<Unit> children = this.getAllChildren(id);
        List<String> childIds = new ArrayList<>();
        for (Unit unit : children) {
            childIds.add(unit.getCode());
        }
        return childIds;
    }

    /**
     * 获取单位树形结构
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public List<Unit> getTree() {
        List<Unit> units = this.getAll();
        List<Unit> tree = new ArrayList<>();
        for (Unit u1 : units) {
            if (StringUtils.isEmpty(u1.getParentId())) {
                tree.add(u1);
            }
            for (Unit u2 : units) {
                if (u2.getParentId() != null && u2.getParentId().equals(u1.getId())) {
                    u1.getChildren().add(u2);
                }
            }
        }
        return tree;
    }
    
    /**
     * 
     *获取财物类别树形结构 
     * @return
     */
    @Transactional(readOnly = true)
    public List<Unit> getPropertyTypeTree() {
        List<Unit> units = unitMapper.selectPropertyType();
        List<Unit> tree = new ArrayList<>();
        for (Unit u1 : units) {
            if (u1.getParentId().equals("900000104")||u1.getParentId().equals("")) {
                tree.add(u1);
            }
            for (Unit u2 : units) {
                if (u2.getParentId() != null && u2.getParentId().equals(u1.getCode())) {
                    u1.getChildren().add(u2);
                }
            }
        }
        return tree;
    }

    /**
     * 获取所有下级单位（包含当前单位）
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    private List<Unit> getAllChildren(String id) {
        if (StringUtils.isEmpty(id)) {
            return new ArrayList<>();
        }
        return unitMapper.selectAllChildren(id);
    }

    /**
     * 覆盖默认删除方法，删除前先判断有无下级单位，更新树结构左右值
     */
    @Override
    @Transactional
    public int delete(String id) {

        UnitExample example = new UnitExample();
        example.setParentId(id);
        if (this.getByExample(example).size() > 0) {
            return 0;
        }

        Unit unit = get(id);
        if (unit == null) {
            return 0;
        }
        try {
            unitMapper.lock();

            Integer left = unit.getLeftValue();
            Integer right = unit.getRightValue();
            Integer width = Integer.valueOf(right.intValue() - left.intValue() + 1);

            unitMapper.updateLeftValueMinus(width, right);
            unitMapper.updateRightValueMinus(width, right);

            return super.delete(id);

        } catch (Exception e) {
            throw e;
        } finally {
            unitMapper.unlock();
        }

    }

    /**
     * 覆写默认保存方法，保存前先更新左右值
     */
    @Override
    @Transactional
    public int save(Unit unit) {
        int leftValue, rightValue;
        Integer num;
        try {
            unitMapper.lock();
            Unit parent = get(unit.getParentId());
            if (parent == null) {
                Integer max = unitMapper.selectMaxRightValue();
                if (max == null) {
                    leftValue = 1;
                    rightValue = 2;
                } else {
                    leftValue = max.intValue() + 1;
                    rightValue = max.intValue() + 2;
                }
            } else {
                Integer right = parent.getRightValue();
                unitMapper.updateRightValuePlus(right.intValue() - 1);
                unitMapper.updateLeftValuePlus(right.intValue() - 1);
                leftValue = right;
                rightValue = right + 1;
            }

            unit.setLeftValue(Integer.valueOf(leftValue));
            unit.setRightValue(Integer.valueOf(rightValue));
            num = super.save(unit);
        } catch (Exception e) {
            throw new OperationFailedException(e);
        } finally {
            unitMapper.unlock();
        }
        return num;
    }

    /**
     * 重建树结构左右值，在结构混乱后可调用此方法重建结构
     */
    public void rebuild() {
        try {
            unitMapper.lock();
            List<Unit> units = getAll();
            int value = 1;
            Unit temp = new Unit();
            for (Unit unit : units) {
                if (unit.getParentId() == null) {

                    temp.setId(unit.getId());

                    temp.setLeftValue(value++);
                    value = buildChildren(unit.getId(), value);
                    temp.setRightValue(value++);

                    unitMapper.updateByPrimaryKeySelective(temp);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            unitMapper.unlock();
        }
    }

    /**
     * 构建下级单位左右值
     * 
     * @param parentId
     *            父单位id
     * @param value
     *            父节点右值
     * @return 最后一个子节点右值
     */
    private int buildChildren(String parentId, int value) {
        UnitExample example = new UnitExample();
        example.setParentId(parentId);
        List<Unit> children = getByExample(example);
        Unit temp = new Unit();
        for (Unit unit : children) {
            temp.setId(unit.getId());

            temp.setLeftValue(value++);
            value = buildChildren(unit.getId(), value);
            temp.setRightValue(value++);

            unitMapper.updateByPrimaryKeySelective(temp);
        }
        return value;
    }

    /**
     * 覆写默认更新方法，避免直接更改父节点及左右值
     */
    @Override
    @Transactional
    public int update(Unit entity) {
        // 不允许更改父节点及左右值
        entity.setParentId(null);
        entity.setLeftValue(null);
        entity.setRightValue(null);
        return super.update(entity);
    }

    /**
     * 添加单位
     * 
     * @param unit
     *            单位
     * @return
     */
    @Transactional
    public int add(Unit unit) {
        String code = unit.getCode();
        Assert.isTrue(!unitMapper.existsWithCode(code), "代码" + code + "已存在");
        unit.setId(code);
        return save(unit);
    }
    /**
     * 根据id查询单位
     * 
     * @param unit
     *            单位
     * @return
     */
    @Transactional
    public Unit selectByPrimaryKey(String id) {
    	return unitMapper.selectByPrimaryKey(id);
    }

}
