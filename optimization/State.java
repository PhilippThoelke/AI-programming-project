package optimization;

import java.util.Random;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Arrays;

public class State {

	private static Random rand = new Random();
	private static HashSet<StateArray> visited = new HashSet<StateArray>();

	public static boolean[] randomState(int size) {
		boolean[] mask = new boolean[size];
		for (int i = 0; i < mask.length; i++) {
			mask[i] = rand.nextBoolean();
		}
		return mask;
	}

	public static LinkedList<boolean[]> unvisitedNeighbourhood(boolean[] state) {
		LinkedList<boolean[]> neighbourhood = new LinkedList<boolean[]>();

		boolean[] newState;
		for (int i = 0; i < state.length; i++) {
			newState = state.clone();
			newState[i] = !newState[i];

			StateArray newStateObject = new StateArray(newState);
			if (!visited.contains(newStateObject)) {
				visited.add(newStateObject);
				neighbourhood.add(newState);
			}
			System.out.println(visited.size());
		}
		return neighbourhood;
	}

	private static class StateArray {

		private boolean[] state;

		private StateArray(boolean[] s) {
			state = s;
		}

		@Override
		public boolean equals(Object other) {
			return Arrays.equals(state, ((StateArray) other).state);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(state);
		}
	}

}