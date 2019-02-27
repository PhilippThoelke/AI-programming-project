package optimization;

import java.util.LinkedList;
import java.util.ListIterator;

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
		// initialize initial state randomly
		boolean[] current = State.randomState(psuCount);
		float currentLoss = Loss.loss(current);

		LinkedList<boolean[]> neighbourhood;
		boolean[] newState;
		ListIterator it;
		float newLoss;

		boolean foundBetter = true;
		// continue as long as we keep improving
		while (foundBetter) {
			foundBetter = false;
			// get the neighbourhood of the current state
			neighbourhood = State.unvisitedNeighbourhood(current);

			it = neighbourhood.listIterator();
			// (!foundBetter || !firstChoice) is true if firstChoice is false
			// --> foundBetter has no influence on while condition
			// if firstChoice is true then foundBetter stops the loop as soon as it becomes true
			// loop iterates over the neighbourhood, stops after first improvement if firstChoice is true
			while (it.hasNext() && (!foundBetter || !firstChoice)) {
				newState = (boolean[]) it.next();
				newLoss = Loss.loss(newState);

				// compare loss of new state to currently best state
				if (currentLoss < newLoss) {
					current = newState;
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

		for (int i = 0; i < iterations; i++) {
			final int index = i;
			threads[i] = new Thread(new Runnable() {
				public void run() {
				        results[index] = hillClimbing(psuCount);
				        System.out.println("Thread " + index + " finished");
				}
			});
			threads[index].start();
		}
		try {
			for (int i = 0; i < threads.length; i++) {
				threads[i].join();
			}
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
			return null;
		}

		boolean[] best = results[0];
		for (int i = 1; i < results.length; i++) {
			if (Loss.loss(best) < Loss.loss(results[i])) {
				best = results[i];
			}
		}
		return best;
	}

}