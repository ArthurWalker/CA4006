package CA4006;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import CA4006.Generator;

import java.util.LinkedList;

public class Workplan {
	private LinkedList queue = new LinkedList();
	private Integer numTask;

	public Workplan(Integer numTask) {
		this.numTask = numTask;
		generateTask();
	}

	public int[] singleTask(int id) {
		int[] arr = new int[5];
		arr[0] = id;
		arr[1] = Generator.generateNumParts();
		arr[2] = Generator.generateAircraftID();
		arr[3] = Generator.generateNumParts();
		arr[4] = Generator.generateAircraftID();
		return arr;
	}

	public void generateTask() {
		for (int i = 1; i <= numTask; i++) {
			enqueue(Arrays.toString(singleTask(i)));
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

	public Integer size() {
		return queue.size();
	}

	public synchronized Robot assignTask(Aircraft[] aircraft) {
		String request = dequeue().toString();
		String[] temp = request.substring(1, request.length() - 1).split(",");
		Integer robotID = Integer.parseInt(temp[0].trim());
		int[] holdingParts = new int[] { Integer.parseInt(temp[1].trim()), Integer.parseInt(temp[3].trim()) };
		Integer[] workingAircraft = new Integer[] { Integer.parseInt(temp[2].trim()),
				Integer.parseInt(temp[4].trim()) };
		return new Robot(robotID, holdingParts, workingAircraft, aircraft);
	}

	public void print() {
		ListIterator list_iter = this.queue.listIterator(0);
		int i = 0;
		while (list_iter.hasNext()) {
			System.out.println(i + ": " + list_iter.next());
			i++;
		}
	}

}
