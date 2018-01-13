package com.demo.liftApp.vo;

public class AppParameterVO {

	public final Long numberOfLifts;
	public final Long numberOfFloors;
	public Long maxNumberOfInputs = 5L;

	public AppParameterVO(Long numberOfLifts, Long numberOfFloors) {
		super();
		this.numberOfLifts = numberOfLifts;
		this.numberOfFloors = numberOfFloors;
	}

	public Long getNumberOfFloors() {
		return numberOfFloors;
	}

	public Long getNumberOfLifts() {
		return numberOfLifts;
	}

	public Long getMaxNumberOfInputs() {
		return maxNumberOfInputs;
	}

	public void setMaxNumberOfInputs(Long maxNumberOfInputs) {
		this.maxNumberOfInputs = maxNumberOfInputs;
	}

}
