package com.bmob.im.demo.bean;


import com.baidu.mapapi.model.inner.GeoPoint;

import cn.bmob.push.a.This;
import cn.bmob.v3.BmobObject;
import android.location.Location;

public class RegistryBean extends BmobObject{
//	public RegistryBean() {
//		this.setTableName("RegistryBean");
//	}
	private String name;
	private GeoPoint location;
	public GeoPoint getLocation() {
		return location;
	}
	public void setLocation(GeoPoint location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
