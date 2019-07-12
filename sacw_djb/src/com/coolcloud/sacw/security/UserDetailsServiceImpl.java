package com.coolcloud.sacw.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coolcloud.sacw.system.entity.User;
import com.coolcloud.sacw.system.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String ADMIN_USERNAME = "admin";

    private static final String ADMIN_PASSWORD = "123456";

    private static final String ADMIN_USER_ID = "0";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if (ADMIN_USERNAME.equals(username)) {
            user = new User();
            user.setId(ADMIN_USER_ID);
            user.setName(ADMIN_USERNAME);
            user.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        } else {
            user = userService.getByName(username);
            if (user == null) {
                throw new UsernameNotFoundException("用户：" + username + "不存在");
            }
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true, authorities);
    }

}
