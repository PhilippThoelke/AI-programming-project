package optimization;

import java.lang.Thread;
import java.lang.Runnable;
import java.lang.InterruptedException;

public class Optimizers {

	public static boolean[] hillClimbing(int psuCount) {
		return hillClimbing(psuCount, false);
	}

	public static boolean[] firstChoiceHillClimbing(int psuCount) {
		return hillClimbing(psuCount, true);
	}

	private static boolean[] hillClimbing(int psuCount, boolean firstChoice) {
		// initialize first state randomly
		boolean[] current = State.randomState(psuCount);
		float currentLoss = Loss.loss(current);

		boolean[][] neighbourhood;
		float newLoss;

		boolean foundBetter = true;
		// continue as long as we keep improving
		while (foundBetter) {
			foundBetter = false;
			// get the neighbourhood of the current state
			neighbourhood = State.generateNeighbourhood(current);

			// (!foundBetter || !firstChoice) is true if firstChoice is false
			// --> foundBetter has no influence on while condition
			// if firstChoice is true then foundBetter stops the loop as soon as it becomes true
			// loop iterates over the neighbourhood, stops after first improvement if firstChoice is true
			for (int i = 0; i < neighbourhood.length && (!foundBetter || !firstChoice); i++) {
				newLoss = Loss.loss(neighbourhood[i]);
				// compare loss of new state to currently best state
				if (currentLoss < newLoss) {
					current = neighbourhood[i];
					currentLoss = newLoss;
					foundBetter = true;
				}
			}
		}
		return current;
	}

	public static boolean[] parallelHillClimbing(int psuCount, int iterations) {
		Thread[] threads = new Thread[iterations];
		boolean[][] results = new boolean[iterations][];

		// start n threads for parallel computation
		for (int i = 0; i < iterations; i++) {
			final int index = i;
			// initialize thread
			threads[i] = new Thread(new Runnable() {
				public void run() {
				        // run hill climbing in thread and save result into array
				        results[index] = hillClimbing(psuCount);
				}
			});
			threads[index].start();
		}

		try {
			// wait until all threads are finished
			for (int i = 0; i < threads.length; i++) {
				threads[i].join();
			}
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
			return null;
		}

		boolean[] bestState = results[0];
		float bestLoss = Loss.loss(bestState);
		float currentLoss;

		// find state with maximal loss in the results array
		for (int i = 1; i < results.length; i++) {
			currentLoss = Loss.loss(results[i]);
			if (bestLoss < currentLoss) {
				bestState = results[i];
				bestLoss = currentLoss;
			}
		}
		return bestState;
	}

}