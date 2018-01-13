package com.demo.liftApp.validator;

import java.util.regex.Pattern;

import com.demo.liftApp.util.UserDirectionEnum;
import com.demo.liftApp.util.UserTypeEnum;
import com.demo.liftApp.vo.AppParameterVO;
import com.demo.liftApp.vo.UserParameterVO;

public class UserParamValidator {

	public boolean validate(AppParameterVO appParameterVO, UserParameterVO userParameterVO) {

		String userDirection = userParameterVO.getUserDirection();
		String userType = userParameterVO.getUserType();
		Long liftDestinationFloor = userParameterVO.getLiftDestinationFloor();
		Long userLiftNumber = userParameterVO.getUserLiftNumber();

		Long totalNumberOfFloors = appParameterVO.getNumberOfFloors();
		Long totalNumberOfLifts = appParameterVO.getNumberOfLifts();

		Pattern pattern = Pattern.compile("[1-9]+");

		if (!pattern.matcher(liftDestinationFloor + "").matches()) {
			throw new IllegalArgumentException("Invalid parameter passed: Destination floor -- " + liftDestinationFloor);
		}

		if (UserTypeEnum.INSIDE.getUserType().equalsIgnoreCase(userType) && !pattern.matcher(userLiftNumber + "").matches()) {
			throw new IllegalArgumentException("Invalid parameter passed: Lift number -- " + userLiftNumber);
		}

		if (!(UserTypeEnum.INSIDE.getUserType().equalsIgnoreCase(userType) || UserTypeEnum.OUTSIDE.getUserType().equalsIgnoreCase(userType))) {
			throw new IllegalArgumentException("Invalid parameter passed: User Type -- " + userType);
		}

		if (UserTypeEnum.OUTSIDE.getUserType().equalsIgnoreCase(userType)
				&& !((UserDirectionEnum.UPWARDS.getUserDirection().equalsIgnoreCase(userDirection + "".trim())) || (UserDirectionEnum.DOWNWARDS.getUserDirection()
						.equalsIgnoreCase(userDirection + "".trim())))) {
			throw new IllegalArgumentException("Invalid parameter passed: User Direction -- " + userDirection);
		}

		if (UserTypeEnum.INSIDE.getUserType().equalsIgnoreCase(userType) && userLiftNumber > totalNumberOfLifts) {
			throw new IllegalArgumentException("Invalid parameter passed: Lift number -- " + userLiftNumber + " cannot be greater than total number of lifts -- "
					+ totalNumberOfLifts);
		}

		if (liftDestinationFloor > totalNumberOfFloors) {
			throw new IllegalArgumentException("Invalid parameter passed: Destination floor -- " + liftDestinationFloor + " cannot be greater than total number of floors -- "
					+ totalNumberOfFloors);
		}

		return true;
	}
}
