package com.dyz.filxeservice.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dyz.filxeservice.client.model.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Configuration
public class ClientErrorConfiguration {
	@Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }

    class UserErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
        		ObjectMapper objectMapper = new ObjectMapper();
            Exception exception = null;
            try {
                String respJson = Util.toString(response.body().asReader());
                Result<?> result = objectMapper.readValue(respJson, Result.class);
                if (!Objects.equals(result.getCode(), 1)) {
                    exception = new RuntimeException("remote service error, "+result.getMessage());
                }
            } catch (Exception ex) {
                log.error("client resolver result error", ex);
            }
            return exception;
        }
    }
}
