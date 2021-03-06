package com.dangry.boot.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dangry.boot.model.User;

@RestController
public class UserController {
	
    @RequestMapping("/getUser")
    @Cacheable(value = "userCache")
    public User getUser() {
    	User user = new User();
    	user.setEmail("duanyong@qq.com");
    	user.setId(1L);
    	user.setNickName("小墨鱼");
    	user.setPassWord("1234567890");
    	user.setRegTime("2018-04-27 17:30:00");
    	user.setUserName("dangry");
    	System.out.println("缓存中获取失败，新建对象并放入缓存");  
        return user;
    }
    
    @RequestMapping("/getUser1")
    @Cacheable(value = "userCache")
    public User getUser1() {
    	User user = new User();
    	user.setEmail("duanyong@qq.com");
    	user.setId(1L);
    	user.setNickName("小墨鱼");
    	user.setPassWord("1234567890");
    	user.setRegTime("2018-04-27 17:30:00");
    	user.setUserName("dangry");
    	System.out.println("缓存中获取失败，新建对象并放入缓存");  
        return user;
    }
    
    @RequestMapping("/getUser2")
    @Cacheable(value = "otherCache")
    public User getUser2() {
    	User user = new User();
    	user.setEmail("duanyong@qq.com");
    	user.setId(1L);
    	user.setNickName("小墨鱼");
    	user.setPassWord("1234567890");
    	user.setRegTime("2018-04-27 17:30:00");
    	user.setUserName("dangry");
    	System.out.println("缓存中获取失败，新建对象并放入缓存");  
        return user;
    }
    
    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}