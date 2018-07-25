package com.jy.im.service;

import com.jy.im.service.entity.User;

public interface TicketService {

    /**
     * 绑定用户ticket
     * @return ticket
     * */
    String bindUserTicket(User user);

    /**
     * 根据ticket获取用户
     * */
    User getUserByTicket(String ticket);
}
