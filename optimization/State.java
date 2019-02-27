package optimization;

import java.util.Random;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Arrays;

public class State {

	private static Random rand = new Random();

	// all states that have already been visited
	private static HashSet<StateArray> visited = new HashSet<StateArray>();

	public static boolean[] randomState(int size) {
		boolean[] mask = new boolean[size];
		// initialize a random boolean array
		for (int i = 0; i < mask.length; i++) {
			mask[i] = rand.nextBoolean();
		}
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
			if (!visited.contains(newStateObject)) {
				// unseen state, add to neighbourhood and visited set
				visited.add(newStateObject);
				neighbourhood.add(newState);
			}
		}
		return neighbourhood;
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