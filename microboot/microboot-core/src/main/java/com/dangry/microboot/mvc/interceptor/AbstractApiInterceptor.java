package com.dangry.microboot.mvc.interceptor;

import com.dangry.microboot.http.HttpContextRequest;
import com.dangry.microboot.mvc.ApiMethodMapping;
import com.dangry.microboot.http.HttpContextResponse;
import org.springframework.core.Ordered;

public abstract class AbstractApiInterceptor implements ApiInterceptor,Ordered {
    protected int order = Ordered.LOWEST_PRECEDENCE + 100;
    @Override
    public boolean preDispatch(HttpContextRequest request, HttpContextResponse response) {
        return true;
    }

    @Override
    public boolean postHandler(ApiMethodMapping mapping, HttpContextRequest request, HttpContextResponse response) {
        return true;
    }

    @Override
    public void afterHandle(ApiMethodMapping mapping, Object modelView, HttpContextRequest request, HttpContextResponse response, Throwable throwable) {
    }
    /**
     * 全部完成
     *
     * @param mapping
     * @param request
     * @param response
     */
    @Override
    public void afterCompletion(ApiMethodMapping mapping, HttpContextRequest request, HttpContextResponse response, Throwable throwable){
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }
}
