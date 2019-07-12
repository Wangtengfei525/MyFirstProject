package com.coolcloud.sacw.store.web;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.store.entity.Store;
import com.coolcloud.sacw.store.entity.StoreAddForm;
import com.coolcloud.sacw.store.entity.StoreExample;
import com.coolcloud.sacw.store.entity.StoreUpdateForm;
import com.coolcloud.sacw.store.service.StoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 储物柜控制类 <br/>
 * 储物柜采用预排序遍历树结构与邻接表树结构结合，新增及删除操作请勿直接插入数据库 </br/>
 * 请使用StoreService提供的save及delete方法 <br/>
 * 新增请指定父节点id，否则将插入为根节点 <br/>
 * 若左右值结构混乱，使用StoreService的rebuild方法重新建立左右值
 * 
 * @author yyx
 *
 * @date 2017年12月21日 下午5:09:05
 */
@RestController
@RequestMapping(value = "/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    /**
     * 查询所有目录树
     * 
     * @return
     */
    @GetMapping("/category")
    public Object categpry() {
        List<Store> stores = storeService.getCategoryTree();
        return Result.success().total(stores.size()).rows(stores);
    }

    /**
     * 查询所有柜子
     * 
     * @return
     */
    @GetMapping("queryAll")
    public Result queryAll() {
        List<Store> stores = storeService.queryAll();
        return Result.success().total(stores.size()).rows(stores);
    }
    /**
     * 查询柜子
     * 
     * @return
     */
    @GetMapping("/tree")
    public Object queryStores() {
        return storeService.queryAll();
    }
    /**
     * 查询所有子柜子
     * 
     * @param parentId
     * @return
     */
    @GetMapping("queryByParentId")
    public Result selectChests(String parentId) {
    	System.out.println(parentId);
        List<Store> chests = storeService.selectChests(parentId);
        return Result.success().total(chests.size()).rows(chests);
    }

    /**
     * 更新柜子
     * 
     * @param store
     * @return
     */
    @PostMapping("/update")
    public Result update(@Validated StoreUpdateForm form) {
        Store store = new Store();
        BeanUtils.copyProperties(form, store);
        Integer num = storeService.update(store);
        return Result.success(num + "条数据修改成功");
    }

    /**
     * 添加一个柜子
     * 
     * @param store
     * @return
     */
    @PostMapping("/add")
    public Result add(@Validated StoreAddForm form) {
        Store store = new Store();
        BeanUtils.copyProperties(form, store);
        int num = storeService.add(store);
        return Result.success(num + "个柜子添加成功");
    }

    /**
     * 删除柜子
     * 
     * @param id
     *            柜子ID
     * @return
     */
    @PostMapping("delete")
    public Result delete(String id) {
        Integer num = storeService.deleteByParentId(id);
        return Result.success(num + "个柜子已删除");
    }

    /**
     * 导入cvs文件后返回异常财物信息
     *
     * @param file
     *            传入的cvs文件
     * @param request
     * @param storeExample
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public Object export(@RequestParam MultipartFile file, String parentId) {
        StoreExample storeExample = new StoreExample();
        storeExample.setParentId(parentId);
        return storeService.export(file, storeExample);
    }

    /**
     * 通过柜子ID查询其下所有异常财物信息
     * 
     * @param storeExample
     *            包含柜子ID分页信息的storeExample对象
     * @return
     */
    @GetMapping("/queryExcetionById")
    public Result exceptionProperties(StoreExample storeExample) {
        List<Object> list = storeService.selectExcept(storeExample);
        PageInfo<Object> pageInfo = new PageInfo<>(list);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 开柜操作<br/>
     * 参数：<br/>
     * id:柜子id<br/>
     * 
     * @param id
     *            柜子id
     * @return
     */
    @PostMapping("/open")
    public Result open(String id) {
        boolean opened = storeService.open(id);
        return Result.success(opened ? "柜子已打开" : "开柜失败");
    }

    /**
     * 查询所有柜子
     * 
     * @return
     */
    @GetMapping("/list")
    public Result list(StoreExample storeExample) {
        Integer page = storeExample.getPage();
        Integer rows = storeExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<Store> exchanges = storeService.getByExample(storeExample);
        PageInfo<Store> pageInfo = new PageInfo<>(exchanges);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 获取柜子信息
     * 
     * @param id
     *            柜子id
     * @return
     */
    @GetMapping("/get")
    public Result get(String id) {
        Store store = storeService.get(id);
        return Result.success().add("store", store);
    }

    /**
     * 根据parentId结构重建树结构左右值
     *
     * @return
     */
    @GetMapping("/build")
    public Result build() {
        storeService.rebuild();
        return Result.success("构建成功");
    }

    /**
     * 根据柜子id查询当前及其下所有柜子信息（包含物品数量）
     *
     * @return
     */
    @GetMapping("/children-with-amount")
    public Result childrenWithPropertiesAmount(String id) {
        List<Store> allData = storeService.allData(id);
        return Result.success().total(allData.size()).rows(allData);
    }

    
    
    /**
     * 导出储物柜
     * 
     * @param id
     *            储物柜id
     * @return
     */
    @GetMapping("/export")
    public ModelAndView export(String id) {

        ModelAndView mav = new ModelAndView("storeWithPorpertiesXlsxView");

        List<Store> stores = storeService.getTreeWithProperties(id);

        mav.addObject("stores", stores);
        return mav;
    
        
    }

}
