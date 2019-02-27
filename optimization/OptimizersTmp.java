package optimization;

import java.util.LinkedList;

//This class implements the Radnom Restart Hill Climbing algorithm.
public class OptimizersTmp {

	public static boolean[] randomRestarthillClimbing(int n, int psu_number) {

		boolean[][] hill_climbings = null;
		boolean[] best = hillClimbing(psu_number);

		for (int i = 0; i < n; i++) {
			boolean[] cur = hillClimbing(psu_number);
			if (Loss.loss(cur) < Loss.loss(best)) {
				best = cur;
			}
		}
		return best;
	}

// --------------------------------------------------------------------------

	public static boolean[] firstChoiceHillClimbing(boolean[] cur) {
		boolean[] compare = cur;

		LinkedList neighbourhood = State.unvisitedNeighbourhood(cur);

		for (int i = 0; i < neighbourhood.size(); i++) {
			boolean[] here = (boolean[]) neighbourhood.get(i);
			if (Loss.loss(cur) > Loss.loss(here)) {
				cur = here;
				break;
			}
		}
		return cur;
	}

// --------------------------------------------------------------------------

	public static boolean[] hillClimbing(int psu_number) {
		boolean[] compare = State.randomState(psu_number);
		boolean better = true;

		while (better == true) {
			better = false;
			LinkedList neighbourhood = State.unvisitedNeighbourhood(compare);

			for (int i = 0; i < neighbourhood.size(); i++) {
				boolean[] here = (boolean[]) neighbourhood.get(i);
				if (Loss.loss(compare) < Loss.loss(here)) {
					compare = here;
					better = true;
				}
			}
		}
		return compare;
	}
}