package com.demo.liftApp;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.demo.liftApp.invoker.LiftEngineCallable;
import com.demo.liftApp.service.LiftEngineService;
import com.demo.liftApp.vo.AppParameterVO;
import com.demo.liftApp.vo.UserParameterVO;

@RunWith(MockitoJUnitRunner.class)
public class LiftEngineCallable_UT {

	@Mock
	private LiftEngineService liftEngineService;

	@Mock
	private AppParameterVO appParameterVO;

	@Mock
	private UserParameterVO userParameterVO;

	@InjectMocks
	private LiftEngineCallable liftEngineCallable = new LiftEngineCallable(appParameterVO, userParameterVO, liftEngineService);

	@Test
	public void testCall_Success() throws Exception {
		Mockito.when(liftEngineService.invokeLiftOperation(appParameterVO, userParameterVO)).thenReturn(true);

		boolean result = liftEngineCallable.call();

		Assert.assertEquals(true, result);
	}

	@Test
	public void testCall_Failure() throws Exception {
		Mockito.when(liftEngineService.invokeLiftOperation(appParameterVO, userParameterVO)).thenReturn(false);

		boolean result = liftEngineCallable.call();

		Assert.assertEquals(false, result);
	}

	@Test
	public void testCall_ThrowsException() {
		Mockito.when(liftEngineService.invokeLiftOperation(appParameterVO, userParameterVO)).thenThrow(new NullPointerException("Null value found"));

		boolean result = false;
		try {
			result = liftEngineCallable.call();
			Assert.fail("Service was supposed to have thrown exception!!");
		} catch (Exception exception) {
			Assert.assertEquals(false, result);
			Assert.assertEquals(exception.getMessage(), "Null value found");
		}
	}

}