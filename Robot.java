package CA4006;

import java.util.Arrays;

import CA4006.Generator;

public class Robot implements Runnable {
	private final int maxCapacity = 400;
	private final int individualCapacity=20;
	
	private Integer robotID;
	private int[] holdingParts;
	private Integer capacity=0;
	private Integer[] workingAircraft;
	private Workplan workplan;
	
	public Robot(Integer id,Workplan workplan) {
		this.robotID = id;
		this.workplan = workplan;
	}
	
	public Robot(Integer id, int[] holdingParts, Integer[] aircraft) {
		this.robotID = id;
		if (aircraft[0]==aircraft[1]) {
			this.workingAircraft = new Integer[] {aircraft[0]};
			int temp =holdingParts[0]+holdingParts[1];
			this.holdingParts = new int[] {temp};
		}
		else {
			this.holdingParts =  holdingParts;
			this.workingAircraft= aircraft;
		}
		this.capacity = Arrays.stream(this.holdingParts).sum()*individualCapacity;
	}
	
	public Integer[] workingAircraft() {
		return workingAircraft;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public Integer getRobotID() {
		return robotID;
	}

	public int[] getHoldingParts() {
		return holdingParts;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void run() {
		System.out.println("Robot " + this.robotID + " in Thread" + Thread.currentThread().getName()+". It has "+Arrays.toString(this.holdingParts)+" parts of aircraft "+Arrays.toString(this.workingAircraft)+" with capacity of "+this.capacity);		
	}

}
