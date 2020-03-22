/**
 * 
 */
package CA4006;

import java.util.Arrays;

/**
 * @author ArthurWalker
 *
 */
import CA4006.Generator;

public class Aircraft implements Runnable {
	private Integer aircraftID;
	private Integer arrivalTime;
	private Integer finishedTime;
	private Integer arrivalRobot;
	private int processingTime;
	public boolean lock = false;
	public boolean[] recordedRobot = new boolean[] { false, false, false, false, false, false, false, false, false,
			false };

	public Aircraft(int aircraftID, Integer robotID) {
		this.aircraftID = aircraftID;
		this.arrivalTime = Generator.generateArrivalTime() * 1000;
		this.arrivalRobot = robotID;
		this.recordedRobot[robotID - 1] = true;
	}

	public synchronized Integer getFinishedTime() {
		return finishedTime;
	}

	public synchronized void setFinishedTime(Integer finishedTime) {
		this.finishedTime = finishedTime;
	}

	public synchronized Integer getAircraftID() {
		return aircraftID;
	}

	public synchronized void setArrivalRobot(Integer arrivalRobot) {
		this.arrivalRobot = arrivalRobot;
	}

	public synchronized Integer getArrivalTime() {
		return arrivalTime;
	}

	public synchronized Integer getArrivalRobot() {
		return arrivalRobot;
	}

	public synchronized boolean[] getRecordedRobot() {
		return recordedRobot;
	}

	public synchronized void setProcessingTime(int time) {
		this.processingTime = time;
	}

	public synchronized int getProcessingTime() {
		return this.processingTime;
	}

	public synchronized void setArrivalTime(int time) {
		this.arrivalTime = time;
	}

	public synchronized void ArrivingGoingFromTo(Integer arrivalRobotID, Integer nextRobotID) {
		setArrivalRobot(nextRobotID);
		this.recordedRobot[nextRobotID - 1] = true;
	}

	public synchronized boolean checkLock(Robot robot) {
		if (getArrivalRobot() == robot.getRobotID()
				&& Arrays.asList(robot.getAircraftTask()).contains(this.getAircraftID()) == true) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized void workingRobot(Robot robot) throws InterruptedException {
		while (this.lock) {
			try {
				// System.out.println("Parts: "+robot.getHoldingParts()[getAircraftID()-1]);
				// System.out.println("Parts: "+robot.getHoldingParts()[0]);
				// System.out.println("Time per part: "+robot.getTimeWorkPart());
				int time = robot.getHoldingParts()[getAircraftID() - 1] * robot.getTimeWorkPart();
				// System.out.println("Total time: "+time);
				if (time > 0) {
					Thread.sleep(time);
				}
				setProcessingTime(time);
				setFinishedTime(getArrivalTime() + getProcessingTime());
				System.out.println("Finish current RobotID: " + getArrivalRobot() + " in " + getProcessingTime()
						+ " at " + getFinishedTime() + " in Thread " + Thread.currentThread().getName());
				this.lock = false;
			} catch (Exception e) {
				Thread.currentThread().interrupt();
				System.out.println("Thread Interrupt: " + e);
			}
		}
		this.lock = true;
		notifyAll();
	}

	public synchronized void nextTask(Robot nextRobot) {
		// System.out.println(Thread.currentThread().getName()+"
		// "+Thread.currentThread().getState());
		// System.out.println(nextRobot.getRobotID());
		if (getRecordedRobot()[nextRobot.getRobotID() - 1] == false) {
			Integer nextRobotID = nextRobot.getRobotID();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setArrivalTime(getFinishedTime() + 100);
			System.out.println("Execute with next RobotID: " + nextRobotID + " at " + getArrivalTime() + " in Thread "
					+ Thread.currentThread().getName());
			ArrivingGoingFromTo(arrivalRobot, nextRobotID);
			try {
				workingRobot(nextRobot);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized void print() {
		System.out.println("ID:" + getAircraftID() + " ArrivalTime: " + getArrivalTime() + " ArrivalRobot: "
				+ getArrivalRobot() + " in Thread " + Thread.currentThread().getName());
	}

	public void run() {
	}

}
