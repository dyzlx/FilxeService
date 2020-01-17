package com.dyz.filxeservice.client.model;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class ClientUserContextHolder {

    private static final ThreadLocal<ClientUserContext> USER_CONTEXT = new ThreadLocal<>();

    public static final ClientUserContext getClientUserContext() {
        ClientUserContext context = USER_CONTEXT.get();
        if (Objects.isNull(context)) {
            context = createEmptyClientUserContext();
            USER_CONTEXT.set(context);
        }
        return USER_CONTEXT.get();
    }

    public static final void setUserContext(ClientUserContext context) {
        if (Objects.isNull(context)) {
            log.error("object UserContext is null");
        }
        log.info("set user context, context = {},", context);
        USER_CONTEXT.set(context);
    }

    private static final ClientUserContext createEmptyClientUserContext() {
        return new ClientUserContext();
    }
}
