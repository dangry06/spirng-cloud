package com.dangry.microboot.mvc;

import com.dangry.microboot.http.MediaType;

public class ModelAndView {
    //viewName
    protected String viewName;
    protected Object result;
    protected MediaType mediaType;
    public ModelAndView(){}

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public ModelAndView(String viewName){
        this.viewName = viewName;
    }
    public String getViewName(){
        return this.viewName;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
