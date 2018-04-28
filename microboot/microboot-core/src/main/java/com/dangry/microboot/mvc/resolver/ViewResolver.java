package com.dangry.microboot.mvc.resolver;

import com.dangry.microboot.http.HttpContextRequest;
import com.dangry.microboot.http.HttpContextResponse;
import com.dangry.microboot.mvc.ModelAndView;
import org.springframework.core.Ordered;

public abstract class ViewResolver implements Ordered {
    protected int order = Ordered.LOWEST_PRECEDENCE;
    protected String contentType;
    /**
     * 是否支持渲染
     * @param result
     */
    public abstract ModelAndView resolve(Object result);

    /**
     * 渲染
     * @param mv
     * @param request
     * @param response
     * @return
     */
    public abstract void render(ModelAndView mv, HttpContextRequest request, HttpContextResponse response) throws Exception;

    @Override
    public int getOrder() {
        return order;
    }

    public String getContentType(){
        return contentType;
    }

    public void setOrder(int order) {
        this.order = order;
    }
    public void setContentType(String contentType){
        this.contentType = contentType;
    }
}
