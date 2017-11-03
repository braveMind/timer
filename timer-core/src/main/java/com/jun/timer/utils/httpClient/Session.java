package com.jun.timer.utils.httpClient;


import com.jun.timer.utils.httpClient.service.HttpEntityProviderService;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

import java.io.IOException;
import java.util.Objects;

/**
 * function:
 * Created by jun
 * 16/11/8 下午5:44.
 */
public class Session {

    private HttpClient httpClient;
    private HttpClientContext context;
    private HttpRequest request;
    private HttpResponse httpResponse;
    private int httpCode;

    public HttpEntityProviderService getProviderService() {
        return providerService;
    }

    public void setProviderService(HttpEntityProviderService providerService) {
        this.providerService = providerService;
    }

    private HttpEntityProviderService providerService;

    public HttpRequest getRequest() {
        return request;
    }

    public HttpClientContext getContext() {
        return context;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;

    }


    public int getHttpCode() {
        return httpCode;
    }

    public ResponseUtils getRepUtils() {
        return repUtils;
    }

    public BasicCookieStore getCookies() {
        return cookies;
    }

    public void setRepUtils(ResponseUtils repUtils) {
        this.repUtils = repUtils;
    }

    private ResponseUtils repUtils;
    private BasicCookieStore cookies;

    public Session(HttpEntityProviderService providerService) {
        this.context = HttpClientContext.create();
        this.httpClient = new HttpConnectionManager().getHtpClient();
        this.cookies = new BasicCookieStore();
        this.context.setCookieStore(cookies);
        this.providerService = providerService;

    }


    public Session post(String url) {
        this.request = new HttpPost(url);
        return this;
    }

    public Session addHeader(String name, String value) {
        Objects.requireNonNull(this.request);
        this.request.addHeader(name, value);
        return this;
    }

    public Session addHeader(Header header) {
        Objects.requireNonNull(this.request);
        this.request.addHeader(header);
        return this;
    }

    public Session get(String url) {
        this.request = new HttpGet(url);
        return this;
    }

    public Session process() throws IOException {

        HttpRequest request = this.getRequest();
        Objects.requireNonNull(this.request);
        HttpClient httpClient = this.getHttpClient();
        HttpClientContext context = this.getContext();
        if (request instanceof HttpGet) {
            this.getContext().setCookieStore(cookies);
            HttpGet get = (HttpGet) request;
            this.httpResponse = httpClient.execute(get, context);
            this.httpCode = httpResponse.getStatusLine().getStatusCode();
            this.repUtils = new ResponseUtils(this.httpResponse);
        } else if (this.request instanceof HttpPost) {
            context.setCookieStore(cookies);
            HttpPost post = (HttpPost) request;
            post.setEntity(this.getProviderService().builder());
            this.httpResponse = this.httpClient.execute(post, this.context);
            this.httpCode = httpResponse.getStatusLine().getStatusCode();
            this.repUtils = new ResponseUtils(this.httpResponse);
        }
        return this;
    }


}
