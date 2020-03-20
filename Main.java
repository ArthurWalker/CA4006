package CA4006;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import CA4006.Generator;
import CA4006.Aircraft;
import CA4006.Robot;
import CA4006.Stored_Supplies;

public class Main {
	private final static int numAircraft = 2;

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		ExecutorService service = Executors.newFixedThreadPool(15);
		Workplan workplan =new Workplan(20);
		service.execute(workplan);
		
		for (int i = 0; i < 10; i++) {
			service.execute(new Robot(i));
		}
		
		// Functions allow robots to get tasks from Workplan (Workplan is not a variable, it is a thread). Wait(), notify(), ...
		
		// Aircraft[] aircrafts = new Aircraft[numAircraft];

		// service.execute(new Stored_Supplies());

		System.out.println("Thread name " + Thread.currentThread().getName());

		service.shutdown();
		service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	}

}
