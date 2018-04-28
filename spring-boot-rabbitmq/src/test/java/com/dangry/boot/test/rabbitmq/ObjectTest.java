package com.dangry.boot.test.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dangry.boot.model.User;
import com.dangry.boot.rabbit.object.ObjectSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectTest {

	@Autowired
	private ObjectSender sender;

	@Test
	public void sendOject() throws Exception {
		User user=new User();
		user.setName("neo");
		user.setPass("123456");
		sender.send(user);
	}

}