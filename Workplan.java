package CA4006;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import CA4006.Generator;

import java.util.LinkedList;


public class Workplan implements Runnable {
    private LinkedList queue = new LinkedList();
	private Integer numTask;
	
	public Workplan(Integer numTask) {
		this.numTask= numTask;
		generateTask();
	}


	public int[] singleTask() {
		int[] arr = new int[3];
		arr[0] = Generator.generateRobotID();
		arr[1] = Generator.generateNumParts();
		arr[2] = Generator.generateAircraftID();
		return arr;
	}

	public void generateTask() {
		for (int i = 0; i < numTask; i++) {
			enqueue(Arrays.toString(singleTask()));
		}
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
		ListIterator list_iter = this.queue.listIterator(0);
		while (list_iter.hasNext()) {
			System.out.println(list_iter.next());
		}
		System.out.println("This is workplan in Thread " + Thread.currentThread().getName());
	}

}
