package CA4006;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import static java.nio.charset.StandardCharsets.UTF_8;

import CA4006.Generator;
import CA4006.Aircraft;
import CA4006.Robot;

public class Main {
	private final static int numAircraft = 2;
	private final static int numRobot = 10;

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		String string2 = "output.dat";
		File outFile = new File(string2);
		try {
			outFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		OutputStream out = new FileOutputStream(outFile);

		ExecutorService service = Executors.newFixedThreadPool(15);

		Workplan workplan = new Workplan(numRobot);

		Aircraft[] aircraft = new Aircraft[2];
		for (int i = 0; i < numAircraft; i++) {
			Integer temp = Generator.generateRandomNumber(10);
			try {
				aircraft[i] = new Aircraft(i + 1, temp, out);
				aircraft[i].print(out);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		System.out.println("Start assigning Tasks:");

		for (int i = 0; i < numRobot; i++) {
			service.execute(workplan.assignTask(aircraft, out));
		}

		service.shutdown();
		service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

		String text = "DONE!";
		try {
			out.write((text + "\n").getBytes(UTF_8));
			out.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(text);

		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
