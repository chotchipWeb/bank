package com.chotchip.Notification.service.impl;

import com.chotchip.Notification.service.ResolveUserService;
import org.springframework.stereotype.Service;

@Service
public class ResolveUserServiceImpl implements ResolveUserService {
    @Override
    public int resolveUserId(String token) {
        // TODO мы должны использовать токен для аутентификации
        return 1;
    }
}
