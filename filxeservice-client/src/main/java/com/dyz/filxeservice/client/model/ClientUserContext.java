package com.dyz.filxeservice.client.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"userRoles", "authToken"})
public class ClientUserContext {

    public final static String USER_ID = "ms-user-id";

    public final static String USER_ROLES = "ms-user-roles";

    public final static String CORRELATION_ID = "ms-correlation-id";

    public final static String AUTH_TOKEN = "ms-auth-token";

    private Integer userId;

    private List<String> userRoles;

    private String correlationId;

    private String authToken;
}
