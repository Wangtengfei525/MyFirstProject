package com.coolcloud.sacw.store.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.coolcloud.sacw.common.BaseService;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.file.entity.AbstractFile;
import com.coolcloud.sacw.file.service.FileService;
import com.coolcloud.sacw.property.entity.Property;
import com.coolcloud.sacw.property.service.PropertyService;
import com.coolcloud.sacw.store.Cabinet;
import com.coolcloud.sacw.store.entity.Library;
import com.coolcloud.sacw.store.entity.Store;
import com.coolcloud.sacw.store.entity.StoreExample;
import com.coolcloud.sacw.store.mapper.LibraryMapper;
import com.coolcloud.sacw.store.mapper.StoreMapper;
import com.coolcloud.sacw.system.entity.Category;
import com.coolcloud.sacw.system.entity.CategoryExample;
import com.github.pagehelper.PageHelper;

@Service
public class StoreService extends BaseService<Store, String> {

    private static final String DEFAULT_PARENT_ID = "0";

    /**
     * 储物柜控制服务ip
     */
    @Value("${app.store.addr}")
    private String addr;

    /**
     * 服务端口号
     */
    @Value("${app.store.port}")
    private Integer port;

    @Autowired
    private FileService fileService;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private LibraryMapper libraryMapper;

    @Transactional(readOnly = true)
    public List<Store> queryAll() {
        List<Store> stores = getAll();
        List<Store> tree = new ArrayList<>();
        for (Store store : stores) {
            if (store.getParentId().equals("0")) {
                tree.add(store);
            }
            for (Store s : stores) {
                if (s.getParentId() != null && s.getParentId().equals(store.getId())) {
                    store.addChild(s);
                }
            }
        }
        return tree;
    }
    /**
     * 覆写默认保存方法， 保存前更新树结构左右值
     */
    @Override
    @Transactional
    public int save(Store store) {
        int leftValue, rightValue;
        Integer num;
        try {
            storeMapper.lock();
            Store parent = get(store.getParentId());
            if (parent == null) {
                Integer max = storeMapper.selectMaxRightValue();
                if (max == null) {
                    leftValue = 1;
                    rightValue = 2;
                } else {
                    leftValue = max.intValue() + 1;
                    rightValue = max.intValue() + 2;
                }
            } else {
                Integer right = parent.getRightValue();
                storeMapper.updateRightValuePlus(right.intValue() - 1);
                storeMapper.updateLeftValuePlus(right.intValue() - 1);
                leftValue = right;
                rightValue = right + 1;
            }

            store.setLeftValue(Integer.valueOf(leftValue));
            store.setRightValue(Integer.valueOf(rightValue));
            num = super.save(store);
        } catch (Exception e) {
            throw e;
        } finally {
            storeMapper.unlock();
        }
        return num;
    }

    /**
     * 覆写默认删除方法，删除前更新树结构左右值
     */
    @Override
    @Transactional
    public int delete(String id) {
        StoreExample example = new StoreExample();
        example.setParentId(id);
        if (getByExample(example).size() > 0) {
            return 0;
        }

        Store store = get(id);
        if (store == null) {
            return 0;
        }
        try {
            storeMapper.lock();

            Integer left = store.getLeftValue();
            Integer right = store.getRightValue();
            Integer width = Integer.valueOf(right.intValue() - left.intValue() + 1);

            storeMapper.updateLeftValueMinus(width, right);
            storeMapper.updateRightValueMinus(width, right);

            return super.delete(id);

        } catch (Exception e) {
            throw e;
        } finally {
            storeMapper.unlock();
        }
    }

    /**
     * 覆写默认更新方法，避免直接更改父节点及左右值
     */
    @Override
    @Transactional
    public int update(Store entity) {
        // 不允许更改父节点及左右值
        entity.setParentId(null);
        entity.setLeftValue(null);
        entity.setRightValue(null);
        return super.update(entity);
    }

