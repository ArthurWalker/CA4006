/**
 * 
 */
package CA4006;

/**
 * @author ArthurWalker
 *
 */
import CA4006.Generator;

public class Aircraft implements Runnable {
	private Integer aircraftID;
	private Integer arrivalTime;
	private Integer arrivalRobot;
	private boolean[] productionLine;

	public Aircraft(int robotID) {
		this.aircraftID = Generator.generateAircraftID();
		this.arrivalTime = Generator.generateArrivalTime();
		this.arrivalRobot = robotID;
		boolean[] temp;
		temp = new boolean[] { false, false, false, false, false, false, false, false, false, false };
		temp[this.arrivalRobot] = true;
		this.productionLine = temp;
	}

	public Integer getAircraftID() {
		return aircraftID;
	}

	public Integer getArrivalTime() {
		return arrivalTime;
	}

	public Integer getArrivalRobot() {
		return arrivalRobot;
	}

	public boolean[] getProductionLine() {
		return productionLine;
	}

	public void run() {
		System.out.println("ID:"+this.aircraftID+" ArrivalTime: "+this.arrivalTime+" ArrivalRobot: "+this.arrivalRobot);
		System.out.println("Aircraft " + this.aircraftID + " in Thread" + Thread.currentThread().getName());
	}

}
