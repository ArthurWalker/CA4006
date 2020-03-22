package CA4006;

import java.util.Arrays;
import java.util.logging.Logger;

import CA4006.Generator;

public class Robot implements Runnable {
	private final int maxCapacity = 35;
	private final int timeWorkPart = 100;

	private Integer robotID;
	private int[] holdingParts = new int[] { 0, 0 };
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
			if (aircraftTask[0] == 2) {
				this.holdingParts[1] = temp;
			} else {
				this.holdingParts[0] = temp;
			}
		} else {
			if (aircraftTask[0] == 2) {
				aircraftTask = new Integer[] { aircraftTask[1], aircraftTask[0] };
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

	public synchronized int[] getHoldingParts() {
		return holdingParts;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public synchronized Aircraft[] getAircraftList() {
		return aircraftList;
	}

	public int getTimeWorkPart() {
		return timeWorkPart;
	}

	public synchronized void installation() throws InterruptedException {
		if (getAircraftTask()[0] == 1) {
			// if (getHoldingParts()[0]==0) {
			// System.out.println(getHoldingParts());
			// }
			if (getAircraftList()[0].checkLock(this)) {
				Thread.sleep(getAircraftList()[0].getArrivalTime());
				getAircraftList()[0].lock = true;
				getAircraftList()[0].workingRobot(this);
			} else {
				synchronized (getAircraftList()[0]) {
					getAircraftList()[0].wait();
				}
				getAircraftList()[0].nextTask(this);
			}
		}
	}

	public synchronized void print() {
		System.out.println("Robot " + getRobotID() + " in Thread" + Thread.currentThread().getName() + ". It has "
				+ Arrays.toString(getHoldingParts()) + " parts of aircraft " + Arrays.toString(getAircraftTask())
				+ " with capacity of " + getCapacity());
	}

	public synchronized void run() {
		print();
		try {
			installation();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
