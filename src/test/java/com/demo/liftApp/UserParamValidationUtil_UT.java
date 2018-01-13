package com.demo.liftApp;

import org.junit.Assert;
import org.junit.Test;

import com.demo.liftApp.util.UserDirectionEnum;
import com.demo.liftApp.util.UserTypeEnum;
import com.demo.liftApp.validator.UserParamValidator;
import com.demo.liftApp.vo.AppParameterVO;
import com.demo.liftApp.vo.UserParameterVO;

public class UserParamValidationUtil_UT {

	private UserParamValidator userParamValidator = new UserParamValidator();
	private AppParameterVO appParameterVO;
	private UserParameterVO userParameterVO;

	@Test
	public void testValidation_InvalidLiftNumber() {

		appParameterVO = new AppParameterVO(3L, 3L);
		userParameterVO = new UserParameterVO(UserTypeEnum.INSIDE.getUserType(), -3L, 4L);

		boolean result = false;

		try {
			userParamValidator.validate(appParameterVO, userParameterVO);
			Assert.fail("Validation should have failed!!");
		} catch (Exception exception) {
			Assert.assertEquals(false, result);
			Assert.assertEquals("Invalid parameter passed: Lift number -- -3", exception.getMessage());
		}

	}

	@Test
	public void testValidation_InvalidDestinationFloor() {

		appParameterVO = new AppParameterVO(4L, 3L);
		userParameterVO = new UserParameterVO(UserTypeEnum.INSIDE.getUserType(), 4L, -4L);

		boolean result = false;

		try {
			userParamValidator.validate(appParameterVO, userParameterVO);
			Assert.fail("Validation should have failed!!");
		} catch (Exception exception) {
			Assert.assertEquals(false, result);
			Assert.assertEquals("Invalid parameter passed: Destination floor -- -4", exception.getMessage());
		}
	}

	@Test
	public void testValidation_InvalidUserType() {

		appParameterVO = new AppParameterVO(4L, 3L);
		userParameterVO = new UserParameterVO("ZZ", 4L, 4L);

		boolean result = false;

		try {
			userParamValidator.validate(appParameterVO, userParameterVO);
			Assert.fail("Validation should have failed!!");
		} catch (Exception exception) {
			Assert.assertEquals(false, result);
			Assert.assertEquals("Invalid parameter passed: User Type -- ZZ", exception.getMessage());
		}
	}

	@Test
	public void testValidation_InvalidUserDestination() {

		appParameterVO = new AppParameterVO(4L, 3L);
		userParameterVO = new UserParameterVO(UserTypeEnum.OUTSIDE.getUserType(), "YY", 4L);

		boolean result = false;

		try {
			userParamValidator.validate(appParameterVO, userParameterVO);
			Assert.fail("Validation should have failed!!");
		} catch (Exception exception) {
			Assert.assertEquals(false, result);
			Assert.assertEquals("Invalid parameter passed: User Direction -- YY", exception.getMessage());
		}
	}

	@Test
	public void testValidation_InvalidDestinationFloor_GreaterThanTotalFloors() {

		appParameterVO = new AppParameterVO(4L, 3L);
		userParameterVO = new UserParameterVO(UserTypeEnum.INSIDE.getUserType(), 4L, 4L);

		boolean result = false;

		try {
			userParamValidator.validate(appParameterVO, userParameterVO);
			Assert.fail("Validation should have failed!!");
		} catch (Exception exception) {
			Assert.assertEquals(false, result);
			Assert.assertEquals("Invalid parameter passed: Destination floor -- 4 cannot be greater than total number of floors -- 3", exception.getMessage());
		}
	}

	@Test
	public void testValidation_InvalidLiftNumber_GreaterThanTotalLifts() {

		appParameterVO = new AppParameterVO(4L, 3L);
		userParameterVO = new UserParameterVO(UserTypeEnum.INSIDE.getUserType(), 5L, 4L);

		boolean result = false;

		try {
			userParamValidator.validate(appParameterVO, userParameterVO);
			Assert.fail("Validation should have failed!!");
		} catch (Exception exception) {
			Assert.assertEquals(false, result);
			Assert.assertEquals("Invalid parameter passed: Lift number -- 5 cannot be greater than total number of lifts -- 4", exception.getMessage());
		}
	}

	@Test
	public void testValidation_OutsideUser_Valid() {

		appParameterVO = new AppParameterVO(4L, 3L);
		userParameterVO = new UserParameterVO(UserTypeEnum.OUTSIDE.getUserType(), UserDirectionEnum.UPWARDS.getUserDirection(), 2L);

		boolean result = userParamValidator.validate(appParameterVO, userParameterVO);
		Assert.assertEquals(true, result);
	}

	@Test
	public void testValidation_OutsideUser_InvalidLiftNumber_GreaterThanTotalFloors() {

		appParameterVO = new AppParameterVO(4L, 3L);
		userParameterVO = new UserParameterVO(UserTypeEnum.OUTSIDE.getUserType(), UserDirectionEnum.UPWARDS.getUserDirection(), 4L);

		boolean result = false;

		try {
			userParamValidator.validate(appParameterVO, userParameterVO);
			Assert.fail("Validation should have failed!!");
		} catch (Exception exception) {
			Assert.assertEquals(false, result);
			Assert.assertEquals("Invalid parameter passed: Destination floor -- 4 cannot be greater than total number of floors -- 3", exception.getMessage());
		}
	}
}