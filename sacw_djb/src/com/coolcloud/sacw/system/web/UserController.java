package com.coolcloud.sacw.system.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coolcloud.sacw.common.Result;
import com.coolcloud.sacw.system.entity.User;
import com.coolcloud.sacw.system.entity.UserExample;
import com.coolcloud.sacw.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/system/user")
public class UserController {

    /**
     * 默认用户密码
     */
    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/list")
    public Result list(UserExample userExample) {
        Integer page = userExample.getPage();
        Integer rows = userExample.getRows();
        if (page != null && rows != null) {
            PageHelper.startPage(page, rows);
        }
        List<User> users = userService.getByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return Result.success().total(pageInfo.getTotal()).rows(pageInfo.getList());
    }

    @PostMapping("/add")
    public Result add(@Valid User user) {
        user.setId(null);
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        Integer num = userService.save(user);
        return Result.success(num + "个用户添加成功");
    }

    @PostMapping("/update")
    public Object update(@Valid User user) {
        Integer num = userService.update(user);
        return Result.success(num + "个用户更新成功");
    }

    @PostMapping("/updateUser")
    @ResponseBody
    public Object updateUser(@Valid User user) {
        Integer num = userService.update(user);
        return Result.success(num + "个用户更新成功");
    }

    /**
     * 批量删除
     * 
     * @param ids
     *            逗号分隔的id
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam(name = "ids", required = true) String ids) {
        List<String> list = new ArrayList<>();
        for (String string : ids.split(",")) {
            list.add(string);
        }
        Integer num = 0;
        if (list.size() == 1) {
            num = userService.delete(list.get(0));
        } else {
            num = userService.deleteBatch(list);
        }
        return Result.success(num + "个用户成功删除");
    }

    /**
     * 分配角色
     * 
     * @param id
     *            用户id
     * @param roles
     *            逗号分隔的角色id
     * @return
     */
    @PostMapping(value = "/assign-roles")
    public Result assignPermissions(String id, String roles) {
        Integer num = userService.assignPermissions(id, roles);
        return Result.success(num + "个角色已分配");
    }

    /**
     * 获取用户信息
     * 
     * @param id
     *            用户id
     * @return
     */
    @GetMapping("/get")
    public Result get(String id) {
        return Result.success().add("user", userService.get(id));
    }

}
