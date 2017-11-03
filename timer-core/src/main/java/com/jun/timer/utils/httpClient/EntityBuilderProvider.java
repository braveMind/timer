package com.jun.timer.utils.httpClient;

import com.jun.timer.utils.httpClient.service.HttpEntityProviderService;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.util.Asserts;

/**
 * function:
 * Created by jun
 * 16/11/8 下午3:45.
 */

public class EntityBuilderProvider implements HttpEntityProviderService<EntityBuilder> {
    private EntityBuilder entityBuilder;


    public EntityBuilder provider() {
        this.entityBuilder= EntityBuilder.create();
        return entityBuilder;
    }

    public HttpEntity builder() {
        Asserts.notNull(this.entityBuilder, "entityBuilder should not be null");
        return this.entityBuilder.build();
    }
}
