package CA4006;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList; 
import java.util.Queue;

import CA4006.Generator;

public class Workplan implements Runnable {

	private int[][] tasks;
	private Integer numTask;
	
	public Workplan(Integer numTask) {
		this.numTask= numTask;
		generateTask();
	}
	
	public void generateTask() {
		int[][] temp = new int[numTask][];
		for (int i = 0; i < numTask; i++) {
			temp[i] = singleTask();
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
		System.out.println("This is workplan in Thread " + Thread.currentThread().getName());
	}

}
