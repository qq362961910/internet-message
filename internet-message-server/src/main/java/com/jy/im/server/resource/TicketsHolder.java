package com.jy.im.server.resource;

import com.jy.im.service.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TicketsHolder {

    private final Map<String, User> userTicket = new HashMap<>();

    public void addUserTicket(User user, String ticket) {
        userTicket.put(ticket, user);
    }

    public User getUserByTicket(String ticket) {
        return userTicket.get(ticket);
    }


}
