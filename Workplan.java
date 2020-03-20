package CA4006;
import CA4006.Generator;

public class Workplan implements Runnable {
	
	private int[][] tasks;
	
	public Workplan(Integer numTask) {
		int[][] temp = new int[numTask][];
		for (int i = 0; i < numTask; i++) {
			temp[i]= singleTask();	 
		}
		this.tasks = temp;
		
	}
	
	public int[][] getTasks() {
		return tasks;
	}


	public int[] singleTask() {
		int[] arr = new int[3];
		arr[0] = Generator.generateID();
		arr[1] = Generator.generateNumParts();
		arr[2] = Generator.generateID();
		System.out.println(arr);
  		return arr;
	}
	
	public void run() {
		System.out.println("This is workplan in Thread "+Thread.currentThread().getName());
	}
	
}
