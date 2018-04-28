package com.dangry.microboot.mvc.resolver.view;

import com.dangry.microboot.http.HttpContextRequest;
import com.dangry.microboot.http.HttpContextResponse;
import com.dangry.microboot.http.MediaType;
import com.dangry.microboot.mvc.ModelAndView;
import com.dangry.microboot.mvc.resolver.ViewResolver;

public class StringViewResolver extends ViewResolver {
    @Override
    public ModelAndView resolve(Object result) {
        if (result instanceof ModelAndView) {
            ModelAndView mv = (ModelAndView)result;
            if (mv.getResult() instanceof String) {
                return mv;
            }else if(MediaType.TEXT_PLAIN.isCompatibleWith(mv.getMediaType())){
                return mv;
            }
        }else if(result instanceof String){
            ModelAndView mv = new ModelAndView();
            mv.setResult(result);
            return mv;
        }
        return null;
    }

    @Override
    public void render(ModelAndView mv, HttpContextRequest request, HttpContextResponse response) throws Exception {
        if(null != mv.getResult()) {
            response.setContent(mv.getResult().toString());
        }
        if (null == getContentType()) {
            setContentType(MediaType.TEXT_PLAIN_VALUE);
        }
    }
}
