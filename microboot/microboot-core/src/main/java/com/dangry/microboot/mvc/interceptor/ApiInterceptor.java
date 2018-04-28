package com.dangry.microboot.mvc.interceptor;

import com.dangry.microboot.mvc.ApiMethodMapping;
import com.dangry.microboot.http.HttpContextRequest;
import com.dangry.microboot.http.HttpContextResponse;
import org.springframework.core.Ordered;

/**
 * api拦截器
 */
public interface ApiInterceptor extends Ordered{
    /**
     * 作用于url匹配分发之前，甚至可以url以更改invoke的method
     * @param request
     * @param response
     * @return
     */
    boolean preDispatch(HttpContextRequest request, HttpContextResponse response);

    /**
     * 作用于invoke之前，可以更改请求参数的值，
     * @param mapping
     * @param request
     * @param response
     */
    boolean postHandler(ApiMethodMapping mapping, HttpContextRequest request, HttpContextResponse response);

    /**
     * 作用于invoke之后，可以更改返回值
     * @param mapping
     * @param modelView
     * @param request
     * @param response
     * @param throwable
     */
    void afterHandle(ApiMethodMapping mapping, Object modelView, HttpContextRequest request, HttpContextResponse response, Throwable throwable);

    /**
     * 全部完成
     *
     * @param mapping
     * @param request
     * @param response
     */
    void afterCompletion(ApiMethodMapping mapping, HttpContextRequest request, HttpContextResponse response, Throwable throwable);

}
