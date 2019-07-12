package com.coolcloud.sacw.system.mapper;

import com.coolcloud.sacw.common.BaseMapper;
import com.coolcloud.sacw.system.entity.User;

public interface UserMapper extends BaseMapper<User, String> {

    Integer updateUser(User user);

    /**
     * 根据用户名查找用户
     * 
     * @param name
     * @return
     */
    User selectByName(String name);
}
