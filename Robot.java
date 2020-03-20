package CA4006;
import CA4006.Generator;

public class Robot implements  Runnable{
	private final int maxCapacity =250;
	
	private Integer robotID;
	private Integer holdingParts;
	private Integer capacity;
	
	public Robot(Integer id, int capacity, int holdingParts) {
		this.robotID = id;
		this.capacity = capacity;
		this.holdingParts = holdingParts;
	}

	public Robot(Integer id) {
		this.robotID=id;
	}
	
	public int getMaxCapacity() {
		return maxCapacity;
	}

	public Integer getRobotID() {
		return robotID;
	}

	public Integer getHoldingParts() {
		return holdingParts;
	}

	public Integer getCapacity() {
		return capacity;
	}

	
	public void run() {
		System.out.println("Robot name "+this.robotID+" in Thread"+Thread.currentThread().getName());
	}

}