    public List<Store> selectChests(String parentId) {

        return storeMapper.queryStoresByParentId(parentId);
    }

    /**
     * 查询柜子id集合中的所有财务信息
     * 
     * @return
     */
    public List<Property> queryPropertiesByStoreIds(String id, StoreExample storeExample) {
        // 判断store对象及其id是否为空,不为空进行查询
        if (id != null) {
            System.out.println(id);
            List<String> list = selectChest(id);
            List<Property> properties = null;
            if (list.size() == 0) {
                properties = new ArrayList<>();
            } else {
                Integer page = storeExample.getPage();
                Integer rows = storeExample.getRows();
                if (page != null && rows != null) {
                    PageHelper.startPage(page, rows);
                }
                properties = propertyService.selectByStoreIds(list);
            }
            return properties;
        }
        return null;
    }

    /**
     * 返回该目录下所有柜子ID的集合
     * 
     * @param id
     * @return
     */
    public List<String> selectChest(String id) {
        // 获取所有柜子集合
        List<Store> stores = this.getAll();
        // 获取该id的子柜子所有集合
        List<Store> tree = new ArrayList<>();
        // 存放为柜子的id集合
        List<String> ids = new ArrayList<>();

        if (hasChild(id) == false) {
            ids.add(id);
            return ids;
        }
        for (Store store : stores) {
            if (store.getParentId().equals(id)) {
                tree.add(store);
                children(store, stores);
            }
        }
        for (Store store : tree) {
            if (store.getTreeType() == "0") {
                ids.add(store.getId());
            }
        }
        for (Store store : tree) {
            children2(store.getChildren(), ids);
        }
        System.out.println(tree.size());
        System.out.println(ids.size());
        return ids;
    }

    /**
     * 递归获取所有为柜子的集合
     * 
     * @param stores
     * @param ids
     */
    private void children2(List<Store> stores, List<String> ids) {
        for (Store child : stores) {
            if (child.getParentId() != null && child.getTreeType().equals("0")) {
                ids.add(child.getId());
            } else if (child.getParentId() != null && child.getTreeType().equals("0")) {
                children2(child.getChildren(), ids);
            }
        }
    }

    /**
     * 递归删除父ID自身及其下属所有柜子
     * 
     * @param parentId
     * @return
     */
    @Transactional
    public Integer deleteByParentId(String parentId) {
        // 判断父ID是否为空
        if (!StringUtils.hasText(parentId)) {
            return null;
        }
        // 删除记录数
        Integer num = 0;
        // 父ID不为空查询其第一级子集合
        List<Store> list = storeMapper.queryStoresByParentId(parentId);
        // 当子集不为空时，递归调用方法删除子集
        if (list.size() > 0) {
            for (Store store2 : list) {
                // 如果还有子集再递归调用本方法
                if (hasChild(store2.getId())) {
                    deleteByParentId(parentId);
                } else {
                    // 没有子集直接删除
                    num += storeMapper.deleteByPrimaryKey(store2.getId());
                }
            }
            num += storeMapper.deleteByPrimaryKey(parentId);
            return num;
        }
        // 当其没有子集是直接删除自身
        if (list.size() == 0) {
            num += storeMapper.deleteByPrimaryKey(parentId);
            return num;
        }
        return null;
    }

