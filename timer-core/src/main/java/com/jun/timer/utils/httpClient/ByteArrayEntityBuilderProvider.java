package com.jun.timer.utils.httpClient;

import com.jun.timer.utils.httpClient.service.HttpEntityProviderService;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created by jun
 * 17/6/8 下午3:07.
 * des:发送 requestBody
 */
public class ByteArrayEntityBuilderProvider implements HttpEntityProviderService<ByteArrayEntity> {
    private ByteArrayEntity httpEntity;

    public ByteArrayEntityBuilderProvider(String requestBody) {
        try {
            this.httpEntity = new ByteArrayEntity(requestBody.getBytes("UTF-8"));
            httpEntity.setContentType("application/json");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ByteArrayEntity provider() {
        return this.httpEntity;
    }

    @Override
    public HttpEntity builder() {
        return this.httpEntity;
    }

    ;
}
