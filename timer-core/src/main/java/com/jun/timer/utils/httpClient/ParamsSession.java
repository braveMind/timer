package com.jun.timer.utils.httpClient;




import com.jun.timer.utils.httpClient.service.HttpEntityProviderService;
import com.jun.timer.utils.httpClient.service.SessionService;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by jun on 16/7/2.
 */
public class ParamsSession extends Session implements SessionService {


    public ParamsSession(HttpEntityProviderService<EntityBuilder> providerService) {
        super(providerService);
    }

    public final ParamsSession addParams(Map paramsMap) throws UnsupportedEncodingException {
        Objects.requireNonNull(paramsMap);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        EntityBuilder entityBuilder= (EntityBuilder) this.getProviderService().provider();
        entityBuilder.setParameters(nameValuePairs);
        return this;
    }


}






