package optimization;

import java.lang.Thread;
import java.lang.Runnable;
import java.lang.InterruptedException;

import java.util.Random;

/*
 *	The Optimizers class contains the code for the five local search algorithms hill climbing,
 *	first choice hill climbing, local beam search, parallel hill climbing and simulated annealing.
 *	Objects of this class should not be created since all the local search operations
 *	are static methods that do not require instantiation. Each of the local search algorithms
 *	takes the PSU count as a parameter (local beam search and parallel hill climbing require
 *	an additional state parameter) and returns a boolean array representing the optimized state.
 *	In each call to one of the methods the initial state or states are initialized randomly so
 *	multiple calls may result in different results.
 */

public class Optimizers {

	// parameters for simulated annealing
	// this configuration was found by testing
	private static final double INITIAL_TEMPERATURE = 2500;
	private static final float LOSS_SCALE = 1e7f;
	private static final int TEMPERATURE_STEP_DELAY = 5;
	private static final float TEMPERATURE_DECREASE = 0.3f;

	private static Random rand = new Random();

	public static boolean[] hillClimbing(int psuCount) {
		// public wrapper for hill climbing
		return hillClimbing(psuCount, false);
	}

	public static boolean[] firstChoiceHillClimbing(int psuCount) {
		// public wrapper for first choice hill climbing
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

	public static boolean[] simulatedAnnealing(int psuCount) {
		boolean[] currentState = State.randomState(psuCount);
		float currentLoss = Loss.loss(currentState);

		double temperature = INITIAL_TEMPERATURE;

		boolean[] newState;
		float newLoss;
		float evaluator;
		int stepCounter = 0;

		while (temperature >= 0) {
			// find a random neighbour in the current neighbourhood
			newState = State.randomNeighbour(currentState);
			newLoss = Loss.loss(newState);

			evaluator = (newLoss - currentLoss) * LOSS_SCALE;
			if (evaluator > 0) {
				// random state is better than current
				currentState = newState;
				currentLoss = newLoss;
			} else {
				// random state is worse than current
				if (rand.nextFloat() < Math.exp(evaluator / temperature)) {
					// choose worse random state with probability exp(evaluator / temperature)
					currentState = newState;
					currentLoss = newLoss;
				}
			}

			stepCounter++;
			// decrease temperature every nth step
			if (stepCounter == TEMPERATURE_STEP_DELAY) {
				temperature -= TEMPERATURE_DECREASE;
				stepCounter = 0;
			}
		}
		return currentState;
	}

}