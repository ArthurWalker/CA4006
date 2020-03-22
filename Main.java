package CA4006;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import CA4006.Generator;
import CA4006.Aircraft;
import CA4006.Robot;

public class Main {
	private final static int numAircraft = 2;
	private final static int numRobot = 10;

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		ExecutorService service = Executors.newFixedThreadPool(15);

		Workplan workplan = new Workplan(numRobot);

		Aircraft[] aircraft = new Aircraft[2];
		for (int i = 0; i < numAircraft; i++) {
			Integer temp = Generator.generateRandomNumber(10);
			try {
				aircraft[i] = new Aircraft(i + 1, temp);
				aircraft[i].print();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}

		System.out.println("Start assigning Tasks:");

		for (int i = 0; i < numRobot; i++) {
			service.execute(workplan.assignTask(aircraft));
		}

		service.shutdown();
		service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	}

}
