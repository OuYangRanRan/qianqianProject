package com.bmob.im.demo.bean;

import java.util.Date;
import cn.bmob.v3.BmobObject;

public class RegistryTimeBean{
	private String name;
	private int count;
	private String createdAt;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String string) {
		this.createdAt = string;
	}
}
