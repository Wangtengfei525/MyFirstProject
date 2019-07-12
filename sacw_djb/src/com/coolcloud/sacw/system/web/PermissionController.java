package com.coolcloud.sacw.system.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.system.entity.Permission;
import com.coolcloud.sacw.system.entity.PermissionExample;
import com.coolcloud.sacw.system.service.PermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/system/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 权限列表
     * 
     * @param permissionExample
     *            若指定有 roleId 参数，则查询用户的权限状态 若指定有 tree == true，返回树结构 同时指定 tree ==
     *            true 和 id 参数，以 id 为顶构建树结构
     * @return
     */
    @GetMapping("/list")
    public Object list(PermissionExample permissionExample) {
        String roleId = permissionExample.getRoleId();
        if (roleId != null) {
            List<Permission> permissions = permissionService.getByRole(roleId);
            return Result.success().total(permissions.size()).rows(permissions);
        }
        // 树状列表
        Boolean isTree = permissionExample.getTree();
        if (isTree != null && isTree) {
            String id = permissionExample.getId();
            List<String> tops = new ArrayList<>();
            if (id != null) {
                tops.add(id);
            }
            return permissionService.tree(tops);
        }
        // 基本列表
        Integer page = permissionExample.getPage();
        Integer rows = permissionExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<Permission> permissions = permissionService.getByExample(permissionExample);
        PageInfo<Permission> pageInfo = new PageInfo<>(permissions);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    /**
     * 添加
     * 
     * @param permission
     * @return
     */
    @PostMapping("/add")
    public Result add(@Valid Permission permission) {
        permission.setId(null);
        if (permission.getDisplay() == null) {
            permission.setDisplay(1);
        }
        Integer num = permissionService.save(permission);
        return Result.success(num + "个权限添加成功");
    }

    /**
     * 更新
     * 
     * @param permission
     * @return
     */
    @PostMapping("/update")
    public Result update(Permission permission) {
        Integer num = permissionService.update(permission);
        return Result.success(num + "个权限已修改");
    }

    /**
     * 删除
     * 
     * @param ids
     *            逗号分隔的id
     * @return
     */
    @PostMapping("/delete")
    public Result delete(String ids) {
        List<String> list = new ArrayList<>();
        for (String string : ids.split(",")) {
            list.add(string);
        }
        Integer num = 0;
        if (list.size() == 1) {
            num = permissionService.delete(list.get(0));
        } else {
            num = permissionService.deleteBatch(list);
        }
        return Result.success(num + "个权限成功删除");
    }

}
