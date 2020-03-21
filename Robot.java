package CA4006;

import java.util.Arrays;
import java.util.logging.Logger;

import CA4006.Generator;

public class Robot implements Runnable {
	private final int maxCapacity = 35;
	private final int timeWorkPart = 100;

	private Integer robotID;
	private int[] holdingParts;
	private Integer capacity = 0;
	private Integer[] aircraftTask;
	private Workplan workplan;
	private Aircraft[] aircraftList;

	public Robot(Integer id, Workplan workplan) {
		this.robotID = id;
		this.workplan = workplan;
	}

	public Robot(Integer id, int[] holdingParts, Integer[] aircraftTask, Aircraft[] aircraftList) {
		this.robotID = id;
		if (aircraftTask[0] == aircraftTask[1]) {
			this.aircraftTask = new Integer[] { aircraftTask[0] };
			int temp = holdingParts[0] + holdingParts[1];
			if (aircraftTask[0]==2) {
				this.holdingParts = new int[] { 0,temp };
			} else {
				this.holdingParts  = new int[] {temp,0};
			}
		} else {
			if (holdingParts[0]==2) {
				holdingParts = new int[] {holdingParts[1],holdingParts[0]};
			}
			this.holdingParts = holdingParts;
			this.aircraftTask = aircraftTask;
		}
		this.capacity = Arrays.stream(this.holdingParts).sum();
		this.aircraftList = aircraftList;
	}
	
	public Integer[] getAircraftTask() {
		return aircraftTask;
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

	public Aircraft[] getAircraftList() {
		return aircraftList;
	}
	
	public int getTimeWorkPart() {
		return timeWorkPart;
	}
	
	public synchronized void installation() throws InterruptedException {
		getAircraftList()[0].workingRobot(this);
		wait();
	}
	
	public void run() {
		System.out.println("Robot " + getRobotID() + " in Thread" + Thread.currentThread().getName() + ". It has "
				+ Arrays.toString(getHoldingParts()) + " parts of aircraft " + Arrays.toString(getAircraftTask())
				+ " with capacity of " + getCapacity());
		try {
			installation();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
