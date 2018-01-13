package com.demo.liftApp.invoker;

import java.util.concurrent.Callable;

import com.demo.liftApp.service.LiftEngineService;
import com.demo.liftApp.vo.AppParameterVO;
import com.demo.liftApp.vo.UserParameterVO;

public class LiftEngineCallable implements Callable<Boolean> {

	private AppParameterVO appParameterVO;
	private UserParameterVO userParameterVO;
	private LiftEngineService liftEngineService;

	public LiftEngineCallable(AppParameterVO appParameterVO, UserParameterVO userParameterVO, LiftEngineService liftEngineService) {
		this.appParameterVO = appParameterVO;
		this.userParameterVO = userParameterVO;
		this.liftEngineService = liftEngineService;
	}

	@Override
	public Boolean call() throws Exception {
		return liftEngineService.invokeLiftOperation(appParameterVO, userParameterVO);
	}

}