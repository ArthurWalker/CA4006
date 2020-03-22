package CA4006;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import CA4006.Generator;

public class Robot implements Runnable {
	private final int maxCapacity = 25;
	private final int timeWorkPart = 100;

	private Integer robotID;
	private int[] holdingParts = new int[] { 0, 0 };
	private Integer capacity = 0;
	private Integer[] aircraftTask;
	private Workplan workplan;
	private Aircraft[] aircraftList;
	public boolean lock = true;
	public boolean dealingNextAircraft = false;
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

	public synchronized int getMaxCapacity() {
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

	public synchronized Aircraft whichSooner(Aircraft[] aircrafts) {
		if (aircrafts[0].getArrivalTime() <= aircrafts[1].getArrivalTime()) {
			return aircrafts[0];
		} else {
			return aircrafts[1];
		}
	}

	public synchronized Aircraft whichLater(Aircraft[] aircrafts) {
		if (aircrafts[0].getArrivalTime() <= aircrafts[1].getArrivalTime()) {
			return aircrafts[1];
		} else {
			return aircrafts[0];
		}
	}

	public synchronized void workingAircraft(Aircraft aircraft) throws InterruptedException {
		if (getHoldingParts()[aircraft.getAircraftID() - 1] > 0) {
			if (aircraft.checkLock(this)) {
				System.out.println("Waiting "+aircraft.getArrivalTime()+" for Aircraft"+aircraft.getAircraftID());
				Thread.sleep(aircraft.getArrivalTime());
				aircraft.lock = true;
				aircraft.workingRobot(this);
			} else {
				synchronized (aircraft) {
					aircraft.wait();
				}
				aircraft.nextTask(this);
			}
		}
	}
	
	public synchronized void installation() throws InterruptedException {
		Aircraft aircraftSooner = whichSooner(getAircraftList());
		Aircraft aircraftLater = whichLater(getAircraftList());

		if (this.lock) {
			while (this.lock) {
				this.lock = false;
				workingAircraft(aircraftSooner);
			}
		} else {
			synchronized (this) {
				this.wait();
			}
		} 
		if (this.lock && aircraftLater.getArrivalRobot()!=getRobotID()) {
			synchronized (this) {
				this.wait();
			}
		}
		this.lock = true;
		this.notifyAll();

		while (this.lock) {
			this.lock = false;
			this.dealingNextAircraft = true;
			workingAircraft(aircraftLater);
			this.dealingNextAircraft = false;
		}
	}

	public synchronized void print() {
		System.out.println("Robot " + getRobotID() + " in Thread" + Thread.currentThread().getName() + ". It has "
				+ Arrays.toString(getHoldingParts()) + " parts of aircraft " + Arrays.toString(getAircraftTask())
				+ " with total parts of " + getCapacity()+". It has the maximum capacity of "+getMaxCapacity());
	}

	public synchronized void run() {
		print();
		try {
			installation();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
