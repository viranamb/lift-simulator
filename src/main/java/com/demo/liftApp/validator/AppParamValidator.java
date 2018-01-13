package com.demo.liftApp.validator;

import java.util.regex.Pattern;

import com.demo.liftApp.vo.AppParameterVO;

public class AppParamValidator {

	public boolean validate(AppParameterVO appParameterVO) {

		Long totalNumberOfFloors = appParameterVO.getNumberOfFloors();
		Long totalNumberOfLifts = appParameterVO.getNumberOfLifts();

		Pattern pattern = Pattern.compile("[1-9]+");

		if (!pattern.matcher(totalNumberOfLifts + "").matches()) {
			throw new IllegalArgumentException("Invalid parameter passed: Number of lifts -- " + totalNumberOfLifts);
		}

		if (!pattern.matcher(totalNumberOfFloors + "").matches()) {
			throw new IllegalArgumentException("Invalid parameter passed: Number of floors -- " + totalNumberOfFloors);
		}

		return true;
	}
}
