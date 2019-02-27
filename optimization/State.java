package optimization;

import java.util.Random;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Arrays;

public class State {

	private static Random rand = new Random();

	// all states that have already been visited
	private static HashMap<StateArray, Float> visited = new HashMap<StateArray, Float>();

	public static boolean[] randomState(int size) {
		boolean[] mask = new boolean[size];
		// initialize a random boolean array
		for (int i = 0; i < mask.length; i++) {
			mask[i] = rand.nextBoolean();
		}
		// add the state to the visited states map
		visited.put(new StateArray(mask), null);
		return mask;
	}

	public static LinkedList<boolean[]> unvisitedNeighbourhood(boolean[] state) {
		LinkedList<boolean[]> neighbourhood = new LinkedList<boolean[]>();

		boolean[] newState;
		for (int i = 0; i < state.length; i++) {
			// generate new state array with a single flipped boolean
			newState = state.clone();
			newState[i] = !newState[i];

			// check if new state has already been visited
			StateArray newStateObject = new StateArray(newState);
			if (!visited.containsKey(newStateObject)) {
				// unseen state, add to neighbourhood and visited set
				visited.put(newStateObject, null);
				neighbourhood.add(newState);
			}
		}
		return neighbourhood;
	}

	public synchronized static Float getStateValue(boolean[] state) {
		// return loss value from the state-loss map
		return visited.get(new StateArray(state));
	}

	public synchronized static void setStateValue(boolean[] state, float value) {
		// set the loss of a state in the state-loss map
		visited.replace(new StateArray(state), value);
	}

	public static void reset() {
		// empty the set of visited states
		visited.clear();
	}

	// ----------------- helper class StateArray ----------------- \\

	private static class StateArray {

		private boolean[] state;

		private StateArray(boolean[] s) {
			state = s;
		}

		@Override
		public boolean equals(Object other) {
			// use Arrays class to compare the two arrays
			return Arrays.equals(state, ((StateArray) other).state);
		}

		@Override
		public int hashCode() {
			// use Arrays class to compute the array's hash code
			return Arrays.hashCode(state);
		}
	}

}