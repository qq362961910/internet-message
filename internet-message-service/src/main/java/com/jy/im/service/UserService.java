package com.jy.im.service;

import com.jy.im.service.entity.User;

public interface UserService {
    User queryUserByUserIdAndPassword(long userId, String password);
}
