package com.jy.im.service.impl;

import com.jy.im.service.UserService;
import com.jy.im.service.entity.User;

public class UserServiceImpl implements UserService {
    @Override
    public User queryUserByUserIdAndPassword(long userId, String password) {
        User user = new User();
        user.setId(userId);
        user.setPassword(password);
        user.setUsername("null");
        user.setAvatar("http://ogoysg5ko.bkt.clouddn.com/avatar.jpg");
        return user;
    }
}
