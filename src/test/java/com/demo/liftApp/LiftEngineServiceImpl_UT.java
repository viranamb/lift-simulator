package com.demo.liftApp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.demo.liftApp.entity.Lift;
import com.demo.liftApp.service.LiftEngineService;
import com.demo.liftApp.service.impl.LiftEngineServiceImpl;
import com.demo.liftApp.util.UserDirectionEnum;
import com.demo.liftApp.util.UserTypeEnum;
import com.demo.liftApp.vo.AppParameterVO;
import com.demo.liftApp.vo.UserParameterVO;

@RunWith(JUnit4.class)
public class LiftEngineServiceImpl_UT {

	private ConcurrentMap<Long, Lift> liftNumberMap;

	private BlockingQueue<Lift> availableLiftQueue;

	private AppParameterVO appParameterVO = new AppParameterVO(4L, 4L);

	private LiftEngineService liftEngineService;

	@Before
	public void intialize() {
		Lift lift1 = new Lift(1L, 1L, 1L);
		Lift lift2 = new Lift(2L, 2L, 1L);
		Lift lift3 = new Lift(3L, 3L, 1L);
		Lift lift4 = new Lift(4L, 4L, 1L);

		liftNumberMap = new ConcurrentHashMap<Long, Lift>();
		liftNumberMap.put(1L, lift1);
		liftNumberMap.put(2L, lift2);
		liftNumberMap.put(3L, lift3);
		liftNumberMap.put(4L, lift4);

		availableLiftQueue = new LinkedBlockingQueue<Lift>();
		availableLiftQueue.add(lift1);
		availableLiftQueue.add(lift2);
		availableLiftQueue.add(lift3);
		availableLiftQueue.add(lift4);

		liftEngineService = new LiftEngineServiceImpl(liftNumberMap, availableLiftQueue);
	}

	@After
	public void destroy() {
		liftNumberMap = null;
		availableLiftQueue = null;
		liftEngineService = null;
	}

	@Test
	public void testLiftOperation_UserInside_Valid_LowermostFloorAsDestination() {

		UserParameterVO userParameterVO = new UserParameterVO(UserTypeEnum.INSIDE.getUserType(), 1L, 3L);

		boolean result = liftEngineService.invokeLiftOperation(appParameterVO, userParameterVO);

		Assert.assertEquals(true, result);
	}

	@Test
	public void testLiftOperation_UserInside_Valid_MiddleFloorAsDestination() {

		UserParameterVO userParameterVO = new UserParameterVO(UserTypeEnum.INSIDE.getUserType(), 2L, 2L);

		boolean result = liftEngineService.invokeLiftOperation(appParameterVO, userParameterVO);

		Assert.assertEquals(true, result);
	}

	@Test
	public void testLiftOperation_UserInside_Valid_TopmostFloorAsDestination() {

		UserParameterVO userParameterVO = new UserParameterVO(UserTypeEnum.INSIDE.getUserType(), 1L, 2L);

		boolean result = liftEngineService.invokeLiftOperation(appParameterVO, userParameterVO);

		Assert.assertEquals(true, result);
	}

	@Test
	public void testLiftOperation_UserOutside_Upwards_Valid() {

		UserParameterVO userParameterVO = new UserParameterVO(UserTypeEnum.OUTSIDE.getUserType(), UserDirectionEnum.UPWARDS.getUserDirection(), 3L);

		boolean result = liftEngineService.invokeLiftOperation(appParameterVO, userParameterVO);

		Assert.assertEquals(true, result);
	}

	@Test
	public void testLiftOperation_UserOutside_Downwards_Valid_UserOnLowermostFloor() {

		UserParameterVO userParameterVO = new UserParameterVO(UserTypeEnum.OUTSIDE.getUserType(), UserDirectionEnum.DOWNWARDS.getUserDirection(), 1L);

		boolean result = liftEngineService.invokeLiftOperation(appParameterVO, userParameterVO);

		Assert.assertEquals(true, result);
	}

	@Test
	public void testLiftOperation_UserOutside_Downwards_Valid_UserOnMiddleFloor() {

		UserParameterVO userParameterVO = new UserParameterVO(UserTypeEnum.OUTSIDE.getUserType(), UserDirectionEnum.DOWNWARDS.getUserDirection(), 2L);

		boolean result = liftEngineService.invokeLiftOperation(appParameterVO, userParameterVO);

		Assert.assertEquals(true, result);
	}

	@Test
	public void testLiftOperation_UserOutside_Downwards_Valid_UserOnTopmostFloor() {

		UserParameterVO userParameterVO = new UserParameterVO(UserTypeEnum.OUTSIDE.getUserType(), UserDirectionEnum.UPWARDS.getUserDirection(), 4L);

		boolean result = liftEngineService.invokeLiftOperation(appParameterVO, userParameterVO);

		Assert.assertEquals(true, result);
	}

}