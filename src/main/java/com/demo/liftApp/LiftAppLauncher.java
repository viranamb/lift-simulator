package com.demo.liftApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.liftApp.invoker.LiftEngineCallable;
import com.demo.liftApp.service.LiftEngineService;
import com.demo.liftApp.service.impl.LiftEngineServiceImpl;
import com.demo.liftApp.util.UserTypeEnum;
import com.demo.liftApp.validator.AppParamValidator;
import com.demo.liftApp.validator.UserParamValidator;
import com.demo.liftApp.vo.AppParameterVO;
import com.demo.liftApp.vo.UserParameterVO;

public class LiftAppLauncher {

	private final static Logger LOG = Logger.getLogger(LiftAppLauncher.class);

	public static void main(String[] args) {

		Scanner userInputScanner = new Scanner(System.in);
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("./application-context.xml");
			LiftEngineService liftEngineService = (LiftEngineServiceImpl) applicationContext.getBean("liftEngineService");
			AppParamValidator appParamValidator = (AppParamValidator) applicationContext.getBean("appParamValidator");
			UserParamValidator userParamValidator = (UserParamValidator) applicationContext.getBean("userParamValidator");

			AppParameterVO appParameterVO = fetchAppParameters(args, appParamValidator);

			List<UserParameterVO> userInputParameterList = fetchUserInputParameterList(userInputScanner, appParameterVO, userParamValidator);

			List<LiftEngineCallable> liftEngineCallableList = new ArrayList<LiftEngineCallable>();
			for (UserParameterVO userParameterVO : userInputParameterList) {
				liftEngineCallableList.add(new LiftEngineCallable(appParameterVO, userParameterVO, liftEngineService));
			}

			List<Future<Boolean>> futures = executorService.invokeAll(liftEngineCallableList);

			// Check for any failed tasks and log them
			processResult(futures);

		} catch (Exception exception) {
			LOG.error("Exception : " + exception.getMessage());
		} finally {
			executorService.shutdown();
			userInputScanner.close();
			System.exit(0);
		}
	}

	private static void processResult(List<Future<Boolean>> futures) {
		for (Future<Boolean> future : futures) {
			try {
				future.get();
			} catch (Exception exception) {
				LOG.error("Exception encountered during operation : " + exception.getMessage());
			}
		}
	}

	private static AppParameterVO fetchAppParameters(String[] args, AppParamValidator appParamValidatorUtil) {
		if (args.length < 2) {
			throw new IllegalArgumentException("Application parameters (number of lifts and number of floors) have to be passed");
		}
		AppParameterVO appParameterVO = new AppParameterVO(Long.valueOf(args[0]), Long.valueOf(args[1]));

		// Optional feature to dynamically modify number of input threads
		if (args.length == 3) {
			appParameterVO.setMaxNumberOfInputs(Long.valueOf(args[2]));
		}

		appParamValidatorUtil.validate(appParameterVO);

		return appParameterVO;
	}

	private static List<UserParameterVO> fetchUserInputParameterList(Scanner requestScanner, AppParameterVO appParameterVO, UserParamValidator userParamValidator) {
		List<UserParameterVO> userInputParameterList = new ArrayList<UserParameterVO>();
		for (int i = 0; i < appParameterVO.getMaxNumberOfInputs(); i++) {
			LOG.info("Enter lift request parameters for " + appParameterVO.getNumberOfLifts() + " lifts and " + appParameterVO.getNumberOfFloors() + " floors :");
			String userInputString = requestScanner.nextLine();
			UserParameterVO userParameterVO = processUserInputParameters(appParameterVO, userInputString, userParamValidator);
			userInputParameterList.add(userParameterVO);
		}
		return userInputParameterList;
	}

	private static UserParameterVO processUserInputParameters(AppParameterVO appParameterVO, String userInputString, UserParamValidator userParamValidator) {
		String[] userInputParams = userInputString.split(",");

		if (userInputParams.length < 3) {
			throw new IllegalArgumentException("Please input all 3 parameters (user type (IN/OUT), lift number/direction, destination floor/current floor)");
		}

		UserParameterVO userParameterVO = new UserParameterVO();
		userParameterVO.setUserType(userInputParams[0].trim());
		userParameterVO.setLiftDestinationFloor(Long.valueOf(userInputParams[2].trim()));

		if (UserTypeEnum.INSIDE.getUserType().equalsIgnoreCase(userParameterVO.getUserType())) {
			userParameterVO.setUserLiftNumber(Long.valueOf(userInputParams[1].trim()));
		} else {
			userParameterVO.setUserDirection(userInputParams[1].trim());
		}

		userParamValidator.validate(appParameterVO, userParameterVO);

		return userParameterVO;
	}
}
