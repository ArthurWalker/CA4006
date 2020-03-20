package CA4006;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import CA4006.Generator;
import CA4006.Aircraft;
import CA4006.Robot;
import CA4006.Stored_Supplies;

public class Main {
	private final static int numAircraft = 2;

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		ExecutorService service = Executors.newFixedThreadPool(15);
		
		for (int i = 1; i <= numAircraft; i++) {
			int temp = Generator.generateRandomNumber(10)-1;
			Aircraft aircraft = new Aircraft(temp);
			service.execute(aircraft);
		}
		
		Workplan workplan =new Workplan(10);
		System.out.println("Initialize plans:");
		workplan.print();
		
		System.out.println("Start assigning Tasks:");
		
		for (int i = 0; i < 10; i++) {
			service.execute(workplan.assignTask());
		}
		
		service.shutdown();
		service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	}

}
