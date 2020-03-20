package CA4006;

public class Stored_Supplies implements Runnable {
	private Integer capacity_part=20;

	public Integer getCapacity_part() {
		return capacity_part;
	} 
	
	public void run() {
		System.out.println("This is the stored supply in Thread "+Thread.currentThread().getName());
	}
}
