package com.dyz.filxeservice.client.config;

import com.dyz.filxeservice.client.model.ClientUserContext;
import com.dyz.filxeservice.client.model.ClientUserContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;

import java.util.List;

@Configuration
@Import({ClientErrorConfiguration.class, ClientLogConfiguration.class, MultipartSupportConfiguration.class})
public class FeginClientConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ClientUserContext clientUserContext = ClientUserContextHolder.getClientUserContext();
        Assert.notNull(clientUserContext, "user context is null");

        Integer userId = clientUserContext.getUserId();
        List<String> roles = clientUserContext.getUserRoles();
        String correlationId = clientUserContext.getCorrelationId();
        String authToken = clientUserContext.getAuthToken();

        Assert.notNull(userId, "user id is null");
        Assert.notNull(correlationId, "correlation id is null");

        template.header(ClientUserContext.USER_ID, userId.toString());
        template.header(ClientUserContext.USER_ROLES, Strings.join(roles, ','));
        template.header(ClientUserContext.CORRELATION_ID, correlationId.toString());
        template.header(ClientUserContext.AUTH_TOKEN, authToken);
    }
}
