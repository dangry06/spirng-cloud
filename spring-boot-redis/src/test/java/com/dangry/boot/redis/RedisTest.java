package com.dangry.boot.redis;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.dangry.boot.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void test() throws Exception {
		stringRedisTemplate.opsForValue().set("aaa", "111");
		System.out.println("get key from redis : " + stringRedisTemplate.opsForValue().get("aaa"));
		Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
	}

	@Test
	public void testObj() throws Exception {
		User user = new User();
		user.setEmail("duanyong@qq.com");
		user.setId(1L);
		user.setNickName("小墨鱼");
		user.setPassWord("1234567890");
		user.setRegTime("2018-04-27 17:30:00");
		user.setUserName("dangry");

		System.out.println(redisTemplate);
		ValueOperations<String, User> operations = redisTemplate.opsForValue();
		operations.set("com.dangry", user);
		System.out.println(operations.get("com.dangry"));
		operations.set("com.dangry.f", user, 1, TimeUnit.SECONDS);
		//Thread.sleep(1000);
		// redisTemplate.delete("com.neo.f");
//		boolean exists = redisTemplate.hasKey("com.dangry.f");
//		if (exists) {
//			System.out.println("exists is true");
//		} else {
//			System.out.println("exists is false");
//		}
		// Assert.assertEquals("aa", operations.get("com.dangry.f").getUserName());
	}

}