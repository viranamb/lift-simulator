package com.demo.liftApp.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.demo.liftApp.AppParamValidationUtil_UT;
import com.demo.liftApp.LiftEngineCallable_UT;
import com.demo.liftApp.LiftEngineServiceImpl_UT;
import com.demo.liftApp.UserParamValidationUtil_UT;

@RunWith(Suite.class)
@SuiteClasses({ LiftEngineServiceImpl_UT.class, LiftEngineCallable_UT.class, UserParamValidationUtil_UT.class, AppParamValidationUtil_UT.class })
public class TestSuite {

}
