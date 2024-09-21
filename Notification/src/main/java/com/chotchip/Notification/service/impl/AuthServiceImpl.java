package com.chotchip.Notification.service.impl;

import com.chotchip.Notification.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public String authUser(String user, String password) {
        // TODO тут должна быть аутентификация пользователя и получения его токена
        return "token";
    }
}
