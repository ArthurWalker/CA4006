package CA4006;
import CA4006.Generator;

public class Robot implements  Runnable{
	private final Double maxCapacity =1320.0;
	
	private Integer robotID;
	private Integer holdingParts;
	private Integer capacity;
	
	public Robot(Integer id) {
		this.robotID = id;
		this.capacity = Generator.generateCapacity();
		this.holdingParts = Generator.generateNumParts();
	}

	public Double getMaxCapacity() {
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
