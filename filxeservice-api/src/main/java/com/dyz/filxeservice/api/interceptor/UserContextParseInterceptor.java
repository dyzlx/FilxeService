package com.dyz.filxeservice.api.interceptor;

import com.dyz.filxeservice.common.execption.IllegalParamException;
import com.dyz.filxeservice.common.model.UserContext;
import com.dyz.filxeservice.common.model.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@WebFilter(filterName = "userContextParseFilter", urlPatterns = {"/logicfiles/*", "/partitions/*"})
public class UserContextParseInterceptor implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        log.info("parse user context from http request header");
        UserContext currentUserContext = UserContextHolder.getUserContext();
        String userIdStr = httpServletRequest.getHeader(UserContext.USER_ID);
        String rolesStr = httpServletRequest.getHeader(UserContext.USER_ROLES);
        String correlationId = httpServletRequest.getHeader(UserContext.CORRELATION_ID);
        String authToken = httpServletRequest.getHeader(UserContext.AUTH_TOKEN);
        // userId and correlationId is required
        if (!ObjectUtils.allNotNull(userIdStr, correlationId)) {
            log.error("required headers userId and correlationId param is null");
            throw new IllegalParamException(0, "required header param is null");
        }
        List<String> roles = null;
        if(Objects.nonNull(rolesStr)) {
            roles = Arrays.asList(rolesStr.split(","));
        }
        Integer userId = Integer.parseInt(userIdStr);
        currentUserContext.setUserId(userId);
        currentUserContext.setUserRoles(roles);
        currentUserContext.setCorrelationId(correlationId);
        currentUserContext.setAuthToken(authToken);
        chain.doFilter(httpServletRequest, response);
    }
}