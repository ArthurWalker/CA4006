package CA4006;
import java.util.Arrays;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

import CA4006.Generator;

public class Workplan implements Runnable {
	
	private int[][] tasks;
	private Queue<Map<Robot,ReentrantLock>> taskQueue;
	
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
		arr[0] = Generator.generateRobotID();
		arr[1] = Generator.generateNumParts();
		arr[2] = Generator.generateAircraftID();
		System.out.println(Arrays.toString(arr));
  		return arr;
	}
	
	public void run() {
		System.out.println("This is workplan in Thread "+Thread.currentThread().getName());
	}
	
}
