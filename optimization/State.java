package optimization;

import java.util.Random;

public class State {

	private static Random rand = new Random();

	public static boolean[] randomState(int size) {
		boolean[] mask = new boolean[size];
		// initialize a random boolean array
		for (int i = 0; i < mask.length; i++) {
			mask[i] = rand.nextBoolean();
		}
		return mask;
	}

	public static boolean[][] generateNeighbourhood(boolean[] state) {
		boolean[][] neighbourhood = new boolean[state.length][];

		boolean[] newState;
		for (int i = 0; i < state.length; i++) {
			// generate new state array with a single flipped boolean
			neighbourhood[i] = state.clone();
			neighbourhood[i][i] = !neighbourhood[i][i];
		}
		return neighbourhood;
	}

}