    /**
     * 检查是否还有子节点
     * 
     * @param parentId
     * @return
     */
    private boolean hasChild(String parentId) {
        List<Store> list = storeMapper.queryStoresByParentId(parentId);
        if (list.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 递归获取子集合
     * 
     * @param store
     * @param stores
     */
    private void children(Store store, List<Store> stores) {
        for (Store child : stores) {
            if (child.getParentId() != null && child.getParentId().equals(store.getId())) {
                store.addChild(child);
                children(child, stores);
            }
        }
    }

    public String selectPid(String id) {
        return storeMapper.queryPidById(id);
    }

    /**
     * 开柜
     * 
     * @param id
     */
    public boolean open(String id) {
        Assert.isTrue(!StringUtils.isEmpty(id), "柜子id不能为空");
        Store store = storeMapper.selectByPrimaryKey(id);
        Assert.notNull(store, "柜子不存在");
        int contro = Integer.parseInt(store.getStoreContro());
        Assert.isTrue(contro != 0, "非智能柜");
        Cabinet cabinet = new Cabinet(addr, port);
        return cabinet.Open(contro);
    }

    /**
     * 导入文件
     * 
     * @param file
     * @return
     */
    public List<Object> export(MultipartFile file, StoreExample storeExample) {
        if (file != null) {
            // 上传文件到目录
            AbstractFile af = fileService.save(file);
            // 得到文件存放绝对路径
            String realPath = af.getPath();
            // 解析csv文件
            readCvsFile(realPath);
            List<Object> list = selectExcept(storeExample);
            return list;
        }
        return null;
    }

    /**
     * 查询异常财物信息
     * 
     * @param storeExample
     * @return
     */
    public List<Object> selectExcept(StoreExample storeExample) {
        String parentId = storeExample.getParentId();
        // 获取所有子柜子集合
        List<String> chests = selectChest(parentId);
        Integer page = storeExample.getPage();
        Integer rows = storeExample.getRows();
        // 开始分页
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        // 通过子柜子集合查询财物基本信息(包含是否异常信息)
        List<Object> list = libraryMapper.selectEx(chests);
        return list;
    }

    /**
     * 解析前台传入的cvs文件，将异常财物的二维码存入数据库
     * 
     * @param realPath
     */
    private void readCvsFile(String realPath) {
        try {
            // 删除之前盘库表所有数据
            libraryMapper.del();
            String encoding = "GBK";
            File file = new File(realPath);
            // 判断文件是否存在
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式

                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineTxt != null) {
                        Map<String, String> pd = new HashMap<>();
                        String[] Txtlist = lineTxt.split(",");
                        for (int i = 0; i < Txtlist.length; i++) {
                            if (Txtlist[i].startsWith("0E")) {
                                pd.put("EVM", Txtlist[i]);
                            }
                            if (Txtlist[i].startsWith("E")) {
                                Txtlist[i] = "0" + Txtlist[i];
                                pd.put("EVM", Txtlist[i]);
                            }
                            if (Txtlist[i].startsWith("3")) {
                                Txtlist[i] = "0E" + Txtlist[i];
                                pd.put("EVM", Txtlist[i]);
                            }
                            Library library = new Library();
                            library.setEvm(Txtlist[i]);
                            // 保存异常财物二维码信息
                            libraryMapper.insertSelective(library);
                        }
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件！！！");
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在！！！");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("读取文件内容出错！！！");
            e.printStackTrace();
        } finally {

        }
    }

    @Transactional(readOnly = true)
    public List<Store> getCategory() {
        Store store = new Store();
        store.setTreeType("1");
        return storeMapper.selectByExample(store);
    }

    /**
     * 获取所有目录树
     * 
     * @return
     */
    public List<Store> getCategoryTree() {
        List<Store> stores = this.getCategory();
        List<Store> tree = new ArrayList<>();
        for (Store store : stores) {
            if (store.getParentId() != null && store.getParentId().equals("0")) {
                tree.add(store);
            }
            for (Store s : stores) {
                if (s.getParentId() != null && s.getParentId().equals(store.getId())) {
                    store.addChild(s);
                }
            }
        }
        return tree;
    }

    /**
     * 重建树结构左右值，在结构混乱后可调用此方法重建结构
     */
    public void rebuild() {
        try {
            storeMapper.lock();
            List<Store> stores = getAll();
            int value = 1;
            Store temp = new Store();
            for (Store store : stores) {
                if (store.getParentId() == null || store.getParentId().equals("0")) {

                    temp.setId(store.getId());

                    temp.setLeftValue(value++);
                    value = buildChildren(store.getId(), value);
                    temp.setRightValue(value++);

                    storeMapper.updateByPrimaryKeySelective(temp);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            storeMapper.unlock();
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
        StoreExample example = new StoreExample();
        example.setParentId(parentId);
        List<Store> children = getByExample(example);
        Store temp = new Store();
        for (Store store : children) {
            temp.setId(store.getId());

            temp.setLeftValue(value++);
            value = buildChildren(store.getId(), value);
            temp.setRightValue(value++);

            storeMapper.updateByPrimaryKeySelective(temp);
        }
        return value;
    }

    @Transactional(readOnly = true)
    public List<Store> getChildrenWithPropertiesAmount(String id) {
        if (StringUtils.isEmpty(id)) {
            return new ArrayList<>();
        }
        return storeMapper.selectChildrenWithPropertiesAmount(id);
    }

    public List<Store> allData(String id) {

        StoreExample example = new StoreExample();
        example.setParentId(id);

        List<Store> stores = this.getByExample(example);
        for (Store store : stores) {
        	String name = storeMapper.queryName(store.getId());
        	name = name.substring(2, 4);
        	if(!"车库".equals(name)) {
       // 		store.setPropertiesAmount(storeMapper.selectPropertiesAmountByStoreId(store.getId()));
      //  		store.setPropertiesAmount(propertiesAmount);
        		
        		//这一行是自己填写的
        		store.setPropertiesAmount(storeMapper.selectOneStorePropertyAmount(store.getId()));
        		
        	}else {
        		store.setPropertiesAmount(storeMapper.selectPropertiesAmountByStoreIdCar(store.getId()));
        	}
        	
            
        }
        return stores;
    }

    /**
     * 获取储物柜树，同时查出每个柜子下的物品
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public List<Store> getTreeWithProperties(String id) {
        List<Store> stores, tree = new ArrayList<>();
        List<Property> properties;
        if (StringUtils.isEmpty(id)) {
            stores = getAll();
            properties = propertyService.selectInStock();
        } else {
            stores = storeMapper.selectAllChildren(id);
            properties = propertyService.selectInStockByStoreId(id);
        }
        Map<String, List<Property>> map = new HashMap<>();
        for (Property property : properties) {

            String kwbm = property.getKwbm();
            if (StringUtils.isEmpty(kwbm)) {
                continue;
            }

            List<Property> list = map.get(kwbm);

            if (list == null) {
                list = new ArrayList<>();
                map.put(kwbm, list);
            }

            list.add(property);
        }

        for (Store store : stores) {

            List<Property> ps = map.get(store.getId());

            if (ps != null) {
                store.setProperties(ps);
            }

            if (store.getParentId().equals("0") || store.getId().equals(id)) {
                tree.add(store);
            }

            for (Store s : stores) {
                if (s.getParentId() != null && s.getParentId().equals(store.getId())) {
                    store.addChild(s);
                }
            }
        }
        return tree;
    }

    /**
     * 添加储物柜
     * 
     * @param store
     * @return
     */
    public int add(Store store) {
        String parentId = store.getParentId();
        if (DEFAULT_PARENT_ID.equals(parentId)) {
            store.setStoreUnitName(store.getStoreName());
        } else {
            Store parent = get(parentId);
            Assert.notNull(parent, "父级储物柜不存在");
            store.setStoreUnitName(parent.getStoreUnitName() + "/" + store.getStoreName());
        }
        return save(store);
    }
    
    
    //这个是用来查询一个柜子里面的财物数量
    public   Integer   findStorePropertyAmount(String  storeId)
	{
		/*Integer  num=propertyMapper*/
		
		
		Integer  num=storeMapper.selectOneStorePropertyAmount(storeId);
		
		return num;
	}

}
