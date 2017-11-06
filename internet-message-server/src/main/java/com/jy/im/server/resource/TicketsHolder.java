package com.jy.im.server.resource;

import com.jy.im.common.util.PasswordUtil;
import com.jy.im.service.entity.User;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
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

    public static String generateTicket() {
        try {
            return PasswordUtil.encryptPassword(String.valueOf(System.currentTimeMillis()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
