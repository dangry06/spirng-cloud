package com.dangry.microboot.demo.command;

import com.dangry.microboot.demo.resolver.CustomerParam;
import com.dangry.microboot.http.HttpContextRequest;
import com.dangry.microboot.http.HttpContextResponse;
import com.dangry.microboot.mvc.ModelAndView;
import com.dangry.microboot.mvc.annotation.*;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ApiCommand
public class ApiCommandTest{
    @ApiMethod("/t1")
    public ModelAndView test1(@ApiParam("name")String name, @ApiParam("age")int age,HttpContextResponse response){

        ModelAndView mv = new ModelAndView("jsonView");
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("a","我的家");
        res.put("b", new Date());
        res.put("name", name);
        res.put("age", age);
        mv.setResult(res);
        return mv;
    }

    @ApiMethod(value = "/t2",httpMethod = ApiMethod.HttpMethod.GET)
    public ModelAndView test2(){
        ModelAndView mv = new ModelAndView("jsonView");
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("Hello","这是一");
        res.put("gaga", new Date());
        mv.setResult(res);
        return mv;
    }

    @ApiMethod("/t3")
    public String t3() {
        return "狮驼岭";
    }

    @ApiMethod("/detail/{id}")
    public ModelAndView detail(@ApiPathVariable("id")int id, String abc, @ApiParam("name")String name){
        ModelAndView mv = new ModelAndView("jsonView");
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("Hello","这是一");
        res.put("gaga", new Date());
        res.put("detailId", id);
        res.put("name", name);
        mv.setResult(res);
        return mv;
    }

    @ApiMethod("/body/{id}")
    public String body(@ApiPathVariable("id")int id,@ApiRequestBody String body){
        return body + "\n" + id;
    }

    @ApiMethod("cus")
    public String cus(CustomerParam param) {
        return param.customer;
    }

    @ApiMethod("void")
    public void voidm(HttpContextRequest request, HttpContextResponse response) {
        response.setStatus(HttpResponseStatus.METHOD_NOT_ALLOWED);
    }
    @ApiMethod("download")
    public void download(HttpContextRequest request, HttpContextResponse response){
        try {
            File file = new File("/Users/wwj/test.txt");
            response.addHeader("content-type", "text/html");
            FileInputStream fileInputStream = new FileInputStream(file);
            response.setFile(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @ApiMethod("body")
    public String body(@ApiParam("p1") String p1, @ApiRequestBody String body) {
        return "p1="+p1 + ",body=" + body;
    }
}
