package com.dangry.microboot.mvc.resolver.exception;

import com.dangry.microboot.constants.Constants;
import com.dangry.microboot.http.HttpContextRequest;
import com.dangry.microboot.http.HttpContextResponse;
import com.dangry.microboot.mvc.ApiMethodMapping;
import com.dangry.microboot.mvc.resolver.ExceptionResolver;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

public class DefaultExceptionResolver implements ExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(DefaultExceptionResolver.class);
    @Override
    public void resolveException(ApiMethodMapping mapping, HttpContextRequest request, HttpContextResponse response, Throwable ex) {
        if((ex instanceof IllegalArgumentException) && ex.getMessage()!=null){
            response.setStatus(HttpResponseStatus.BAD_REQUEST);
            logger.warn("reqId="+request.getAttachment(Constants.REQ_ID), ex);
        }else{
            response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
            logger.error("reqId="+request.getAttachment(Constants.REQ_ID), ex);
        }
        try {
            String msg = ex.getMessage();
            if (null == msg) {
                msg = ex.toString();
            }
            response.setContent(msg);
        } catch (Exception e) {
            response.setContent(String.valueOf(e));
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
