/**
 * 
 */
package CA4006;

/**
 * @author ArthurWalker
 *
 */
import CA4006.Generator;

public class Aircraft {
	private Integer aircraftID;
	private Integer arrivalTime;
	private Integer arrivalRobot;

	public Aircraft (boolean[] occupiedRobot) {
		this.aircraftID = Generator.generateID();
		this.arrivalTime = Generator.generateArrivalTime();
		this.arrivalRobot = Generator.generateRobot(occupiedRobot);
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

}
