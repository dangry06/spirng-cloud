package com.ladon.miguapp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AndroidVersion {
	
	public static String randomVersion() {
		int index = random.nextInt(list.size());
		return list.get(index);
	}
	
	private static final Random random = new Random();
	private static final List<String> list = new ArrayList<String>();
	static {
		list.add("4.1");
		list.add("4.2");
		list.add("4.2.2");
		list.add("4.3");
		list.add("4.4");
		list.add("5.0");
		list.add("5.1");
		list.add("6.0");
		list.add("6.0.1");
		list.add("6.1");
		list.add("7.0");
		list.add("7.1");
	}
}
