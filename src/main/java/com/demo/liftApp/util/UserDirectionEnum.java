package com.demo.liftApp.util;

public enum UserDirectionEnum {

	UPWARDS("UP"), DOWNWARDS("DOWN");

	String userDirection;

	UserDirectionEnum(String userDirection) {
		this.userDirection = userDirection;
	}

	public String getUserDirection() {
		return userDirection;
	}
}
