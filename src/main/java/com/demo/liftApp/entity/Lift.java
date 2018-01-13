package com.demo.liftApp.entity;

public class Lift {

	private final Long liftId;
	private final Long liftNumber;
	private Long currentFloor;

	public Lift(final Long liftId, final Long liftNumber, Long currentFloor) {
		this.liftId = liftId;
		this.liftNumber = liftNumber;
		this.currentFloor = currentFloor;
	}

	public Long getLiftId() {
		return liftId;
	}

	public Long getLiftNumber() {
		return liftNumber;
	}

	public Long getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(Long currentFloor) {
		this.currentFloor = currentFloor;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (this.getClass() != other.getClass()) {
			return false;
		}

		if (this == other) {
			return true;
		}

		Long otherLiftId = ((Lift) other).getLiftId();
		return otherLiftId == null ? false : otherLiftId.equals(this.getLiftId());
	}

	@Override
	public int hashCode() {
		int hashcode = 0;
		hashcode = 31 * hashcode + (this.getLiftId() == null ? 0 : this.getLiftId().hashCode());
		return hashcode;
	}
}
