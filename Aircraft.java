/**
 * 
 */
package CA4006;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
	public OutputStream out;

	public Aircraft(int aircraftID, Integer robotID, OutputStream out) {
		this.aircraftID = aircraftID;
		this.arrivalTime = Generator.generateArrivalTime() * 1000;
		this.arrivalRobot = robotID;
		this.recordedRobot[robotID - 1] = true;
		this.out = out;
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
				int time = robot.getHoldingParts()[getAircraftID() - 1] * robot.getTimeWorkPart();
				if (time > 0) {
					Thread.sleep(time);
				}
				setProcessingTime(time);
				setFinishedTime(getArrivalTime() + getProcessingTime());
				String text = "Aircraft " + getAircraftID() + " - Finish current RobotID: " + getArrivalRobot() + " in "
						+ getProcessingTime() + " at " + getFinishedTime() + " in Thread "
						+ Thread.currentThread().getName();
				try {
					this.out.write((text + "\n").getBytes(UTF_8));
					this.out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println(text);
				this.lock = false;
			} catch (Exception e) {
				Thread.currentThread().interrupt();
				System.out.println("Thread Interrupt: " + e);
			}
		}
		this.lock = true;
		this.notifyAll();
	}

	public synchronized void nextTask(Robot nextRobot) {
		if (getRecordedRobot()[nextRobot.getRobotID() - 1] == false) {
			Integer nextRobotID = nextRobot.getRobotID();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setArrivalTime(getFinishedTime() + 100);
			int loadingParts = 0;
			if (nextRobot.getCapacity() > nextRobot.getMaxCapacity()) {
				loadingParts = (nextRobot.getCapacity() - nextRobot.getMaxCapacity());
			}
			String text;
			if (loadingParts > 0 && nextRobot.dealingNextAircraft) {
				try {
					Thread.sleep(loadingParts * 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				setArrivalTime(getArrivalTime() + loadingParts * 100);
				text = "Aircraft " + getAircraftID() + " - Waiting " + loadingParts * 100 + " to load " + loadingParts
						+ " parts then Execute with next RobotID: " + nextRobotID + " at " + getArrivalTime()
						+ " in Thread " + Thread.currentThread().getName();
				try {
					this.out.write((text + "\n").getBytes(UTF_8));
					this.out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("Aircraft " + getAircraftID() + " - Waiting " + loadingParts * 100 + " to load "
						+ loadingParts + " parts then Execute with next RobotID: " + nextRobotID + " at "
						+ getArrivalTime() + " in Thread " + Thread.currentThread().getName());
			} else if (loadingParts == 0 || nextRobot.dealingNextAircraft == false) {
				text = "Aircraft " + getAircraftID() + " - Execute with next RobotID: " + nextRobotID + " at "
						+ getArrivalTime() + " in Thread " + Thread.currentThread().getName();
				try {
					this.out.write((text + "\n").getBytes(UTF_8));
					this.out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("Aircraft " + getAircraftID() + " - Execute with next RobotID: " + nextRobotID
						+ " at " + getArrivalTime() + " in Thread " + Thread.currentThread().getName());
			}

			ArrivingGoingFromTo(arrivalRobot, nextRobotID);
			try {
				workingRobot(nextRobot);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void print(OutputStream out) {
		String text = "ID:" + getAircraftID() + " ArrivalTime: " + getArrivalTime() + " ArrivalRobot: "
				+ getArrivalRobot() + " in Thread " + Thread.currentThread().getName();
		try {
			out.write(text.getBytes(UTF_8));
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(text);
	}

	public void run() {
	}

}
