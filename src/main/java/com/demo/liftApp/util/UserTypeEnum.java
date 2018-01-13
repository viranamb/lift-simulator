package com.demo.liftApp.util;

public enum UserTypeEnum {

	INSIDE("IN"), OUTSIDE("OUT");

	String userType;

	UserTypeEnum(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return userType;
	}
}
