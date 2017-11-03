package com.jun.timer.utils.httpClient;

import com.jun.timer.utils.httpClient.service.HttpEntityProviderService;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.Asserts;

/**
 * function:
 * Created by jun
 * 16/11/8 下午3:55.
 */
public class MultipartEntityBuilderProvider implements HttpEntityProviderService<MultipartEntityBuilder> {
    private MultipartEntityBuilder multipartEntityBuilder;

    public MultipartEntityBuilder provider() {
        this.multipartEntityBuilder = MultipartEntityBuilder.create();
        return multipartEntityBuilder;
    }

    public HttpEntity builder() {
        Asserts.notNull(multipartEntityBuilder, "entityBuilder should not be null");
        return this.multipartEntityBuilder.build();
    }
}
