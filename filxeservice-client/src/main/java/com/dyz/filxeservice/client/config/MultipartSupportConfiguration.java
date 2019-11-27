package com.dyz.filxeservice.client.config;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.form.MultipartFormContentProcessor;
import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import static feign.form.ContentType.MULTIPART;
import static java.util.Collections.singletonMap;

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
		return new SpringMultipartFormEncoder(new SpringEncoder(factory));
	}
}

class SpringMultipartFormEncoder extends FormEncoder {

    public SpringMultipartFormEncoder (Encoder delegate) {
        super(delegate);

        val processor = (MultipartFormContentProcessor) getContentProcessor(MULTIPART);
        processor.addFirstWriter(new SpringSingleMultipartFileWriter());
        processor.addFirstWriter(new SpringManyMultipartFilesWriter());
    }

    @Override
    public void encode (Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        if (bodyType.equals(MultipartFile[].class)) {
            val files = (MultipartFile[]) object;
            val data = new LinkedMultiValueMap<String, MultipartFile>(files.length);
            for (val file : files) {
                data.add(file.getName(), file);
            }
            super.encode(data, MAP_STRING_WILDCARD, template);
        } else if (bodyType.equals(MultipartFile.class)) {
            val file = (MultipartFile) object;
            val data = singletonMap(file.getName(), object);
            super.encode(data, MAP_STRING_WILDCARD, template);
        } else if (isMultipartFileCollection(object)) {
            val iterable = (Iterable<?>) object;
            val data = new HashMap<String, Object>();
            for (val item : iterable) {
                val file = (MultipartFile) item;
                data.put(file.getName(), file);
            }
            super.encode(data, MAP_STRING_WILDCARD, template);
        } else {
            super.encode(object, bodyType, template);
        }
    }

    private boolean isMultipartFileCollection (Object object) {
        if (!(object instanceof Iterable)) {
            return false;
        }
        val iterable = (Iterable<?>) object;
        val iterator = iterable.iterator();
        return iterator.hasNext() && iterator.next() instanceof MultipartFile;
    }
}