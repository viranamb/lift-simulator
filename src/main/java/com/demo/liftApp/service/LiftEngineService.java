package com.demo.liftApp.service;

import com.demo.liftApp.vo.AppParameterVO;
import com.demo.liftApp.vo.UserParameterVO;

public interface LiftEngineService {

	public boolean invokeLiftOperation(AppParameterVO appParameterVO, UserParameterVO user);
}
