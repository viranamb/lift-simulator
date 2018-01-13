package com.demo.liftApp.vo;

public class UserParameterVO {

	private String userType;
	private Long userLiftNumber;
	private String userDirection;
	private Long liftDestinationFloor;

	public UserParameterVO() {
		super();
	}

	public UserParameterVO(String userType, Long liftDestinationFloor) {
		super();
		this.userType = userType;
		this.liftDestinationFloor = liftDestinationFloor;
	}

	public UserParameterVO(String userType, Long userLiftNumber, Long liftDestinationFloor) {
		super();
		this.userType = userType;
		this.userLiftNumber = userLiftNumber;
		this.liftDestinationFloor = liftDestinationFloor;
	}

	public UserParameterVO(String userType, String userDirection, Long liftDestinationFloor) {
		this.userType = userType;
		this.userDirection = userDirection;
		this.liftDestinationFloor = liftDestinationFloor;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getLiftDestinationFloor() {
		return liftDestinationFloor;
	}

	public void setLiftDestinationFloor(Long liftDestinationFloor) {
		this.liftDestinationFloor = liftDestinationFloor;
	}

	public Long getUserLiftNumber() {
		return userLiftNumber;
	}

	public void setUserLiftNumber(Long userLiftNumber) {
		this.userLiftNumber = userLiftNumber;
	}

	public String getUserDirection() {
		return userDirection;
	}

	public void setUserDirection(String userDirection) {
		this.userDirection = userDirection;
	}

}
