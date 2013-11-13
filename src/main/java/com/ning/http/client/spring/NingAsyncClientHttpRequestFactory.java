package com.ning.http.client.spring;

import com.ning.http.client.AsyncHttpClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.AsyncClientHttpRequestFactory;

import java.io.IOException;
import java.net.URI;

public class NingAsyncClientHttpRequestFactory implements AsyncClientHttpRequestFactory, InitializingBean, DisposableBean {

    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    @Override
    public AsyncClientHttpRequest createAsyncRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return new NingAsyncClientHttpRequest(uri, httpMethod, asyncHttpClient);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void destroy() throws Exception {
        asyncHttpClient.close();
    }

}
