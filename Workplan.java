package CA4006;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList; 
import java.util.Queue;

import CA4006.Generator;

import java.util.LinkedList;


public class Workplan implements Runnable {
    private LinkedList queue = new LinkedList();
	private int[][] tasks;
	private Integer numTask;
	
	public Workplan(Integer numTask) {
		this.numTask= numTask;
		generateTask();
	}

	public int[][] getTasks() {
		return tasks;
	}

	public int[] singleTask() {
		int[] arr = new int[3];
		arr[0] = Generator.generateRobotID();
		arr[1] = Generator.generateNumParts();
		arr[2] = Generator.generateAircraftID();
		return arr;
	}

	public void generateTask() {
		int[][] temp = new int[numTask][];
		for (int i = 0; i < numTask; i++) {
			temp[i] = singleTask();
			enqueue(temp[i]);
		}
		this.tasks = temp;
	}
	
	public void enqueue(Object arr) {
		this.queue.addLast(arr);
	}
	
	public Object dequeue() {
		return this.queue.removeFirst();
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public void run() {
		System.out.println(this.queue);
		System.out.println("This is workplan in Thread " + Thread.currentThread().getName());
	}

}
