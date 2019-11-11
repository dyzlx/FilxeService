package com.dyz.filxeservice.client;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@FeignClient(value = "filxeservice", configuration = LogicFileClient.MultipartSupportConfig.class)
public interface LogicFileClient {

	@RequestMapping(value = "/filxeservice/logicfiles/{logicFileId}", method = RequestMethod.DELETE, consumes = {
			"application/json", "application/xml" })
	void deleteLogicFile(@PathVariable(name = "logicFileId") Integer logicFileId,
			@RequestHeader(name = "userId") Integer userId);

	@RequestMapping(value = "/filxeservice/logicfiles", method = RequestMethod.DELETE, consumes = { "application/json",
			"application/xml" })
	void deleteLogicFiles(@RequestBody List<Integer> logicFileIds, @RequestHeader(name = "userId") Integer userId);

	@RequestMapping(value = "/filxeservice/logicfiles/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	List<Integer> uploadFiles(@RequestPart(name = "file") MultipartFile[] file,
			@RequestHeader(name = "userId") Integer userId);

	@Configuration
	class MultipartSupportConfig {

		private static final List<HttpMessageConverter<?>> converters = new RestTemplate().getMessageConverters();
		private static final ObjectFactory<HttpMessageConverters> factory = new ObjectFactory<HttpMessageConverters>() {
			@Override
			public HttpMessageConverters getObject() throws BeansException {
				return new HttpMessageConverters(MultipartSupportConfig.converters);
			}
		};

		@Bean
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder(new SpringEncoder(factory));
		}
	}

	// @Configuration
	// class MultipartSupportConfig {
	//
	// @Autowired
	// private ObjectFactory<HttpMessageConverters> messageConverters;
	//
	// @Bean
	// public Encoder feignFormEncoder() {
	// return new SpringFormEncoder(new SpringEncoder(messageConverters));
	// }
	// @Bean
	// @Scope("prototype")
	// @Primary
	// public Encoder multipartFormEncoder() {
	// return new SpringFormEncoder();
	// }
	//
	// @Bean
	// public feign.Logger.Level multipartLoggerLevel() {
	// return feign.Logger.Level.FULL;
	// }
	// }
}
