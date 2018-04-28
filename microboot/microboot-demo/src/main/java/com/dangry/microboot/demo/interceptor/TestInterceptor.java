package com.dangry.microboot.demo.interceptor;

import com.dangry.microboot.http.HttpContextRequest;
import com.dangry.microboot.http.HttpContextResponse;
import com.dangry.microboot.mvc.ApiMethodMapping;
import com.dangry.microboot.mvc.interceptor.AbstractApiInterceptor;

public class TestInterceptor extends AbstractApiInterceptor {
    @Override
    public boolean preDispatch(HttpContextRequest request, HttpContextResponse response) {
        System.out.println("TestInterceptor preDispatch url="+request.getRequestUrl());
        return super.preDispatch(request, response);
    }

    @Override
    public boolean postHandler(ApiMethodMapping mapping, HttpContextRequest request, HttpContextResponse response) {
        System.out.println("TestInterceptor postHandler url="+request.getRequestUrl());
        return super.postHandler(mapping, request, response);
    }

    @Override
    public void afterHandle(ApiMethodMapping mapping, Object modelView, HttpContextRequest request, HttpContextResponse response, Throwable throwable) {
        System.out.println("TestInterceptor afterHandle url="+request.getRequestUrl());
        super.afterHandle(mapping, modelView, request, response, throwable);
    }
}
