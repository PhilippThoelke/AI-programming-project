package optimization;

import java.util.Random;

/*
 *	The State class implements static operations to generate new random states, neighbourhoods
 *	of states and single random neighbours. A state is a boolean array with the length equal to
 *	the number of PSUs where each element determines whether the PSU with the index of the
 *	element is being used or not. This way a high dimensional state space is constructed which
 *	contains every possible constellation of PSUs. The neighbourhood of a state consists of all
 *	states around the given one where a single entry in the state array is flipped. This ensures
 *	that all possible states can be reached and that the neighbourhood does not grow too large to
 *	iterate over.
 */

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
			// generate new state array
			neighbourhood[i] = state.clone();
			// flip the ith boolean in the new state array
			neighbourhood[i][i] = !neighbourhood[i][i];
		}
		return neighbourhood;
	}

	public static boolean[] randomNeighbour(boolean[] state) {
		boolean[] neighbour = state.clone();
		// flip a random boolean in the new state array
		int index = rand.nextInt(neighbour.length);
		neighbour[index] = !neighbour[index];
		return neighbour;
	}

}