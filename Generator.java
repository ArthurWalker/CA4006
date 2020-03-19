package CA4006;
import java.util.Random;

public class Generator {
	private static final Integer maxAircraft = 100;
	private static final Integer maxRobot = 10;
	private static Long startTime = System.currentTimeMillis();
	
	
	//  Used to generate random values for Aircraft ID, number of parts, capacity of each robot, time and Working RobotID
	public static Integer generateRandomNumber() {
		return new Random().nextInt(100)+1;
	}
	
	public static Integer generateRandomNumber(Integer bound) {
		return new Random().nextInt(bound)+1;
	}

	public static Integer generateID() {
		return generateRandomNumber(maxAircraft);
	}
	
	public static Integer generateCapacity() {
		return generateRandomNumber();		
	}
	
	public static Integer generateNumParts() {
		return generateRandomNumber();
	}
	
	public static Integer generateTime() {
		return generateRandomNumber(2000);
	}
	
	public static Integer generateArrivalTime() {
		return new Long(System.currentTimeMillis()-startTime).intValue();
	}
	
	public static Integer generateRobot(boolean[] occupiedRobot) {
		int temp;
		do {
			temp = generateRandomNumber(maxRobot);
			temp =-1;
		} while (occupiedRobot[temp]);
		occupiedRobot[temp] = true;
		return temp;
	}
	
	public static Integer generateRobot(Integer robotID) {
		Integer temp = 0;
		do {
			temp = generateRandomNumber(maxRobot);
		}while (temp == robotID);
		return temp;
	}
	
	
}
