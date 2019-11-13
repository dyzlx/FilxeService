package com.dyz.filxeservice.client.config;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@Configuration
public class MultipartSupportConfiguration {
	
	private static final List<HttpMessageConverter<?>> converters = new RestTemplate().getMessageConverters();
	private static final ObjectFactory<HttpMessageConverters> factory = new ObjectFactory<HttpMessageConverters>() {
		@Override
		public HttpMessageConverters getObject() throws BeansException {
			return new HttpMessageConverters(MultipartSupportConfiguration.converters);
		}
	};
	
	@Bean
	public Encoder feignFormEncoder() {
		return new SpringFormEncoder(new SpringEncoder(factory));
	}
}