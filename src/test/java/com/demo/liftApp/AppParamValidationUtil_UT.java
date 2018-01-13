package com.demo.liftApp;

import org.junit.Assert;
import org.junit.Test;

import com.demo.liftApp.validator.AppParamValidator;
import com.demo.liftApp.vo.AppParameterVO;

public class AppParamValidationUtil_UT {

	private AppParamValidator appParamValidator = new AppParamValidator();
	private AppParameterVO appParameterVO;

	@Test
	public void testValidation_ValidInput() {
		appParameterVO = new AppParameterVO(4L, 3L);

		boolean result = appParamValidator.validate(appParameterVO);

		Assert.assertEquals(true, result);
	}

	@Test
	public void testValidation_InvalidNumberOfFloors() {
		appParameterVO = new AppParameterVO(null, null);

		boolean result = false;

		try {
			appParamValidator.validate(appParameterVO);
			Assert.fail("Validation should have failed!!");
		} catch (Exception exception) {
			Assert.assertEquals(false, result);
			Assert.assertEquals("Invalid parameter passed: Number of lifts -- null", exception.getMessage());
		}
	}

	@Test
	public void testValidation_InvalidNumberOfLifts() {
		appParameterVO = new AppParameterVO(3L, -3L);

		boolean result = false;

		try {
			appParamValidator.validate(appParameterVO);
			Assert.fail("Validation should have failed!!");
		} catch (Exception exception) {
			Assert.assertEquals(false, result);
			Assert.assertEquals("Invalid parameter passed: Number of floors -- -3", exception.getMessage());
		}

	}

}