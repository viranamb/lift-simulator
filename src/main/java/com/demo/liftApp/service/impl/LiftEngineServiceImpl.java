package com.demo.liftApp.service.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.demo.liftApp.entity.Lift;
import com.demo.liftApp.service.LiftEngineService;
import com.demo.liftApp.util.UserTypeEnum;
import com.demo.liftApp.vo.AppParameterVO;
import com.demo.liftApp.vo.UserParameterVO;

public class LiftEngineServiceImpl implements LiftEngineService {

	private final static Logger LOG = Logger.getLogger(LiftEngineServiceImpl.class);

	private final ConcurrentMap<Long, Lift> liftNumberMap;
	private final BlockingQueue<Lift> availableLiftQueue;

	public LiftEngineServiceImpl(ConcurrentMap<Long, Lift> liftNumberMap, BlockingQueue<Lift> availableLiftQueue) {
		this.liftNumberMap = liftNumberMap;
		this.availableLiftQueue = availableLiftQueue;
	}

	public boolean invokeLiftOperation(AppParameterVO appParameterVO, UserParameterVO userParameterVO) {
		Lift liftToBeUsed = null;
		try {
			if (UserTypeEnum.OUTSIDE.getUserType().equalsIgnoreCase(userParameterVO.getUserType())) {
				liftToBeUsed = availableLiftQueue.take();
			} else {
				liftToBeUsed = liftNumberMap.get(userParameterVO.getUserLiftNumber());
				availableLiftQueue.remove(liftToBeUsed);
			}

			// Synchronize on the lift object so that no other thread can use
			// the same lift till the current thread completes it's operation on
			// the lift.
			synchronized (liftToBeUsed) {
				moveLiftToDestinationFloor(userParameterVO, liftToBeUsed);
				if (!availableLiftQueue.contains(liftToBeUsed)) {
					availableLiftQueue.put(liftToBeUsed);
				}
			}
		} catch (InterruptedException exception) {
			if (!availableLiftQueue.contains(liftToBeUsed)) {
				availableLiftQueue.add(liftToBeUsed);
			}
			Thread.currentThread().interrupt();
			LOG.error("Thread interrupted : " + exception.getMessage());
			return false;
		}
		return true;
	}

	private void moveLiftToDestinationFloor(UserParameterVO userParameterVO, Lift lift) throws InterruptedException {
		Long liftDestinationFloor = userParameterVO.getLiftDestinationFloor();
		while (lift.getCurrentFloor() != liftDestinationFloor) {
			Thread.sleep(1000);
			LOG.info("Lift " + lift.getLiftNumber() + " crossed floor " + lift.getCurrentFloor());
			if (lift.getCurrentFloor() > liftDestinationFloor) {
				lift.setCurrentFloor(lift.getCurrentFloor() - 1);
			} else {
				lift.setCurrentFloor(lift.getCurrentFloor() + 1);
			}
		}

		Thread.sleep(2000);
		LOG.info("Lift " + lift.getLiftNumber() + " stopped on floor " + liftDestinationFloor + " to serve request (" + buildUserInputString(userParameterVO) + ")");
	}

	private String buildUserInputString(UserParameterVO userParameterVO) {
		StringBuilder inputString = new StringBuilder();
		inputString.append(userParameterVO.getUserType() + ",");
		inputString.append(userParameterVO.getUserLiftNumber() == null ? userParameterVO.getUserDirection() + "," : userParameterVO.getUserLiftNumber() + ",");
		inputString.append(userParameterVO.getLiftDestinationFloor());
		return inputString.toString();
	}
}
