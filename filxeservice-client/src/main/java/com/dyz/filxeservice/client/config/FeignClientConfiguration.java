package com.dyz.filxeservice.client.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

@Slf4j
@Configuration
@Import({ClientErrorConfiguration.class, ClientLogConfiguration.class, MultipartSupportConfiguration.class})
public class FeignClientConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        log.info("passing user context http header parameters");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (Objects.nonNull(headerNames)) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                if (Objects.nonNull(name) && name.startsWith("ms-")) {
                    template.header(name, values);
                }
            }
        }
    }
}
