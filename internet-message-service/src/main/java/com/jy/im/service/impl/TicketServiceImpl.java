package com.jy.im.service.impl;

import com.jy.im.common.util.PasswordUtil;
import com.jy.im.service.TicketService;
import com.jy.im.service.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    private final Map<String, User> userTicket = new HashMap<>();

    @Override
    public String bindUserTicket(User user) {
        String ticket = generateTicket();
        userTicket.put(ticket, user);
        return ticket;
    }

    @Override
    public User getUserByTicket(String ticket) {
        return userTicket.get(ticket);
    }

    private static String generateTicket() {
        try {
            return PasswordUtil.encryptPassword(String.valueOf(System.currentTimeMillis()));
        } catch (NoSuchAlgorithmException e) {
            logger.error("create password error", e);
            return "default";
        }
    }
}
