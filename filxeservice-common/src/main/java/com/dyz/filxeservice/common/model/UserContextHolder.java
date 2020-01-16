package com.dyz.filxeservice.common.model;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserContextHolder {
    
    private static final ThreadLocal<UserContext> USER_CONTEXT = new ThreadLocal<>();
    
    public static final UserContext getUserContext() {
        log.info("get user context");
        UserContext context = USER_CONTEXT.get();
        if(Objects.isNull(context)) {
            context = createEmptyUserContext();
            USER_CONTEXT.set(context);
        }
        return USER_CONTEXT.get();
    }
    
    public static final void setUserContext(UserContext context) {
        if(Objects.isNull(context)) {
            log.error("object UserContext is null");
        }
        log.info("set user context, context = {},", context);
        USER_CONTEXT.set(context);
    }
    
    private static final UserContext createEmptyUserContext() {
        log.info("new user context");
        return new UserContext();
    }
}