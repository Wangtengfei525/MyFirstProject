package com.coolcloud.sacw.system.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.common.util.Assert;
import com.coolcloud.sacw.system.entity.Role;
import com.coolcloud.sacw.system.entity.RoleExample;
import com.coolcloud.sacw.system.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 角色控制类
 * 
 * @author xyz
 *
 * @date 2018年4月18日 上午11:29:35
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色数据
     * 
     * @param roleExample
     *            若传入 <b>userId</b> 参数 ，则查询对应用户的角色状态
     * @return
     */
    @GetMapping("/list")
    public Result list(RoleExample roleExample) {
        String userId = roleExample.getUserId();
        if (userId != null) {
            List<Role> roles = roleService.getByUser(userId);
            return Result.success().total(roles.size()).rows(roles);
        }
        Integer page = roleExample.getPage();
        Integer rows = roleExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<Role> roles = roleService.getByExample(roleExample);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 更新角色信息
     * 
     * @param role
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Result update(@Valid Role role) {
        Integer num = roleService.update(role);
        return Result.success(num + "个角色已更改");
    }

    /**
     * 添加角色信息
     * 
     * @param role
     * @return
     */
    @PostMapping("/add")
    public Result add(@Valid Role role) {
        role.setId(null);
        Integer num = roleService.save(role);
        return Result.success(num + "个角色添加成功");
    }

    /**
     * 批量删除
     * 
     * @param ids
     *            逗号分隔的id
     * @return
     */
    @PostMapping("/delete")
    public Result delete(String ids) {
        Assert.isTrue(StringUtils.hasText(ids), "无效id");
        List<String> list = new ArrayList<>();
        for (String string : ids.split(",")) {
            list.add(string);
        }
        Integer num = 0;
        if (list.size() == 1) {
            num = roleService.delete(list.get(0));
        } else {
            num = roleService.deleteBatch(list);
        }
        return Result.success(num + "个角色成功删除");
    }

    /**
     * 分配权限
     * 
     * @param id
     *            角色id
     * @param permissions
     *            逗号分隔的权限id
     * @return
     */
    @PostMapping("/assign-permissions")
    public Result assignPermissions(@RequestParam("id") String id, @RequestParam("permissions") String permissions) {
        Assert.isTrue(StringUtils.hasText(id), "无效id");
        Assert.isTrue(roleService.exists(id), "无效id");
        List<String> list = new ArrayList<>();
        for (String string : permissions.split(",")) {
            list.add(string);
        }
        Integer num = this.roleService.assignPermissions(id, list);
        return Result.success(num + "个权限已分配");
    }

}
