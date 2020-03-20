package CA4006;

import CA4006.Generator;

public class Robot implements Runnable {
	private final int maxCapacity = 250;
	private final int individualCapacity=20;
	
	private Integer robotID;
	private Integer holdingParts = 0;
	private Integer capacity=0;
	private Integer workingAircraft;
	
	public Robot(Integer id) {
		this.robotID = id;
	}

	public Robot(Integer id, Integer holdingParts, Integer aircraft) {
		this.robotID = id;
		if (this.holdingParts!=0) {
			this.holdingParts+=holdingParts;
		}else {
			this.holdingParts = holdingParts;	
		}
		if (this.capacity != 0) {
			this.capacity +=holdingParts*individualCapacity;
		}else {
			this.capacity = holdingParts*individualCapacity;	
		}
		this.workingAircraft = aircraft;
	}
	
	public Integer workingAircraft() {
		return workingAircraft;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public Integer getRobotID() {
		return robotID;
	}

	public Integer getHoldingParts() {
		return holdingParts;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void run() {
		System.out.println("Robot name " + this.robotID + " in Thread" + Thread.currentThread().getName());
	}

}
