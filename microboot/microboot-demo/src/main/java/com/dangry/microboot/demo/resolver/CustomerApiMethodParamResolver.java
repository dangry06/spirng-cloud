package com.dangry.microboot.demo.resolver;

import com.dangry.microboot.http.HttpContextRequest;
import com.dangry.microboot.http.HttpContextResponse;
import com.dangry.microboot.mvc.ApiMethodParam;
import com.dangry.microboot.mvc.resolver.param.AbstractApiMethodParamResolver;

public class CustomerApiMethodParamResolver extends AbstractApiMethodParamResolver {
    @Override
    public boolean support(ApiMethodParam apiMethodParam) {
        return apiMethodParam.getParamType().equals(CustomerParam.class);
    }

    @Override
    public Object getParamObject(ApiMethodParam apiMethodParam, HttpContextRequest request, HttpContextResponse response) throws Exception {
        CustomerParam param = new CustomerParam();
        param.customer = "哈哈，customer";
        return param;
    }
}
