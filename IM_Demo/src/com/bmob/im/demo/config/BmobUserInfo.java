package com.bmob.im.demo.config;

import cn.bmob.im.BmobUserManager;

public class BmobUserInfo {
	private static BmobUserManager bmobUserManager=new BmobUserManager();
	private static String objectid;
	private static String name;
	public static BmobUserManager getBmobUserManager() {
		return bmobUserManager;
	}
	public static void setBmobUserManager(BmobUserManager bmobUserManager) {
		BmobUserInfo.bmobUserManager = bmobUserManager;
	}
	public static String getObjectid() {
		return objectid;
	}
	public static void setObjectid(String objectid) {
		BmobUserInfo.objectid = objectid;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		BmobUserInfo.name = name;
	}
	
}
