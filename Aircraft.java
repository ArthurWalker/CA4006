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
	private Integer arrivalRobot;
	public boolean lock = false;
	public boolean[] recordedRobot = new boolean[] {false,false,false,false,false,false,false,false,false,false} ;

	public Aircraft(int aircraftID, Integer robotID) {
		this.aircraftID = aircraftID;
		this.arrivalTime = Generator.generateArrivalTime();
		this.arrivalRobot = robotID;
		this.recordedRobot[robotID-1]=true;
	}

	public Integer getAircraftID() {
		return aircraftID;
	}

	public void setArrivalRobot(Integer arrivalRobot) {
		this.arrivalRobot = arrivalRobot;
	}

	public Integer getArrivalTime() {
		return arrivalTime;
	}

	public Integer getArrivalRobot() {
		return arrivalRobot;
	}

	public boolean[] getRecordedRobot() {
		return recordedRobot;
	}
	
	public synchronized void ArrivingGoingFromTo(Integer arrivalRobotID, Integer destinationRobotID) {
		this.arrivalTime = Generator.generateArrivalTime();
		setArrivalRobot(destinationRobotID);
		this.recordedRobot[destinationRobotID-1]=true;
	}
	
	public synchronized boolean checkLock(Robot robot) {
		if (this.getArrivalRobot() == robot.getRobotID() && Arrays.asList(robot.getAircraftTask()).contains(this.getAircraftID()) == true) {
			return true;
		}else {
			return false;
		}
	}

	public synchronized void workingRobot(Robot robot) throws InterruptedException {		
		try {
			int time = robot.getHoldingParts()[getAircraftID()-1]*robot.getTimeWorkPart(); 
			System.out.println(robot.getRobotID()+" takes "+time+" miliseconds");
			Thread.sleep(time);
			this.lock = false;
		}catch (Exception e) {
			// TODO: handle exception
			Thread.currentThread().interrupt();
			System.out.println("Thread Interrupt: "+e);
		}
		this.lock = true;
		notifyAll();
		if (getRecordedRobot()[robot.getRobotID()-1]==false) {
			nextTask(robot.getRobotID());
			wait();
		}
	}
	
	public synchronized void print() {
		System.out.println(
				"ID:" + getAircraftID() + " ArrivalTime: " + getArrivalTime() + " ArrivalRobot: " + getArrivalRobot()+" in Thread " + Thread.currentThread().getName());
	}
	
	public synchronized void nextTask(Integer nextRobotID) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			wait();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("Current RobotID: "+arrivalRobot+", Next RobotID: "+nextRobotID);
		ArrivingGoingFromTo(arrivalRobot, nextRobotID);
	}
	
	public void run() {
		
	}

}
