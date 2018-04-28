package com.dangry.microboot.mvc.resolver.param;

import com.dangry.microboot.mvc.ApiMethodParam;
import com.dangry.microboot.http.HttpContextRequest;
import com.dangry.microboot.http.HttpContextResponse;

public class ApiMethodParamCommandContextResolver extends AbstractApiMethodParamResolver {

    @Override
    public boolean support(ApiMethodParam apiMethodParam) {
        return apiMethodParam.getParamType().equals(HttpContextRequest.class);
    }

    @Override
    public Object getParamObject(ApiMethodParam apiMethodParam, HttpContextRequest context, HttpContextResponse response) {
        if (support(apiMethodParam)) {
            return context;
        }
        return null;
    }
}
