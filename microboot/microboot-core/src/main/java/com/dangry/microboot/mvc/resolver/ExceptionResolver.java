package com.dangry.microboot.mvc.resolver;

import com.dangry.microboot.http.HttpContextRequest;
import com.dangry.microboot.http.HttpContextResponse;
import com.dangry.microboot.mvc.ApiMethodMapping;
import org.springframework.core.Ordered;

public interface ExceptionResolver extends Ordered{
    void resolveException(ApiMethodMapping mapping, HttpContextRequest request, HttpContextResponse response, Throwable ex);
}
